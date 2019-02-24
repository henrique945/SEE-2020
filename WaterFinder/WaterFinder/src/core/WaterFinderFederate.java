package core;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.TimeZone;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import helper.ListWaterFinder;
import helper.ReferenceOygenFactory;
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
import interaction.ResponseFinderNewRoute;
import interaction.ResponseOxygenFactoryRoute;
import interaction.FinishFinderNewRoute;
import interaction.FinishOxygenFactoryRoute;
import interaction.RequestFinderNewRoute;
import interaction.RequestOxygenFactoryRoute;
import model.OxygenFactory;
import model.Position;
import model.PositionCollection;
import model.WaterFinder;
import model.WaterFinderStatus;
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

public class WaterFinderFederate extends SEEAbstractFederate implements Observer {

	private static final int MAX_WAIT_TIME = 10000;

	// Responsible for the manipulation of all the instances of WaterFinder
	private ListWaterFinder listWaterFinder = null;

	// Responsible for the temporary storage of the path by WaterFinder
	private ArrayList<Position> list = null;

	private RequestFinderNewRoute requestFinderNewRoute = null;

	private RequestOxygenFactoryRoute requestOxygenFactoryRoute = null;

	private FinishOxygenFactoryRoute finishOxygenFactoryRoute = null;

	private FinishFinderNewRoute finishFinderNewRoute = null;

	private String local_settings_designator = null;

	private ModeTransitionRequest mtr = null;

	private SimpleDateFormat format = null;

	private int referenceDone = 0;

	public WaterFinderFederate(SEEAbstractFederateAmbassador seefedamb, ListWaterFinder listWaterFinder) {
		super(seefedamb);
		this.listWaterFinder = listWaterFinder;
		this.mtr = new ModeTransitionRequest();
		this.requestFinderNewRoute = new RequestFinderNewRoute();
		this.requestOxygenFactoryRoute = new RequestOxygenFactoryRoute();
		this.finishOxygenFactoryRoute = new FinishOxygenFactoryRoute();
		this.finishFinderNewRoute = new FinishFinderNewRoute();
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
				super.subscribeElement((Class<? extends ObjectClass>) OxygenFactory.class);

				super.subscribeReferenceFrame(FrameType.AitkenBasinLocalFixed);

				// subscribe class interaction
				super.subscribeInteraction((Class<? extends InteractionClass>) ResponseFinderNewRoute.class);
				super.subscribeInteraction((Class<? extends InteractionClass>) ResponseOxygenFactoryRoute.class);

				// public class interaction
				super.publishInteraction(requestFinderNewRoute);
				super.publishInteraction(requestOxygenFactoryRoute);
				super.publishInteraction(finishFinderNewRoute);
				super.publishInteraction(finishOxygenFactoryRoute);

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
		String notification = ReferenceOygenFactory.reference == null ? "Awaiting Localizacação of the oxygen factory"
				: Integer.toString(listWaterFinder.size()) + " water finders located in the  oxygen factory";
		System.out.println(notification);

		if (referenceDone != listWaterFinder.size())
			return;

		// Checks all instances with in the list
		for (int index = 1; index <= listWaterFinder.size(); index++) {
			WaterFinder waterFinder = listWaterFinder.GetWaterFinder(index);
			list = listWaterFinder.GetArrayList(index);

			if (list.isEmpty()) {
				if (waterFinder.getWater_finder_status().equals(WaterFinderStatus.FULL_LOAD)) {
					sendRequestOxygenFactoryRoute(index);
				} else if (listWaterFinder.isEnableRequestPath()) {
					String request_temp = listWaterFinder.GetTargetPosition(index).getLastIndex();
					sendRequestFinderNewRoute(request_temp, index);
				}
			} else if (listWaterFinder.GetGoIn(index)) {
				goTo(index);
			}

			try {
//				System.out.println(waterFinder);
				super.updateElement(waterFinder);

			} catch (FederateNotExecutionMember | NotConnected | AttributeNotOwned | AttributeNotDefined
					| ObjectInstanceNotKnown | SaveInProgress | RestoreInProgress | RTIinternalError | IllegalName
					| ObjectInstanceNameInUse | ObjectInstanceNameNotReserved | ObjectClassNotPublished
					| ObjectClassNotDefined | UpdateException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void update(Observable arg0, Object arg1) {

		if (arg1 instanceof OxygenFactory) {

			if (ReferenceOygenFactory.reference == null) {
				publishWaterFinder((OxygenFactory) arg1);
			}
		} // If reference null get position of OxygenFactory

		else if (arg1 instanceof ResponseFinderNewRoute) {
			System.out.println((ResponseFinderNewRoute) arg1);
			walk(((ResponseFinderNewRoute) arg1).getPath(), ((ResponseFinderNewRoute) arg1).getRoverID(), false);
		}

		else if (arg1 instanceof ResponseOxygenFactoryRoute) {
			System.out.println((ResponseOxygenFactoryRoute) arg1);
			walk(((ResponseOxygenFactoryRoute) arg1).getPath(), ((ResponseOxygenFactoryRoute) arg1).getRoverID(), true);
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

	private void goTo(int index) {
		WaterFinder waterFinder = listWaterFinder.GetWaterFinder(index);
		Position begin = waterFinder.getPosition();
		waterFinder.setPosition(list.get(0));
		Position end = waterFinder.getPosition();
		System.out.println("x: " + end.getX() + "       y: " + end.getY() + "       z" + end.getZ());
		waterFinder.setRotation(begin.subtraction(end));
		list.remove(0);
		listWaterFinder.setArrayList(index, list);

		if (list.isEmpty()) {
			listWaterFinder.setGoIn(index, false);
			sendPathDone(index);
			waterFinder.isIdle();
		}

	}// The waterFinder update his position to the first position of the list

	private void publishWaterFinder(OxygenFactory oxygenFactory) {

		ReferenceOygenFactory.setReference(new Position(oxygenFactory.getPosition().getX(),
				oxygenFactory.getPosition().getY(), oxygenFactory.getPosition().getZ()));

		ReferenceOygenFactory.referenceRotation = oxygenFactory.getRotation();

		for (int i = 1; i <= listWaterFinder.size(); i++) {

			WaterFinder waterFinder = listWaterFinder.GetWaterFinder(i);

			if (!waterFinder.isReferenced()) {

				Position oygenFactoryPosition = ReferenceOygenFactory.reference;
				Position position = new Position(oygenFactoryPosition.getX(), oygenFactoryPosition.getY() - (i * 3),
						oygenFactoryPosition.getZ());
				waterFinder.setRotation("begin");
				waterFinder.setPosition(position);
				try {
					super.publishElement(waterFinder, waterFinder.getName());

					waterFinder.setReferenced(true);
					referenceDone++;
					System.out.println("Water Finder " + waterFinder + " in the Oxygen Factory!");
					listWaterFinder.addArrayList(new ArrayList<>(), i);

				} catch (FederateNotExecutionMember | NotConnected | AttributeNotOwned | AttributeNotDefined
						| ObjectInstanceNotKnown | SaveInProgress | RestoreInProgress | RTIinternalError | IllegalName
						| ObjectInstanceNameInUse | ObjectInstanceNameNotReserved | ObjectClassNotPublished
						| ObjectClassNotDefined | UpdateException e) {
					e.printStackTrace();
				} catch (NameNotFound e) {
					e.printStackTrace();
				} catch (InvalidObjectClassHandle e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (PublishException e) {
					e.printStackTrace();
				}

			}
		}

	}

	public void sendRequestOxygenFactoryRoute(int index) {
		if (referenceDone < listWaterFinder.size())
			return;
		WaterFinder waterFinder = listWaterFinder.GetWaterFinder(index);

		try {

			ArrayList<Position> l = new ArrayList<Position>();
			l.add(waterFinder.getPosition());
			requestOxygenFactoryRoute.setRoverID(waterFinder.getName());
			requestOxygenFactoryRoute.setCurrentPosition(waterFinder.getPosition());

			System.out.println("----------------------" + waterFinder.getName() + "      Request Path");

			super.updateInteraction(requestOxygenFactoryRoute);
			System.out.println(requestOxygenFactoryRoute);
			waterFinder.setReturno2fac(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// send for OxygenFactory request for path to destine

	public void sendRequestFinderNewRoute(String local, int index) {
		if (referenceDone < listWaterFinder.size())
			return;
		WaterFinder waterFinder = listWaterFinder.GetWaterFinder(index);

		try {

			ArrayList<Position> l = new ArrayList<Position>();
			l.add(waterFinder.getPosition());
			requestFinderNewRoute.setTargetPosition(local);
			requestFinderNewRoute.setRoverID(waterFinder.getName());
			requestFinderNewRoute.setCurrentPosition(waterFinder.getPosition());

			System.out.println("----------------------" + waterFinder.getName() + "      Request Path");

			super.updateInteraction(requestFinderNewRoute);
			System.out.println(requestFinderNewRoute);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// send for OxygenFactory request for path to destine

	private void walk(PositionCollection path, String roverID, boolean isRouteOxygenFactory) {

		for (int index = 1; index <= listWaterFinder.size(); index++) {
			WaterFinder waterFinder = listWaterFinder.GetWaterFinder(index);
			if (waterFinder == null)
				continue;
			if (waterFinder.getName().equals(roverID)) {

				if (isRouteOxygenFactory)
					waterFinder.setWater_finder_status(WaterFinderStatus.UNLOADING);

				ArrayList<Position> array = new ArrayList<>();
				List<Vector3D> l = path.getArrayList();
				for (int j = 0; j < path.getArrayList().size(); j++) {
					array.add(new Position(l.get(j).getX(), l.get(j).getY(), l.get(j).getZ()));
				}
				listWaterFinder.setArrayList(index, array);
				listWaterFinder.setGoIn(index, true);
				listWaterFinder.setEnableRequestPath(true);
				waterFinder.isWalking();
			}
		}

	}

	private void sendPathDone(int index) {
		WaterFinder waterFinder = listWaterFinder.GetWaterFinder(index);

		try {
			ArrayList<Position> pos = new ArrayList<Position>();
			pos.add(waterFinder.getPosition());

			System.out.println("----------------------" + waterFinder.getName() + "     Send Path Done");

			if (waterFinder.getWater_finder_status().equals(WaterFinderStatus.UNLOADING)) {
				waterFinder.setWater_finder_status(WaterFinderStatus.EMPTY_LOAD);
				waterFinder.setRemaining_tubes(0);
			} else {
				// Random water tubes found for water finder
				int water_found = (int) new Random().nextInt(10);

				if (water_found + waterFinder.getRemaining_tubes() < waterFinder.getTotal_tubes()) {
					waterFinder.setWater_finder_status(WaterFinderStatus.LOADING);
					waterFinder.setRemaining_tubes(waterFinder.getRemaining_tubes() + water_found);
				} else {
					waterFinder.setWater_finder_status(WaterFinderStatus.FULL_LOAD);
				}
			}
			if (waterFinder.isReturno2fac()) {
				finishOxygenFactoryRoute.setRoverId(waterFinder.getName());
				finishOxygenFactoryRoute
						.setPath(new PositionCollection(Arrays.asList(new Vector3D(waterFinder.getPosition().getX(),
								waterFinder.getPosition().getY(), waterFinder.getPosition().getZ()))));

				super.updateInteraction(finishOxygenFactoryRoute);
			} else {
				finishFinderNewRoute.setRoverId(waterFinder.getName());
				finishFinderNewRoute
						.setPath(new PositionCollection(Arrays.asList(new Vector3D(waterFinder.getPosition().getX(),
								waterFinder.getPosition().getY(), waterFinder.getPosition().getZ()))));

				super.updateInteraction(finishFinderNewRoute);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		waterFinder.setReturno2fac(false);
	}// send for Mine interaction with message path done

}
