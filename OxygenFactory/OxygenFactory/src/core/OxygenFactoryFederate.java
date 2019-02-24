package core;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TimeZone;
import java.util.TreeMap;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import constant.FederateNameConstant;
import constant.MessageConstant;
import constant.OxygenConstant;
import graph.PathFind;
import graph.Point;
import helper.EnergyConsumption; //add
import helper.OxygenManager; //add2
import helper.PositionOfOxygenFactory;
import helper.ReferenceMine;
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
import interaction.FinishFinderNewRoute;
import interaction.FinishOxygenFactoryRoute;
import interaction.RequestFinderNewRoute;
import interaction.RequestGoToOxygenFactory;
import interaction.RequestOxygenFactoryRoute;
import interaction.ResponseFinderNewRoute;
import interaction.ResponseGoToOxygenFactory;
import interaction.ResponseOxygenFactoryRoute;
import model.ExcavationMine;
import model.OxygenFactory;
import model.OxygenFactoryRooftop; //add
import model.OxygenFactoryRooftopStatus; //add
import model.Position;
import model.PositionCollection;
import move.RooftopStates; //add
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

public class OxygenFactoryFederate extends SEEAbstractFederate implements Observer {

	private static final int MAX_WAIT_TIME = 10000;

	private OxygenFactory oxygenFactory = null;

	private String local_settings_designator = null;

	private ModeTransitionRequest mtr = null;

	private SimpleDateFormat format = null;

	private OxygenFactoryRooftop rooftop = null;

	private boolean controle = true;

	private EnergyConsumption energy_consumption = null; // add

	private RooftopStates rooftopState = RooftopStates.IDLE; // add

	private OxygenManager oxygenMananger = null; // add

	private ResponseGoToOxygenFactory responseGoToOxygenFactory = null;

	private ResponseOxygenFactoryRoute responseOxygenFactoryRoute = null;

	private ResponseFinderNewRoute responseFinderNewRoute = null;

	private boolean enabledReceiveRover = true;

	private PathFind map = null;

	private boolean referenceDone = false;

	private Map<String, RequestGoToOxygenFactory> listPermission = null;

	public OxygenFactoryFederate(SEEAbstractFederateAmbassador seefedamb, OxygenFactory oxygenFactory,
			OxygenFactoryRooftop rooftop, PathFind map) {
		super(seefedamb);
		this.oxygenFactory = oxygenFactory;
		this.mtr = new ModeTransitionRequest();
		this.rooftop = rooftop;
		this.format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		this.energy_consumption = new EnergyConsumption(OxygenConstant.ENERGY_CONSUMPTION,
				OxygenConstant.ENERGY_CONSUMPTION_PER_MINUTE);
		this.oxygenMananger = new OxygenManager(oxygenFactory); // add
		this.responseGoToOxygenFactory = new ResponseGoToOxygenFactory();
		this.responseOxygenFactoryRoute = new ResponseOxygenFactoryRoute();
		this.responseFinderNewRoute = new ResponseFinderNewRoute();
		this.listPermission = new TreeMap<String, RequestGoToOxygenFactory>();
		this.map = map;
	}

	@SuppressWarnings("unchecked")
	public void configureAndStart(Configuration config)
			throws ConnectionFailed, InvalidLocalSettingsDesignator, UnsupportedCallbackModel,
			CallNotAllowedFromWithinCallback, RTIinternalError, CouldNotCreateLogicalTimeFactory,
			FederationExecutionDoesNotExist, InconsistentFDD, ErrorReadingFDD, CouldNotOpenFDD, SaveInProgress,
			RestoreInProgress, NotConnected, MalformedURLException, FederateNotExecutionMember, NameNotFound,
			InvalidObjectClassHandle, AttributeNotDefined, ObjectClassNotDefined, InstantiationException,
			IllegalAccessException, IllegalName, ObjectInstanceNameInUse, ObjectInstanceNameNotReserved,
			ObjectClassNotPublished, AttributeNotOwned, ObjectInstanceNotKnown, PublishException, UpdateException,
			SubscribeException, InvalidInteractionClassHandle, InteractionClassNotDefined, InteractionClassNotPublished,
			InteractionParameterNotDefined, UnsubscribeException, InterruptedException,
			SynchronizationPointLabelNotAnnounced, FederateServiceInvocationsAreBeingReportedViaMOM {
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

				super.subscribeElement((Class<? extends ObjectClass>) ExcavationMine.class);
				super.subscribeReferenceFrame(FrameType.AitkenBasinLocalFixed);

				// subscribe class interaction
				super.subscribeInteraction((Class<? extends InteractionClass>) RequestGoToOxygenFactory.class);
				super.subscribeInteraction((Class<? extends InteractionClass>) RequestOxygenFactoryRoute.class);
				super.subscribeInteraction((Class<? extends InteractionClass>) FinishOxygenFactoryRoute.class);
				super.subscribeInteraction((Class<? extends InteractionClass>) FinishFinderNewRoute.class);
				super.subscribeInteraction((Class<? extends InteractionClass>) RequestFinderNewRoute.class);

				// public class interaction
				super.publishInteraction(responseGoToOxygenFactory);
				super.publishInteraction(responseOxygenFactoryRoute);
				super.publishInteraction(responseFinderNewRoute);

			} catch (FederateServiceInvocationsAreBeingReportedViaMOM e) {
				e.printStackTrace();
			}
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
		System.out.println(oxygenFactory);
		if (ReferenceMine.reference == null)
			System.out.println("Awaiting localization of the excavation mine");

		if (!referenceDone)
			return;

		try {

			super.updateElement(this.rooftop);
			super.updateElement(this.oxygenFactory);
			// System.out.println(this.oxygenFactory);
			// System.out.println(this.rooftop);

		} catch (FederateNotExecutionMember | NotConnected | AttributeNotOwned | AttributeNotDefined
				| ObjectInstanceNotKnown | SaveInProgress | RestoreInProgress | RTIinternalError | IllegalName
				| ObjectInstanceNameInUse | ObjectInstanceNameNotReserved | ObjectClassNotPublished
				| ObjectClassNotDefined | UpdateException e) {
			e.printStackTrace();
		}

		manageEnergy();

		if (rooftopState.getValue().move(rooftop)) {
			if (rooftop.getRooftopStatus().equals(OxygenFactoryRooftopStatus.MOVING_DOWN)) {
				rooftop.setRooftopStatus(OxygenFactoryRooftopStatus.STOPPED_DOWN);
			} else if (rooftop.getRooftopStatus().equals(OxygenFactoryRooftopStatus.MOVING_UP)) {
				rooftop.setRooftopStatus(OxygenFactoryRooftopStatus.STOPPED_UP);
			}
			rooftopState = RooftopStates.IDLE;
		}
	}

	private void manageEnergy() {
		double currentEnergyConsumption = this.energy_consumption
				.updateEnergyConsumption(super.getTime().getFederateTimeCycle() / 1000000);

		if (oxygenFactory.getEnergy_comsumption() != currentEnergyConsumption)
			oxygenFactory.setEnergy_comsumption(currentEnergyConsumption);

	}

	@Override
	public void update(Observable arg0, Object arg1) {

		if (arg1 instanceof ExcavationMine) {
			if (ReferenceMine.reference == null) {
				publishOxygenFactory((ExcavationMine) arg1);
			}
		} // If reference null get position of ExcavationMine

		if (arg1 instanceof RequestGoToOxygenFactory) {
			System.out.println((RequestGoToOxygenFactory) arg1);
			goToOxygenFactory((RequestGoToOxygenFactory) arg1);
		}

		else if (arg1 instanceof RequestOxygenFactoryRoute) {
			RequestOxygenFactoryRoute requestOxygenFactoryRoute = (RequestOxygenFactoryRoute) arg1;
			System.out.println(requestOxygenFactoryRoute);
			if (enabledReceiveRover
					|| requestOxygenFactoryRoute.getRoverID().contains(FederateNameConstant.MARS_MINER)) {
				if (!(requestOxygenFactoryRoute.getRoverID().contains(FederateNameConstant.WATER_FINDER)
						&& !listPermission.isEmpty()))
					createOxygenFactoryRoute(requestOxygenFactoryRoute);
			}
		}

		else if (arg1 instanceof FinishOxygenFactoryRoute) {
			FinishOxygenFactoryRoute finishOxygenFactoryRoute = (FinishOxygenFactoryRoute) arg1;
			System.out.println(finishOxygenFactoryRoute);
			clearRoverPath(finishOxygenFactoryRoute.getRoverId());
			if (finishOxygenFactoryRoute.getRoverId().contains(FederateNameConstant.MARS_MINER)) {

				Vector3D vector_aux = finishOxygenFactoryRoute.getPath().getArrayList().get(0);
				Position position = new Position(vector_aux.getX(), vector_aux.getY(), vector_aux.getZ());

				RequestFinderNewRoute requestFinderNewRoute = new RequestFinderNewRoute(position,
						MessageConstant.REQUEST_EAST, finishOxygenFactoryRoute.getRoverId());

				sendPath(requestFinderNewRoute);
			}
			changeEnabledReceiveRover();
		}

		else if (arg1 instanceof FinishFinderNewRoute) {
			clearRoverPath(((FinishFinderNewRoute) arg1).getRoverId());
		}

		else if (arg1 instanceof RequestFinderNewRoute) {
			sendPath((RequestFinderNewRoute) arg1);
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

	private void publishOxygenFactory(ExcavationMine excavationMine) {
		ReferenceMine.setReference(new Position(excavationMine.getPosition().getX(),
				excavationMine.getPosition().getY(), excavationMine.getPosition().getZ()));

		ReferenceMine.referenceRotation = excavationMine.getRotation();

		Position minePosition = ReferenceMine.reference;
		Position position = new Position(minePosition.getX(), minePosition.getY(), minePosition.getZ());
		PositionOfOxygenFactory.setReference(position);
		oxygenFactory.setPosition(position);
		rooftop.setPosition(position);

		try {
			super.publishElement(oxygenFactory, "facens_oxygen_factory");
			super.publishElement(rooftop, "facens_oxygen_factory_rooftop");
		} catch (NameNotFound | FederateNotExecutionMember | NotConnected | RTIinternalError | InvalidObjectClassHandle
				| AttributeNotDefined | ObjectClassNotDefined | SaveInProgress | RestoreInProgress
				| InstantiationException | IllegalAccessException | IllegalName | ObjectInstanceNameInUse
				| ObjectInstanceNameNotReserved | ObjectClassNotPublished | AttributeNotOwned | ObjectInstanceNotKnown
				| PublishException | UpdateException e) {
			e.printStackTrace();
		}

		referenceDone = true;
		System.out.println(" Oxygen Factory " + oxygenFactory + " in Position!");
		System.out.println(" Rooftop Oxygen Factory " + rooftop + " in Position!");

	}

	public void changeControle() {
		controle = !controle;
		System.out.println("----------------------OxygenFactoryRooftop------------------------------");
		System.out.println("**** Update controle for " + controle + " ****");
	}

	public void requestBringUp() {
		if (controle && rooftop.getRooftopStatus().equals(OxygenFactoryRooftopStatus.STOPPED_DOWN)) {
			rooftopState = RooftopStates.UP;
			rooftop.setRooftopStatus(OxygenFactoryRooftopStatus.MOVING_UP);
		}
	}// if the Rooftop can be lifted

	public void requestDown() {

		if (controle && rooftop.getRooftopStatus().equals(OxygenFactoryRooftopStatus.STOPPED_UP)) {
			rooftopState = RooftopStates.DOWN;
			rooftop.setRooftopStatus(OxygenFactoryRooftopStatus.MOVING_DOWN);
		}
	}// if Rooftop can be let down

	private void goToOxygenFactory(RequestGoToOxygenFactory requestGoToOxygenFactory) {

		if (enabledReceiveRover) {
			if (listPermission.isEmpty()) {
				changeEnabledReceiveRover();
				oxygenMananger.calculateOxygen(requestGoToOxygenFactory.getRawMaterial());
				responseGoToOxygenFactory.setPermissionLevel(MessageConstant.REQUEST_OXYGEN_FACTORY_OK);
			} else if (((TreeMap<String, RequestGoToOxygenFactory>) listPermission).firstKey()
					.equals(requestGoToOxygenFactory.getRoverID())) {
				requestGoToOxygenFactory = listPermission.get(requestGoToOxygenFactory.getRoverID());
				listPermission.remove(requestGoToOxygenFactory.getRoverID());
				changeEnabledReceiveRover();
				oxygenMananger.calculateOxygen(requestGoToOxygenFactory.getRawMaterial());
				responseGoToOxygenFactory.setPermissionLevel(MessageConstant.REQUEST_OXYGEN_FACTORY_OK);
			} else {
				responseGoToOxygenFactory.setPermissionLevel(MessageConstant.REQUEST_OXYGEN_FACTORY_FULL);
				listPermission.put(requestGoToOxygenFactory.getRoverID(), requestGoToOxygenFactory);
			}

		} else {
			responseGoToOxygenFactory.setPermissionLevel(MessageConstant.REQUEST_OXYGEN_FACTORY_FULL);
			listPermission.put(requestGoToOxygenFactory.getRoverID(), requestGoToOxygenFactory);
		}
		responseGoToOxygenFactory.setRoverID(requestGoToOxygenFactory.getRoverID());
		try {

			super.updateInteraction(responseGoToOxygenFactory);
			System.out.println(responseGoToOxygenFactory);

		} catch (InteractionClassNotPublished | InteractionParameterNotDefined | InteractionClassNotDefined
				| SaveInProgress | RestoreInProgress | FederateNotExecutionMember | NotConnected | RTIinternalError
				| UpdateException e) {
			e.printStackTrace();
		}
	}

	private void clearRoverPath(String key) {
		if (map.getpathRover().containsKey(key)) {
			System.out.println("-----------------------------------");
			System.out.println(key);
			map.clearPath(key);
		}
	}

	private void changeEnabledReceiveRover() {
		enabledReceiveRover = !enabledReceiveRover;
		System.out.println("----------------------OxygenFactory------------------------------");
		System.out.println("**** Update Enable Receie Rover for " + enabledReceiveRover + " ****");
	}

	private void createOxygenFactoryRoute(RequestOxygenFactoryRoute requestOxygenFactoryRoute) {

		System.out.println(requestOxygenFactoryRoute);

		ArrayList<Position> path = new ArrayList<>();
		Position from = requestOxygenFactoryRoute.getCurrentPosition();

		path = getPath(from, PositionOfOxygenFactory.getPosition("o2fac"), requestOxygenFactoryRoute.getRoverID());

		List<Vector3D> translation = new ArrayList<>();
		if (path != null) {
			for (Position item : path) {
				translation.add(new Vector3D(item.getX(), item.getY(), item.getZ()));
			}
		} else {
			translation.add(new Vector3D(from.getX(), from.getY(), from.getZ()));
		}

		responseOxygenFactoryRoute.setPath(new PositionCollection(translation));
		responseOxygenFactoryRoute.setRoverID(requestOxygenFactoryRoute.getRoverID());

		if (!responseOxygenFactoryRoute.getRoverID().contains(FederateNameConstant.MARS_MINER))
			changeEnabledReceiveRover();

		try {
			super.updateInteraction(responseOxygenFactoryRoute);
			System.out.println(responseOxygenFactoryRoute);
		} catch (InteractionClassNotPublished | InteractionParameterNotDefined | InteractionClassNotDefined
				| SaveInProgress | RestoreInProgress | FederateNotExecutionMember | NotConnected | RTIinternalError
				| UpdateException e) {
			e.printStackTrace();
		}
	}

	private void sendPath(RequestFinderNewRoute requestFinderNewRoute) {

		System.out.println(requestFinderNewRoute);

		ArrayList<Position> path = new ArrayList<>();
		Position from = requestFinderNewRoute.getCurrentPosition();
		String target = requestFinderNewRoute.getTargetPosition();
		String key = requestFinderNewRoute.getRoverID();

		if (target.equals(MessageConstant.REQUEST_NORTH)) {
			path = getPath(from, PositionOfOxygenFactory.getPosition("north"), key);
		} else if (target.equals(MessageConstant.REQUEST_SOUTH)) {
			path = getPath(from, PositionOfOxygenFactory.getPosition("south"), key);
		} else if (target.equals(MessageConstant.REQUEST_WEST)) {
			path = getPath(from, PositionOfOxygenFactory.getPosition("west"), key);
		} else if (target.equals(MessageConstant.REQUEST_EAST)) {
			path = getPath(from, PositionOfOxygenFactory.getPosition("east"), key);
		}

		List<Vector3D> translation = new ArrayList<>();
		if (path != null) {
			for (Position item : path) {
				translation.add(new Vector3D(item.getX(), item.getY(), item.getZ()));
			}
		} else {
			translation.add(new Vector3D(from.getX(), from.getY(), from.getZ()));
		}

		responseFinderNewRoute.setPath(new PositionCollection(translation));
		responseFinderNewRoute.setRoverID(requestFinderNewRoute.getRoverID());

		try {
			super.updateInteraction(responseFinderNewRoute);
			System.out.println(responseFinderNewRoute);
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
	}// Places on the list the positions for the Rover walking
}
