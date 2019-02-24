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
import interaction.FinishFinderNewRoute;
import interaction.FinishOxygenFactoryRoute;
import interaction.RequestGoToOxygenFactory;
import interaction.RequestOxygenFactoryRoute;
import interaction.ResponseFinderNewRoute;
import interaction.ResponseGoToOxygenFactory;
import interaction.ResponseOxygenFactoryRoute;
import interaction.RoverFreePath;
import interaction.RoverPath;
import interaction.RoverRequestPath;

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

import constant.MessageConstant;
import helper.ListMarsMiner;
import helper.ReferenceMine;
import model.ExcavationMine;
import model.MarsMiner;
import model.MarsMinerStatus;
import model.OxygenFactory;
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

public class MarsMinerFederate extends SEEAbstractFederate implements Observer {

	private static final int MAX_WAIT_TIME = 10000;

	// private MarsMiner marsMiner = null;

	// Responsible for the manipulation of all the instances of MarsMiner
	private ListMarsMiner listMarsMiner = null;

	// Responsible for the temporary storage of the path by MarsMiner
	private ArrayList<Position> list = null;

	private RoverRequestPath roverRequestPath = null;

	private RoverFreePath roverFreePath = null;

	private RequestOxygenFactoryRoute requestOxygenFactoryRoute = null;

	private RequestGoToOxygenFactory requestGoToOxygenFactory = null;

	private FinishOxygenFactoryRoute finishOxygenFactoryRoute = null;

	private FinishFinderNewRoute finishFinderNewRoute = null;

	private String local_settings_designator = null;

	private ModeTransitionRequest mtr = null;

	private SimpleDateFormat format = null;

	private int referenceDone = 0;

	public MarsMinerFederate(SEEAbstractFederateAmbassador seefedamb, ListMarsMiner listMarsMiner) {
		super(seefedamb);
		this.listMarsMiner = listMarsMiner;
		this.mtr = new ModeTransitionRequest();
		this.roverRequestPath = new RoverRequestPath();
		this.roverFreePath = new RoverFreePath();
		this.requestOxygenFactoryRoute = new RequestOxygenFactoryRoute();
		this.requestGoToOxygenFactory = new RequestGoToOxygenFactory();
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
			/*
			 * for (MarsMiner a : listMarsMiner.getMapMarsMiner().values()) {
			 * super.publishElement(a, a.getName()); referenceDone++; }
			 */

			try {

				super.subscribeElement((Class<? extends ObjectClass>) ExcavationMine.class);
				super.subscribeElement((Class<? extends ObjectClass>) OxygenFactory.class);

				super.subscribeReferenceFrame(FrameType.AitkenBasinLocalFixed);

				// subscribe class interaction
				super.subscribeInteraction((Class<? extends InteractionClass>) RoverPath.class);
				super.subscribeInteraction((Class<? extends InteractionClass>) ResponseOxygenFactoryRoute.class);
				super.subscribeInteraction((Class<? extends InteractionClass>) ResponseGoToOxygenFactory.class);
				super.subscribeInteraction((Class<? extends InteractionClass>) ResponseFinderNewRoute.class);

				// public class interaction
				super.publishInteraction(roverRequestPath);
				roverFreePath.setPath(new PositionCollection(new ArrayList<>()));
				super.publishInteraction(roverFreePath);
				super.publishInteraction(requestGoToOxygenFactory);
				super.publishInteraction(requestOxygenFactoryRoute);
				super.publishInteraction(finishOxygenFactoryRoute);
				super.publishInteraction(finishFinderNewRoute);

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
		
		String notification = ReferenceMine.reference == null ? "Awaiting Localizacação of the excavation mine"
				: Integer.toString(listMarsMiner.size()) + " mine miners located in the mine";
		System.out.println(notification);
		
		if (referenceDone != listMarsMiner.size())
			return;
		System.out.println(listMarsMiner);
		// Checks all instances with in the list
		for (int index = 1; index <= listMarsMiner.size(); index++) {
			MarsMiner marsMiner = listMarsMiner.GetMarsMiner(index);
			list = listMarsMiner.GetArrayList(index);
			if (list.isEmpty()) {
				if (marsMiner.getTimeLimitRequest() != -1) {
					if (marsMiner.getTimeLimitRequest() == 0)
						sendRequestOxygenFactory(marsMiner);
					else if (marsMiner.getTimeLimitRequest() > 0) {
						marsMiner.setTimeLimitRequest(marsMiner.getTimeLimitRequest() - 1);
					}
				} else if (marsMiner.getMars_miner_status().equals(MarsMinerStatus.FULL_LOAD)) {
					requestPath(MessageConstant.REQUEST_O2FAC, index);
					marsMiner.setTimeLimitRequest(-2);
				} else if (listMarsMiner.isEnableRequestPath()) {
					String request_temp = listMarsMiner.GetTargetPosition(index).getLastIndex();
					requestPath(request_temp, index);
				}
			} else if (listMarsMiner.GetGoIn(index)) {
				goTo(index);
			}

			try {
//				System.out.println(marsMiner);
				super.updateElement(marsMiner);

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

		if (arg1 instanceof ExcavationMine) {

			if (ReferenceMine.reference == null) {
				publishMarsMiners((ExcavationMine) arg1);
			}
		} // If reference null get position of ExcavationMine

		else if (arg1 instanceof RoverPath) {
			RoverPath roverPath = (RoverPath) arg1;
			if (roverPath.getPath().getArrayList().isEmpty()) {
				// Checks all instances with in the list
				for (int index = 1; index <= listMarsMiner.size(); index++) {
					MarsMiner marsMiner = listMarsMiner.GetMarsMiner(index);
					if (marsMiner.getName().equals(roverPath.getRoverID())) {
						requestPath(marsMiner.getTarget(), index);
					}
				}
			} else {
				walk(roverPath.getPath(), roverPath.getRoverID(), false);
			}
		}

		else if (arg1 instanceof ResponseGoToOxygenFactory) {
			receiveRequestOxygenFactory((ResponseGoToOxygenFactory) arg1);
		}

		else if (arg1 instanceof ResponseOxygenFactoryRoute) {
			ResponseOxygenFactoryRoute responseOxygenFactoryRoute = (ResponseOxygenFactoryRoute) arg1;
			walk(responseOxygenFactoryRoute.getPath(), responseOxygenFactoryRoute.getRoverID(), true);
			for (int index = 1; index <= listMarsMiner.size(); index++) {
				MarsMiner marsMiner = listMarsMiner.GetMarsMiner(index);
				if (marsMiner.getName().equals(responseOxygenFactoryRoute.getRoverID())) {
					marsMiner.setTimeLimitRequest(-1);
				}
			}

		}

		else if (arg1 instanceof ResponseFinderNewRoute) {
			ResponseFinderNewRoute responseFinderNewRoute = (ResponseFinderNewRoute) arg1;
			walk(responseFinderNewRoute.getPath(), responseFinderNewRoute.getRoverID(), false);
			for (int index = 1; index <= listMarsMiner.size(); index++) {
				MarsMiner marsMiner = listMarsMiner.GetMarsMiner(index);
				if (marsMiner.getName().equals(responseFinderNewRoute.getRoverID())) {
					marsMiner.setReturno2fac(true);
				}
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

	private void sendPathDone(int index) {
		MarsMiner marsMiner = listMarsMiner.GetMarsMiner(index);
		if (marsMiner.getTimeLimitRequest() == -2)
			marsMiner.setTimeLimitRequest(0);

		try {
			ArrayList<Position> pos = new ArrayList<Position>();
			pos.add(marsMiner.getPosition());

			System.out.println("----------------------" + marsMiner.getName() + "     Send Path Done");

			if (marsMiner.isReturno2fac()) {
				SendFinishFinderNewRoute(marsMiner);
			} else if (marsMiner.getMars_miner_status().equals(MarsMinerStatus.UNLOADING)
					&& marsMiner.getTimeLimitRequest() == -1) {
				SendFinishOxygenFactoryRoute(marsMiner);
			} else {
				// Random mass ore found for mars miner.
				Double ore_found = (double) new Random().nextInt(20);

				if (ore_found + marsMiner.getLoaded_materials() < marsMiner.getMaximun_capacity()) {
					marsMiner.setMars_miner_status(MarsMinerStatus.LOADING);
					marsMiner.setLoaded_materials(marsMiner.getLoaded_materials() + ore_found);
				} else {
					marsMiner.setMars_miner_status(MarsMinerStatus.FULL_LOAD);

				}
				roverFreePath.setRoverId(marsMiner.getName());
				roverFreePath.setPath(new PositionCollection(Arrays.asList(new Vector3D(marsMiner.getPosition().getX(),
						marsMiner.getPosition().getY(), marsMiner.getPosition().getZ()))));

				super.updateInteraction(roverFreePath);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}// send for Mine interaction with message path done

	private void SendFinishFinderNewRoute(MarsMiner marsMiner) {
		finishFinderNewRoute.setRoverId(marsMiner.getName());
		finishFinderNewRoute.setPath(new PositionCollection(Arrays.asList(new Vector3D(marsMiner.getPosition().getX(),
				marsMiner.getPosition().getY(), marsMiner.getPosition().getZ()))));

		try {
			super.updateInteraction(finishFinderNewRoute);
		} catch (InteractionClassNotPublished | InteractionParameterNotDefined | InteractionClassNotDefined
				| SaveInProgress | RestoreInProgress | FederateNotExecutionMember | NotConnected | RTIinternalError
				| UpdateException e) {
			e.printStackTrace();
		}
		marsMiner.setReturno2fac(false);
	}

	private void SendFinishOxygenFactoryRoute(MarsMiner marsMiner) {
		marsMiner.setMars_miner_status(MarsMinerStatus.EMPTY_LOAD);
		marsMiner.setLoaded_materials(0.0);

		finishOxygenFactoryRoute.setRoverId(marsMiner.getName());
		finishOxygenFactoryRoute
				.setPath(new PositionCollection(Arrays.asList(new Vector3D(marsMiner.getPosition().getX(),
						marsMiner.getPosition().getY(), marsMiner.getPosition().getZ()))));

		try {
			super.updateInteraction(finishOxygenFactoryRoute);
			System.out.println(finishOxygenFactoryRoute);
		} catch (InteractionClassNotPublished | InteractionParameterNotDefined | InteractionClassNotDefined
				| SaveInProgress | RestoreInProgress | FederateNotExecutionMember | NotConnected | RTIinternalError
				| UpdateException e) {
			e.printStackTrace();
		}
	}

	private void goTo(int index) {
		MarsMiner marsMiner = listMarsMiner.GetMarsMiner(index);
		Position begin = marsMiner.getPosition();
		marsMiner.setPosition(list.get(0));
		Position end = marsMiner.getPosition();
		System.out.println("x: " + end.getX() + "       y: " + end.getY() + "       z" + end.getZ());
		marsMiner.setRotation(begin.subtraction(end));
		list.remove(0);
		listMarsMiner.setArrayList(index, list);

		System.out.println("----------------         " + list.size());

		if (list.isEmpty()) {
			listMarsMiner.setGoIn(index, false);
			sendPathDone(index);
			marsMiner.isIdle();
		}

	}// The marsMiner update his position to the first position of the list

	private void publishMarsMiners(ExcavationMine excavationMine) {

		ReferenceMine.setReference(new Position(excavationMine.getPosition().getX(),
				excavationMine.getPosition().getY(), excavationMine.getPosition().getZ()));

		ReferenceMine.referenceRotation = excavationMine.getRotation();

		for (int i = 1; i <= listMarsMiner.size(); i++) {

			MarsMiner marsMiner = listMarsMiner.GetMarsMiner(i);

			if (!marsMiner.isReferenced()) {

				Position minePosition = ReferenceMine.reference;
				Position position = new Position(minePosition.getX(), minePosition.getY() + (i * 3),
						minePosition.getZ());
				marsMiner.setRotation("begin");
				marsMiner.setPosition(position);
				try {
					super.publishElement(marsMiner, marsMiner.getName());

					marsMiner.setReferenced(true);
					referenceDone++;
					System.out.println("Mars Miner " + marsMiner + " in the Mine!");
					listMarsMiner.setArrayList(i, new ArrayList<Position>());

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

	public void requestPath(String local, int index) {
		if (referenceDone < listMarsMiner.size())
			return;
		MarsMiner marsMiner = listMarsMiner.GetMarsMiner(index);

		try {

			ArrayList<Position> l = new ArrayList<Position>();
			l.add(marsMiner.getPosition());
			roverRequestPath.setTargetPosition(local);
			roverRequestPath.setRoverID(marsMiner.getName());
			roverRequestPath.setCurrentPosition(marsMiner.getPosition());

			System.out.println(

					"----------------------" + marsMiner.getName() + "      Request Path");

			super.updateInteraction(roverRequestPath);
			System.out.println(roverRequestPath);
			marsMiner.setTarget(local);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// send for ExacavationMine request for path to destine

	private void walk(PositionCollection path, String roverID, boolean isRouteOxygenFactory) {

		for (int i = 1; i <= listMarsMiner.size(); i++) {
			MarsMiner marsMiner = listMarsMiner.GetMarsMiner(i);
			if (marsMiner == null)
				continue;
			if (marsMiner.getName().equals(roverID)) {
				if (isRouteOxygenFactory)
					marsMiner.setMars_miner_status(MarsMinerStatus.UNLOADING);

				ArrayList<Position> array = new ArrayList<>();
				List<Vector3D> l = path.getArrayList();
				for (int j = 0; j < path.getArrayList().size(); j++) {
					array.add(new Position(l.get(j).getX(), l.get(j).getY(), l.get(j).getZ()));
				}
				listMarsMiner.setArrayList(i, array);
				listMarsMiner.setGoIn(i, true);
				listMarsMiner.setEnableRequestPath(true);
				marsMiner.isWalking();
			}
		}
	}

	private void sendRequestOxygenFactory(MarsMiner marsMiner) {
		requestGoToOxygenFactory.setRoverID(marsMiner.getName());
		requestGoToOxygenFactory.setRawMaterial(marsMiner.getLoaded_materials());

		try {
			super.updateInteraction(requestGoToOxygenFactory);
		} catch (InteractionClassNotPublished | InteractionParameterNotDefined | InteractionClassNotDefined
				| SaveInProgress | RestoreInProgress | FederateNotExecutionMember | NotConnected | RTIinternalError
				| UpdateException e) {
			e.printStackTrace();
		}

		System.out.println(requestGoToOxygenFactory);
	}

	private void receiveRequestOxygenFactory(ResponseGoToOxygenFactory responseGoToOxygenFactory) {
		System.out.println(responseGoToOxygenFactory);
		for (int i = 1; i <= listMarsMiner.size(); i++) {
			MarsMiner marsMiner = listMarsMiner.GetMarsMiner(i);
			if (marsMiner == null)
				continue;
			if (marsMiner.getName().equals(responseGoToOxygenFactory.getRoverID())) {
				if (responseGoToOxygenFactory.getPermissionLevel()
						.equals(MessageConstant.REQUEST_OXYGEN_FACTORY_FULL)) {
					marsMiner.setTimeLimitRequest(10);
				} else if (responseGoToOxygenFactory.getPermissionLevel()
						.equals(MessageConstant.REQUEST_OXYGEN_FACTORY_OK)) {
					requestOxygenFactoryRoute.setRoverID(marsMiner.getName());
					requestOxygenFactoryRoute.setCurrentPosition(marsMiner.getPosition());
					System.out.println("-------------------------" + marsMiner.getPosition());
					try {
						super.updateInteraction(requestOxygenFactoryRoute);

					} catch (InteractionClassNotPublished | InteractionParameterNotDefined | InteractionClassNotDefined
							| SaveInProgress | RestoreInProgress | FederateNotExecutionMember | NotConnected
							| RTIinternalError | UpdateException e) {
						e.printStackTrace();
					}
					marsMiner.setTimeLimitRequest(-1);
					System.out.println(requestOxygenFactoryRoute);
				}
			}
		}
	}
}
