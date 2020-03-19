package core;

import hla.rti1516e.exceptions.AttributeNotDefined;
import hla.rti1516e.exceptions.AttributeNotOwned;
import hla.rti1516e.exceptions.CallNotAllowedFromWithinCallback;
import hla.rti1516e.exceptions.ConnectionFailed;
import hla.rti1516e.exceptions.CouldNotCreateLogicalTimeFactory;
import hla.rti1516e.exceptions.CouldNotOpenFDD;
import hla.rti1516e.exceptions.ErrorReadingFDD;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.FederateServiceInvocationsAreBeingReportedViaMOM;
import hla.rti1516e.exceptions.FederationExecutionDoesNotExist;
import hla.rti1516e.exceptions.IllegalName;
import hla.rti1516e.exceptions.InconsistentFDD;
import hla.rti1516e.exceptions.InteractionClassNotDefined;
import hla.rti1516e.exceptions.InteractionClassNotPublished;
import hla.rti1516e.exceptions.InteractionParameterNotDefined;
import hla.rti1516e.exceptions.InvalidInteractionClassHandle;
import hla.rti1516e.exceptions.InvalidLocalSettingsDesignator;
import hla.rti1516e.exceptions.InvalidObjectClassHandle;
import hla.rti1516e.exceptions.NameNotFound;
import hla.rti1516e.exceptions.NotConnected;
import hla.rti1516e.exceptions.ObjectClassNotDefined;
import hla.rti1516e.exceptions.ObjectClassNotPublished;
import hla.rti1516e.exceptions.ObjectInstanceNameInUse;
import hla.rti1516e.exceptions.ObjectInstanceNameNotReserved;
import hla.rti1516e.exceptions.ObjectInstanceNotKnown;
import hla.rti1516e.exceptions.RTIinternalError;
import hla.rti1516e.exceptions.RestoreInProgress;
import hla.rti1516e.exceptions.SaveInProgress;
import hla.rti1516e.exceptions.SynchronizationPointLabelNotAnnounced;
import hla.rti1516e.exceptions.UnsupportedCallbackModel;
import interaction.RoverFreePath;
import interaction.RoverPath;
import interaction.RoverRequestPath;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.TimeZone;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import constant.MessageConstant;
import graph.Point;
import graph.PathFind;
import helper.ListMarsMiner;
import helper.PositionOfMine;
import model.ExcavationMine;
import model.MarsMiner;
import model.Position;
import model.PositionCollection;
import siso.smackdown.frame.FrameType;
import skf.config.Configuration;
import skf.core.SEEAbstractFederate;
import skf.core.SEEAbstractFederateAmbassador;
import skf.exception.PublishException;
import skf.exception.SubscribeException;
import skf.exception.UnsubscribeException;
import skf.exception.UpdateException;
import skf.model.interaction.annotations.InteractionClass;
import skf.model.interaction.modeTransitionRequest.ModeTransitionRequest;
import skf.model.object.annotations.ObjectClass;
import skf.model.object.executionConfiguration.ExecutionConfiguration;
import skf.model.object.executionConfiguration.ExecutionMode;
import skf.synchronizationPoint.SynchronizationPoint;

public class ExcavationMineFederate extends SEEAbstractFederate implements Observer {

	private static final int MAX_WAIT_TIME = 10000;

	private ExcavationMine excavationMine = null;

	private String local_settings_designator = null;

	private ModeTransitionRequest mtr = null;

	private SimpleDateFormat format = null;

	private RoverPath roverPath;

	// Responsible for map of mine
	private PathFind map = null;

	private ListMarsMiner listMarsMiner = null;

	public ExcavationMineFederate(SEEAbstractFederateAmbassador seefedamb, ExcavationMine excavationMine,
			PathFind map) {
		super(seefedamb);
		this.excavationMine = excavationMine;
		this.mtr = new ModeTransitionRequest();
		this.map = map;
		this.roverPath = new RoverPath();
		this.listMarsMiner = new ListMarsMiner();
		this.format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	@SuppressWarnings("unchecked")
	public void configureAndStart(Configuration config) throws ConnectionFailed, InvalidLocalSettingsDesignator,
			UnsupportedCallbackModel, CallNotAllowedFromWithinCallback, RTIinternalError,
			CouldNotCreateLogicalTimeFactory, FederationExecutionDoesNotExist, InconsistentFDD, ErrorReadingFDD,
			CouldNotOpenFDD, SaveInProgress, RestoreInProgress, NotConnected, MalformedURLException,
			FederateNotExecutionMember, NameNotFound, InvalidObjectClassHandle, AttributeNotDefined,
			ObjectClassNotDefined, InstantiationException, IllegalAccessException, IllegalName, ObjectInstanceNameInUse,
			ObjectInstanceNameNotReserved, ObjectClassNotPublished, AttributeNotOwned, ObjectInstanceNotKnown,
			PublishException, UpdateException, SubscribeException, InvalidInteractionClassHandle,
			InteractionClassNotDefined, InteractionClassNotPublished, InteractionParameterNotDefined,
			UnsubscribeException, InterruptedException, SynchronizationPointLabelNotAnnounced {
		// 1. configure the SKF framework
		super.configure(config);

		// 2. Connect on RTI
		/*
		 * For MAK local_settings_designator = ""; For PITCH local_settings_designator =
		 * "crcHost=" + <crc_host> + "\ncrcPort=" + <crc_port>;
		 */
		local_settings_designator = "crcHost=" + config.getCrcHost() + "\ncrcPort=" + config.getCrcPort();
		super.connectToRTI(local_settings_designator);

		// 3. join the SEE Federation execution
		super.joinFederationExecution();

		// 4. Subscribe the Subject
		super.subscribeSubject(this);

		/*
		 * 5. Check if the federate is a Late Joiner Federate. All the Federates of the
		 * SEE Teams must be Late Joiner.
		 */
		if (!SynchronizationPoint.INITIALIZATION_STARTED.isAnnounced()) {

			// 6. Wait for the announcement of the Synch-Point "initialization_completed"
			super.waitingForAnnouncement(SynchronizationPoint.INITIALIZATION_COMPLETED, MAX_WAIT_TIME);

			/*
			 * 7. Wait for announcement of "objects_discovered", and Federation Specific
			 * Mutiphase Initialization Synch-Points
			 */
			// -> skipped

			/*
			 * 8. Subscribe Execution Control Object Class Attributes and wait for ExCO
			 * Discovery
			 */
			super.subscribeElement((Class<? extends ObjectClass>) ExecutionConfiguration.class);
			super.waitForElementDiscovery((Class<? extends ObjectClass>) ExecutionConfiguration.class, MAX_WAIT_TIME);

			// 9. Request ExCO update
			while (super.getExecutionConfiguration() == null) {
				super.requestAttributeValueUpdate((Class<? extends ObjectClass>) ExecutionConfiguration.class);
				Thread.sleep(10);
			}

			// 10. Publish MTR Interaction
			super.publishInteraction(this.mtr);

			/*
			 * 11. Publish and Subscribe Object Class Attributes and Interaction Class
			 * Parameters
			 * 
			 * 12. Reserve All Federate Object Instance Names
			 * 
			 * 13. Wait for All Federate Object Instance Name Reservation Success/Failure
			 * Callbacks
			 * 
			 * 14. Register Federate Object Instances
			 */

			try {

				super.publishElement(excavationMine, "facens_excavation_mine");
				super.subscribeReferenceFrame(FrameType.AitkenBasinLocalFixed);

				// subscribe class ExcavationMine
				super.subscribeElement((Class<? extends ObjectClass>) MarsMiner.class);

				super.subscribeInteraction((Class<? extends InteractionClass>) RoverFreePath.class);
				super.subscribeInteraction((Class<? extends InteractionClass>) RoverRequestPath.class);

				super.publishInteraction(roverPath);

			} catch (FederateServiceInvocationsAreBeingReportedViaMOM e) {
				e.printStackTrace();
			}
			// 15. Wait for All Required Objects to be Discovered
			// -> Skipped

			/*
			 * 16. Setup HLA Time Management and Query GALT, Compute HLTB and Time Advance
			 * to HLTB
			 */
			super.setupHLATimeManagement();

			// 17. Achieve "objects_discovered" Sync-Point and wait for synchronization
			// -> Skipped
		} else
			throw new RuntimeException("The federate " + config.getFederateName() + "is not a Late Joiner Federate");

		// 18. Start simulation execution
		super.startExecution();

	}

	@Override
	protected void doAction() {

		System.out.println(excavationMine);
		try {

			super.updateElement(this.excavationMine);
			// System.out.println(this.excavationMine);

		} catch (FederateNotExecutionMember | NotConnected | AttributeNotOwned | AttributeNotDefined
				| ObjectInstanceNotKnown | SaveInProgress | RestoreInProgress | RTIinternalError | IllegalName
				| ObjectInstanceNameInUse | ObjectInstanceNameNotReserved | ObjectClassNotPublished
				| ObjectClassNotDefined | UpdateException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof RoverRequestPath) {
			System.out.println((RoverRequestPath) arg1);
			sendPath((RoverRequestPath) arg1);
		}

		else if (arg1 instanceof RoverFreePath) {
			System.out.println((RoverFreePath) arg1);
			RoverFreePath free = (RoverFreePath) arg1;
			System.out.println(map.getpathRover().keySet());
			if (map.getpathRover().containsKey(free.getRoverId()))
				map.clearPath(free.getRoverId());
		}

		else if (arg1 instanceof MarsMiner) {
			if (listMarsMiner.contains((MarsMiner) arg1)) {
				listMarsMiner.attMarsMiner((MarsMiner) arg1);
			} else {
				short number_of_mars_miner = (short) (excavationMine.getNumber_of_mars_miner() + 1);
				if (number_of_mars_miner > excavationMine.getMaximun_capacity())
					number_of_mars_miner = excavationMine.getMaximun_capacity();
				excavationMine.setNumber_of_mars_miner(number_of_mars_miner);
				listMarsMiner.addMarsMiner(((MarsMiner) arg1).getName(), (MarsMiner) arg1);
			}
		}

		else if (arg1 instanceof ExecutionConfiguration) {
			super.setExecutionConfiguration((ExecutionConfiguration) arg1);

			/* Manage state transitions */
			if (super.getExecutionConfiguration().getCurrent_execution_mode() == ExecutionMode.EXEC_MODE_RUNNING
					&& super.getExecutionConfiguration().getNext_execution_mode() == ExecutionMode.EXEC_MODE_FREEZE) {
				super.freezeExecution();
			}

			else if (super.getExecutionConfiguration().getCurrent_execution_mode() == ExecutionMode.EXEC_MODE_FREEZE
					&& super.getExecutionConfiguration().getNext_execution_mode() == ExecutionMode.EXEC_MODE_RUNNING) {
				super.resumeExecution();
			}

			else if ((super.getExecutionConfiguration().getCurrent_execution_mode() == ExecutionMode.EXEC_MODE_FREEZE
					|| super.getExecutionConfiguration().getCurrent_execution_mode() == ExecutionMode.EXEC_MODE_RUNNING)
					&& super.getExecutionConfiguration().getNext_execution_mode() == ExecutionMode.EXEC_MODE_SHUTDOWN) {
				super.shudownExecution();
			} else
				System.out.println("ExecutionConfiguration status unknown");
			/* End Manage state transitions */
		}

	}

	private void sendPath(RoverRequestPath arg) {

		System.out.println(arg);

		ArrayList<Position> path = new ArrayList<>();
		Position from = arg.getCurrentPosition();
		String target = arg.getTargetPosition();

		if (target.equals(MessageConstant.REQUEST_NORTH)) {
			path = getPath(from, PositionOfMine.getPosition("north"), arg.getRoverID());
		} else if (target.equals(MessageConstant.REQUEST_SOUTH)) {
			path = getPath(from, PositionOfMine.getPosition("south"), arg.getRoverID());
		} else if (target.equals(MessageConstant.REQUEST_WEST)) {
			path = getPath(from, PositionOfMine.getPosition("west"), arg.getRoverID());
		} else if (target.equals(MessageConstant.REQUEST_EAST)) {
			path = getPath(from, PositionOfMine.getPosition("east"), arg.getRoverID());
		} else if (target.equals(MessageConstant.REQUEST_O2FAC)) {
			path = getPath(from, PositionOfMine.getPosition("o2fac"), arg.getRoverID());
		}

		List<Vector3D> translation = new ArrayList<>();
		if (path != null) {
			for (Position item : path) {
				translation.add(new Vector3D(item.getX(), item.getY(), item.getZ()));
			}
		} 

		roverPath.setPath(new PositionCollection(translation));
		roverPath.setRoverID(arg.getRoverID());

		try {
			super.updateInteraction(roverPath);
		} catch (InteractionClassNotPublished | InteractionParameterNotDefined | InteractionClassNotDefined
				| SaveInProgress | RestoreInProgress | FederateNotExecutionMember | NotConnected | RTIinternalError
				| UpdateException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Position> getPath(Position from, Point to, String key) {
		ArrayList<Position> list = new ArrayList<>();
		try {
			list = map.GetIn(map.convertPoint(from), to, key);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return list;
	}// Places on the list the positions for the MarsMiner walking

}
