package federate;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.TimeZone;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import constant.MessageConstant;
import grafo.PathFind;
import grafo.Point;
import helper.CaloriesMananger;
import helper.CarbonDioxideMananger;
import helper.EnergyConsumption;
import helper.ListAstro;
import helper.OxygenMananger;
import helper.PositionOfLuHa;
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
import hla.rti1516e.exceptions.UnsupportedCallbackModel;
import interaction.AstronautFreePath;
import interaction.AstronautPath;
import interaction.RequestPath;
import interactionClass.MTRMode;
import interactionClass.ModeTransitionRequest;
import interactionClass.RequestResource;
import interactionClass.SendResource;
import model.LunarHabitatAstronaut;
import model.MarsHabitatBase;
import model.MarsHabitatRooftop;
import model.Position;
import model.PositionCollection;
import model.RooftopStatus;
import move.RooftopStates;
import siso.smackdown.frame.FrameType;
import skf.config.Configuration;
import skf.core.SEEAbstractFederate;
import skf.core.SEEAbstractFederateAmbassador;
import skf.exception.PublishException;
import skf.exception.SubscribeException;
import skf.exception.UnsubscribeException;
import skf.exception.UpdateException;
import skf.model.interaction.annotations.InteractionClass;
import skf.model.object.annotations.ObjectClass;
import skf.model.object.executionConfiguration.ExecutionConfiguration;
import skf.model.object.executionConfiguration.ExecutionMode;
import skf.synchronizationPoint.SynchronizationPoint;

public class MarsHabitatFederate extends SEEAbstractFederate implements Observer {

	private MarsHabitatBase lunarHabitatBase = null;
	
	private static final int MAX_WAIT_TIME = 100000;

	private MarsHabitatRooftop lunarHabitatRooftop = null;

	private String LOCAL_SETTINGS_DESIGNATOR = null;

	private ModeTransitionRequest modeTransition = null;

	//private ExecutionConfiguration exco = null;

	private HLAModule hla_module = null;
	
	private SimpleDateFormat format = null;

	private AstronautPath astronautPath;

	private RequestResource requestResource;
	
	private SendResource sendResource;

	// Responsible for map of lunar habitat
	private PathFind map = null;

	private EnergyConsumption energy_consumption = null;

	
	private CaloriesMananger caloriesMananger= null;

	private ListAstro listAstro = null;

	// Responsible for maintaining the states during the movement of the rooftop
	private RooftopStates rooftopState = RooftopStates.IDLE;

	// Responsible for total control if it is allowed to rise and fall
	private boolean controle = false;
	
	private OxygenMananger oxygenMananger = null;
	
	private CarbonDioxideMananger carbonDioxideMananger = null;

	public MarsHabitatFederate(SEEAbstractFederateAmbassador seefedamb, MarsHabitatBase lunarHabitatBase,
			MarsHabitatRooftop lunarHabitatRooftop, PathFind map) throws RTIinternalError {
		
		super(seefedamb);
		this.lunarHabitatBase = lunarHabitatBase;
		this.lunarHabitatRooftop = lunarHabitatRooftop;
		this.modeTransition = new ModeTransitionRequest();
		//this.exco = new ExecutionConfiguration();
		this.hla_module = new HLAModule(seefedamb);
		this.map = map;
		this.energy_consumption = new EnergyConsumption(75, 2.5);
		this.listAstro = new ListAstro();
		this.astronautPath = new AstronautPath();
		this.requestResource = new RequestResource();
		this.sendResource = new SendResource();
		this.oxygenMananger = new OxygenMananger(lunarHabitatBase);
		this.caloriesMananger = new CaloriesMananger(lunarHabitatBase);
		this.carbonDioxideMananger = new CarbonDioxideMananger(lunarHabitatBase);
		this.format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	public void configureAndStart(Configuration config)
			throws ConnectionFailed, InvalidLocalSettingsDesignator, UnsupportedCallbackModel,
			CallNotAllowedFromWithinCallback, RTIinternalError, CouldNotCreateLogicalTimeFactory,
			FederationExecutionDoesNotExist, InconsistentFDD, ErrorReadingFDD, CouldNotOpenFDD, SaveInProgress,
			RestoreInProgress, NotConnected, MalformedURLException, FederateNotExecutionMember, InstantiationException,
			IllegalAccessException, NameNotFound, InvalidObjectClassHandle, AttributeNotDefined, ObjectClassNotDefined,
			SubscribeException, UnsubscribeException, InvalidInteractionClassHandle, InteractionClassNotDefined,
			InteractionClassNotPublished, InteractionParameterNotDefined, PublishException, InterruptedException {
		
		// 1. configure the SKF framework
		super.configure(config);
		
		// 2. Connect on RTI
		LOCAL_SETTINGS_DESIGNATOR = "crcHost=" + config.getCrcHost() + "\ncrcPort=" + config.getCrcPort();
		super.connectToRTI(LOCAL_SETTINGS_DESIGNATOR);
		
		// 3. join the SEE Federation execution
		super.joinFederationExecution();
		

		// 4. Subscribe the Subject
		super.subscribeSubject(this);
		
		// -------------------- Federate Initialization Process

		if (!SynchronizationPoint.INITIALIZATION_STARTED.isAnnounced()) {
			// 6. Wait for the announcement of the Synch-Point "initialization_completed"
			//super.waitingForSynchronization(SynchronizationPoint., max_wait_time);
			super.subscribeElement((Class<? extends ObjectClass>) ExecutionConfiguration.class);
			super.waitForElementDiscovery((Class<? extends ObjectClass>) ExecutionConfiguration.class, MAX_WAIT_TIME);

			while (super.getExecutionConfiguration() == null) {
				super.requestAttributeValueUpdate((Class<? extends ObjectClass>) ExecutionConfiguration.class);
					Thread.sleep(10);
			}

			super.publishInteraction(this.modeTransition);

						
			try {
				
				super.publishElement(lunarHabitatBase, "FACENS_LuHa_Base");
				super.publishElement(lunarHabitatRooftop, "FACENS_LuHa_Rooftop");
				super.subscribeElement((Class <? extends ObjectClass>) LunarHabitatAstronaut.class);
				super.subscribeReferenceFrame(FrameType.AitkenBasinLocalFixed);
				super.subscribeInteraction((Class <?extends InteractionClass>) AstronautFreePath.class);
				super.subscribeInteraction((Class <?extends InteractionClass>) RequestPath.class);
				super.publishInteraction(astronautPath);
				super.publishInteraction(requestResource);
				super.subscribeInteraction((Class<?extends InteractionClass>) SendResource.class);

			} catch (FederateServiceInvocationsAreBeingReportedViaMOM e) {
				e.printStackTrace();
			} catch (IllegalName e) {
				e.printStackTrace();
			} catch (ObjectInstanceNameInUse e) {
				e.printStackTrace();
			} catch (ObjectInstanceNameNotReserved e) {
				e.printStackTrace();
			} catch (ObjectClassNotPublished e) {
				e.printStackTrace();
			} catch (AttributeNotOwned e) {
				e.printStackTrace();
			} catch (ObjectInstanceNotKnown e) {
				e.printStackTrace();
			} catch (UpdateException e) {
				e.printStackTrace();
			}
			
			super.setupHLATimeManagement();

		} else
			throw new RuntimeException("The federate " + config.getFederateName() + "is not a Late Joiner Federate");
		
		super.startExecution();
					
		
		

	}

	@Override
	protected void doAction() {
		System.out.println(lunarHabitatRooftop);
		System.out.println(lunarHabitatBase);
		try {
			super.updateElement(lunarHabitatRooftop);
			super.updateElement(lunarHabitatBase);
		} catch (FederateNotExecutionMember | NotConnected | AttributeNotOwned | AttributeNotDefined
				| ObjectInstanceNotKnown | SaveInProgress | RestoreInProgress | RTIinternalError | IllegalName
				| ObjectInstanceNameInUse | ObjectInstanceNameNotReserved | ObjectClassNotPublished
				| ObjectClassNotDefined | UpdateException e) {
			e.printStackTrace();
		}
		
		
		boolean requestIsNeeded = false;		

		manageEnergy();
		carbonDioxideMananger.calculateCarbonDioxide(listAstro.getAstronauts());
		
		caloriesMananger.calculateCalories(listAstro.getAstronauts());
		requestIsNeeded = caloriesMananger.requestResource(requestResource) || requestIsNeeded;
		oxygenMananger.calculateOxygen(listAstro.getAstronauts());
		requestIsNeeded = oxygenMananger.requestResource(requestResource) || requestIsNeeded;

		if(requestIsNeeded){
			try {
				super.updateInteraction(requestResource);
			} catch (InteractionClassNotPublished | InteractionParameterNotDefined | InteractionClassNotDefined
					| SaveInProgress | RestoreInProgress | FederateNotExecutionMember | NotConnected | RTIinternalError
					| UpdateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (rooftopState.getValue().move(lunarHabitatRooftop) == true) {
			if (lunarHabitatRooftop.getRooftopStatus() == RooftopStatus.MOVING_DOWN)
				lunarHabitatRooftop.setRooftopStatus(RooftopStatus.STOPPED_DOWN);
			else if(lunarHabitatRooftop.getRooftopStatus() == RooftopStatus.MOVING_UP)
				lunarHabitatRooftop.setRooftopStatus(RooftopStatus.STOPPED_UP);
			rooftopState = RooftopStates.IDLE;
		}

	}


	private void manageEnergy() {
		double currentEnergyConsumption = this.energy_consumption
				.updateEnergyConsumption(lunarHabitatBase.getNumber_of_astrounauts());
		
		if (lunarHabitatBase.getEnergy_consumption() != currentEnergyConsumption)
			lunarHabitatBase.setEnergy_consumption(currentEnergyConsumption);			
		
	}

	@Override
	public void update(Observable o, Object arg) {
		
		if (arg instanceof SendResource) {
			SendResource sendResource = (SendResource) arg;
			oxygenMananger.putResource(sendResource.getResource());
			caloriesMananger.putResource(sendResource.getResource());
		}

		else if (arg instanceof AstronautFreePath) {
			AstronautFreePath free = (AstronautFreePath) arg;
			Vector3D vec = free.getPath().getArrayList().get(free.getPath().getArrayList().size() - 1);
			map.clearPath(new Position(vec.getX(), vec.getY(), vec.getZ()));
		}
		else if(arg instanceof RequestPath) {
			System.out.println("REQUISITOU UM PATH");
			System.out.println((RequestPath) arg);
			try {
				 
				sendPath((RequestPath) arg);
			} catch (InteractionClassNotPublished | InteractionParameterNotDefined | InteractionClassNotDefined
					| SaveInProgress | RestoreInProgress | FederateNotExecutionMember | NotConnected | RTIinternalError
					| InterruptedException | UpdateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*else if (arg instanceof LunarHabitatAstronaut) {
			if (listAstro.contains((LunarHabitatAstronaut) arg)) {
				listAstro.attAstro((LunarHabitatAstronaut) arg);
			} else {
				short numberOfAstronauts = (short) (lunarHabitatBase.getNumber_of_astrounauts() + 1);
				if (numberOfAstronauts > lunarHabitatBase.getMaximun_capacity())
					numberOfAstronauts = lunarHabitatBase.getMaximun_capacity();
				lunarHabitatBase.setNumber_of_astrounauts(numberOfAstronauts);
				//listAstro.addAstro(((LunarHabitatAstronaut) arg).getName(), new LunarHabitatAstronaut());
				listAstro.addAstro(((LunarHabitatAstronaut) arg).getName(), (LunarHabitatAstronaut) arg);
			}
		}*/
		
		else if (arg instanceof ExecutionConfiguration) {
			//System.out.println("**** Update ExecutionConfiguration ****");
			super.setExecutionConfiguration((ExecutionConfiguration) arg);
			
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
			} //else
				//System.out.println("ExecutionConfiguration status unknown");
			/* End Manage state transitions */

		}	
	}

	
	private void sendPath(RequestPath arg) throws InterruptedException, InteractionClassNotPublished,
			InteractionParameterNotDefined, InteractionClassNotDefined, SaveInProgress, RestoreInProgress,
			FederateNotExecutionMember, NotConnected, RTIinternalError, UpdateException {
		
		System.out.println(arg);

		ArrayList<Position> path = new ArrayList<>();
		Position from = arg.getCurrentPostition();
		String target = arg.getTargetRoom();

		if (target.equals(MessageConstant.REQUEST_PATH_CENTER)) {
			path = getPath(from, PositionOfLuHa.getPosition("center"));
		} else if (target.equals(MessageConstant.REQUEST_PATH_LAB)) {
			path = getPath(from, PositionOfLuHa.getPosition("lab"));
		} else if (target.equals(MessageConstant.REQUEST_PATH_MACHINE_ROOM)) {
			path = getPath(from, PositionOfLuHa.getPosition("machine_room"));
		} else if (target.equals(MessageConstant.REQUEST_PATH_ROOM)) {
			path = getPath(from, PositionOfLuHa.getPosition("room"));
		} else if (target.equals(MessageConstant.REQUEST_PATH_GREENHOUSE)) {
			path = getPath(from, PositionOfLuHa.getPosition("greenhouse"));
		}

		List<Vector3D> translation = new ArrayList<>();
		if (path != null) {
			for (Position item : path) {
				translation.add(new Vector3D(item.getX(), item.getY(), item.getZ()));
			}
		} else {
			translation.add(new Vector3D(from.getX(), from.getY(), from.getZ()));
		}

		astronautPath.setPath(new PositionCollection(translation));

		astronautPath.setAstronautId(arg.getAstronautId());
		
		super.updateInteraction(astronautPath);
	}

	public HLAModule getHLAModule() {
		return this.hla_module;
	}

	public void sendGoToShutdown() {

		modeTransition.setExecution_mode(MTRMode.MTR_GOTO_SHUTDOWN);
		try {
			super.updateInteraction(modeTransition);
		} catch (UpdateException | InteractionClassNotPublished | InteractionParameterNotDefined
				| InteractionClassNotDefined | SaveInProgress | RestoreInProgress | FederateNotExecutionMember
				| NotConnected | RTIinternalError e) {
			e.printStackTrace();
		}

	}

	public void changeControle() {
		controle = !controle;
		System.out.println("----------------------LuHaRooftop------------------------------");
		System.out.println("**** Update controle for " + controle + " ****");
	}

	public void requestBringUp() {
		if (controle == false && lunarHabitatRooftop.getRooftopStatus() == RooftopStatus.STOPPED_DOWN) {
			rooftopState = RooftopStates.UP;
			lunarHabitatRooftop.setRooftopStatus(RooftopStatus.MOVING_UP);
		}
	}// Request the roof if the Rooftop can be lifted

	public void requestDown() {

		if (controle == false && lunarHabitatRooftop.getRooftopStatus() == RooftopStatus.STOPPED_UP) {

			rooftopState = RooftopStates.DOWN;
			lunarHabitatRooftop.setRooftopStatus(RooftopStatus.MOVING_DOWN);
		}
	}// Request luHaBase if Rooftop can be let down

	public ArrayList<Position> getPath(Position from, Point to) throws InterruptedException {
		System.out.println("Pegar o caminho de: " + from + "Ate: " + to);
		ArrayList<Position> list = map.GetIn(map.convertPoint(from), to);
		System.out.println("LIST PARA O ASTRO: " + list);
		return list;
	}// Places on the list the positions for the astronaut walking

	@Override
	public String toString() {
		return "LuHa";
	}

}
