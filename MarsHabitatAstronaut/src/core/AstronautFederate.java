package core;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.TimeZone;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import constant.AstronautConstants;
import helper.ListMarsAstro;
import helper.ReferenceHabitat;
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
import model.Astronaut;
import model.LunarHabitatBase;
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

public class AstronautFederate extends SEEAbstractFederate implements Observer{

	private static final int MAX_WAIT_TIME = 10000;
	
	private String LOCAL_SETTINGS_DESIGNATOR = null;
	private ModeTransitionRequest mtr = null;
	private SimpleDateFormat format = null;
	private Astronaut astronaut = null;
	private ListMarsAstro listMarsAstro = null;
	private RequestPath requestPath = null;
	private AstronautFreePath astronautFreePath = null;
	private boolean referenceDone = false;
	private ArrayList<Position> list = null;
	
	public AstronautFederate(SEEAbstractFederateAmbassador seefedamb, Astronaut astronaut) {
		super(seefedamb);
		this.mtr = new ModeTransitionRequest();
		this.format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		this.requestPath = new RequestPath();
		this.astronautFreePath = new AstronautFreePath();
		this.astronaut = astronaut;
		this.listMarsAstro = new ListMarsAstro();
		listMarsAstro.addLuHaAstro(astronaut, 1);
	}

	@SuppressWarnings("unchecked")
	public void configureAndStart(Configuration config) throws ConnectionFailed, InvalidLocalSettingsDesignator, UnsupportedCallbackModel, CallNotAllowedFromWithinCallback, RTIinternalError, CouldNotCreateLogicalTimeFactory, 
	FederationExecutionDoesNotExist, InconsistentFDD, ErrorReadingFDD, CouldNotOpenFDD, SaveInProgress, RestoreInProgress, NotConnected, MalformedURLException, FederateNotExecutionMember, InstantiationException, 
	IllegalAccessException, NameNotFound, InvalidObjectClassHandle, AttributeNotDefined, ObjectClassNotDefined, SubscribeException, InterruptedException, UnsubscribeException, InvalidInteractionClassHandle, InteractionClassNotDefined, InteractionClassNotPublished, InteractionParameterNotDefined, PublishException {
		super.configure(config);
		
		LOCAL_SETTINGS_DESIGNATOR = "crcHost="+config.getCrcHost()+"\ncrcPort="+config.getCrcPort();
		super.connectToRTI(LOCAL_SETTINGS_DESIGNATOR);
		super.joinFederationExecution();
		super.subscribeSubject(this);
		
		if(!SynchronizationPoint.INITIALIZATION_STARTED.isAnnounced()){
			
			// 6. Wait for the announcement of the Synch-Point "initialization_completed"
			super.waitingForAnnouncement(SynchronizationPoint.INITIALIZATION_COMPLETED, MAX_WAIT_TIME);

			/* 7. Wait for announcement of "objects_discovered", and Federation
			 * Specific Mutiphase Initialization Synch-Points
			 */
			// -> skipped
			
			/* 8. Subscribe Execution Control Object Class Attributes
			 * and wait for ExCO Discovery
			 */
			super.subscribeElement((Class<? extends ObjectClass>) ExecutionConfiguration.class);
			super.waitForElementDiscovery((Class<? extends ObjectClass>) ExecutionConfiguration.class, MAX_WAIT_TIME);
			
			// 9. Request ExCO update
			while(super.getExecutionConfiguration() == null){
				super.requestAttributeValueUpdate((Class<? extends ObjectClass>) ExecutionConfiguration.class);
				Thread.sleep(10);
			}
				
			// 10. Publish MTR Interaction
			super.publishInteraction(this.mtr);
			
			try {
				
				super.subscribeElement((Class<? extends ObjectClass>) LunarHabitatBase.class);
				super.subscribeReferenceFrame(FrameType.AitkenBasinLocalFixed);
			
				super.subscribeInteraction((Class<? extends InteractionClass>) AstronautFreePath.class);	
				super.subscribeInteraction((Class<? extends InteractionClass>) AstronautPath.class);
				
				super.publishInteraction(requestPath);
				astronautFreePath.setPath(new PositionCollection(new ArrayList<>()));
				super.publishInteraction(astronautFreePath);
				
				
			} catch (FederateServiceInvocationsAreBeingReportedViaMOM e) {
					
				e.printStackTrace();
			}
			
			// 15. Wait for All Required Objects to be Discovered
			// -> Skipped
			
			/* 16. Setup HLA Time Management and Query GALT, Compute HLTB and 
			 * Time Advance to HLTB
			 */
			super.setupHLATimeManagement();
			
			// 17. Achieve "objects_discovered" Sync-Point and wait for synchronization
			// -> Skipped
		}else
			throw new RuntimeException("The federate " + config.getFederateName() + "is not a Late Joiner Federate");
		
		//18. Start simulation execution
		super.startExecution();
	}

	
	@Override
	protected void doAction() {
		
		if(!referenceDone){
			System.out.println("Waiting Habitat Location");
			return;
		}
		System.out.println(astronaut);
		list = listMarsAstro.GetArrayList(1);
		
		if(listMarsAstro.GetGoIn(1))
			goTo(1);
		
		
		try {
			super.updateElement(astronaut);
		} catch (FederateNotExecutionMember | NotConnected | AttributeNotOwned | AttributeNotDefined
				| ObjectInstanceNotKnown | SaveInProgress | RestoreInProgress | RTIinternalError | IllegalName
				| ObjectInstanceNameInUse | ObjectInstanceNameNotReserved | ObjectClassNotPublished
				| ObjectClassNotDefined | UpdateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
		if(arg1 instanceof LunarHabitatBase) {
			if(referenceDone == false){
				LunarHabitatBase habitat = (LunarHabitatBase) arg1;
				publishAstronaut(habitat);
			}
		}
		else if(arg1 instanceof AstronautPath) {
			walk((AstronautPath) arg1);
			System.out.println(arg1);
		}
		else if(arg1 instanceof ExecutionConfiguration){
			super.setExecutionConfiguration((ExecutionConfiguration) arg1);
			
			/* Manage state transitions */
			if(super.getExecutionConfiguration().getCurrent_execution_mode() == ExecutionMode.EXEC_MODE_RUNNING &&
					super.getExecutionConfiguration().getNext_execution_mode() == ExecutionMode.EXEC_MODE_FREEZE){
				super.freezeExecution();
			}
			
			else if(super.getExecutionConfiguration().getCurrent_execution_mode() == ExecutionMode.EXEC_MODE_FREEZE &&
					super.getExecutionConfiguration().getNext_execution_mode() == ExecutionMode.EXEC_MODE_RUNNING){
				super.resumeExecution();
			}
			
			else if((super.getExecutionConfiguration().getCurrent_execution_mode() == ExecutionMode.EXEC_MODE_FREEZE || 
					super.getExecutionConfiguration().getCurrent_execution_mode() == ExecutionMode.EXEC_MODE_RUNNING ) &&
					super.getExecutionConfiguration().getNext_execution_mode() == ExecutionMode.EXEC_MODE_SHUTDOWN ){
				super.shudownExecution();
			}
			else
				System.out.println("ExecutionConfiguration status unknown");
		
		}
		else
			System.out.println(arg1);
		
		
	}
	
	private void publishAstronaut(LunarHabitatBase habitat) {
		//Position position = new Position(habitat.getPosition().getX() + , 
				//habitat.getPosition().getY() + 292.5, habitat.getPosition().getZ() );//habitat.getPosition().getX(), habitat.getPosition().getY(), habitat.getPosition().getZ());
		Position position = new Position(-692.5, -347.5, -5581.0);
		astronaut = new Astronaut(AstronautConstants.MARS_ASTRONAUT, position, AstronautConstants.MARS_ASTRONAUT_TYPE);
		astronaut.setAmount_calories(220.0);
		astronaut.setAmount_carbon_dioxide(220.0);
		astronaut.setAmount_oxygen(220.0);
		astronaut.setRotation("begin");
		ReferenceHabitat.setReference(position);
		ReferenceHabitat.setReferenceRotation(habitat.getRotation());
		
		try {
			super.publishElement(astronaut, AstronautConstants.MARS_ASTRONAUT);
			referenceDone = true;
		
		} catch (NameNotFound | FederateNotExecutionMember | NotConnected | RTIinternalError | InvalidObjectClassHandle
				| AttributeNotDefined | ObjectClassNotDefined | SaveInProgress | RestoreInProgress
				| InstantiationException | IllegalAccessException | IllegalName | ObjectInstanceNameInUse
				| ObjectInstanceNameNotReserved | ObjectClassNotPublished | AttributeNotOwned | ObjectInstanceNotKnown
				| PublishException | UpdateException e) {
			e.printStackTrace();
		}
	}

	public void requestPath(String targetRoom) {
		if(!referenceDone) return;
		
		List<Position> list = new ArrayList<Position>();
		list.add(astronaut.getPosition());
		requestPath.setTargetRoom(targetRoom);
		requestPath.setAstronautId(astronaut.getName());
		requestPath.setCurrentPostition(astronaut.getPosition());
		
		System.out.println("\nASTRONAUT - REQUESTING PATH TO " + targetRoom.toUpperCase() + "\n");
		System.out.println("Current position: " + astronaut.getPosition());
		
		try {
			super.updateInteraction(requestPath);
		} catch (InteractionClassNotPublished | InteractionParameterNotDefined | InteractionClassNotDefined
				| SaveInProgress | RestoreInProgress | FederateNotExecutionMember | NotConnected | RTIinternalError
				| UpdateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void goTo(int index) {
		Position begin = astronaut.getPosition();
		astronaut.setPosition(list.get(0));
		Position end = astronaut.getPosition();
		System.out.println("x: "+end.getX()+"y: "+end.getY()+"z"+end.getZ());
		
		list.remove(0);
		
		if(list.isEmpty()) {
			list = null;
			listMarsAstro.setGoIn(1, false);
			sendPathDone();
			astronaut.idle();
		}
	}
	
	private void sendPathDone() {
		Astronaut astronaut = listMarsAstro.GetLuHaAstro(1);
		ArrayList<Position> position = new ArrayList<Position>();
		position.add(astronaut.getPosition());
		
		System.out.println("----------------------" + astronaut.getName() + "------------------------------");
		astronautFreePath.setAstronautId(astronaut.getName());
		astronautFreePath.setPath(new PositionCollection(Arrays.asList(new Vector3D(
				astronaut.getPosition().getX(),
				astronaut.getPosition().getY(),
				astronaut.getPosition().getZ()))));
		
		try {
			super.updateInteraction(astronautFreePath);
		} catch (InteractionClassNotPublished | InteractionParameterNotDefined | InteractionClassNotDefined
				| SaveInProgress | RestoreInProgress | FederateNotExecutionMember | NotConnected | RTIinternalError
				| UpdateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void walk(AstronautPath astronaut_path) {
		if( !astronaut_path.equals(null) && astronaut_path.getAstronautId().equals(astronaut.getName())){
			ArrayList<Position> path = new ArrayList<>();
			List<Vector3D> temp = astronaut_path.getPath().getArrayList();
			int size = temp.size();
			
			for(int i = 0; i < size; i++) {
				path.add(new Position(temp.get(i).getX(), temp.get(i).getY(), temp.get(i).getZ()));
			}
			
			listMarsAstro.setArrayList(1, path);
			listMarsAstro.setGoIn(1, true);
			astronaut.itsWaking();
			
			Position from = path.get(0);
			Position to = path.get(path.size() - 1);
			
			astronaut.setAmount_calories(calculeCalorie(from.getX(), to.getX(), from.getY(), to.getY(), path.size()));
			
		}
	}
	
	private Double calculeCalorie(double x0, double x1, double y0, double y1, int t) {
		double pot = 0;
		double job = 0;
		pot = ((80 * Math.pow((Math.sqrt(Math.pow((x1 - x0), 2) + Math.pow((y1 - y0), 2)) / (t)), 2)) / 2) + 80;
		if (pot > 120)
			astronaut.setHeart_rate((int) (80 + (pot - 120) / 100));
		job = pot * t / 4.18;
		return job / t;
	}
	
}
