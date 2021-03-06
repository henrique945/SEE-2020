import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import constant.SpaceElevatorConstant;
import core.SpaceElevatorFederate;
import core.SpaceElevatorFederateAmbassador;
import hla.rti1516e.exceptions.AttributeNotDefined;
import hla.rti1516e.exceptions.CallNotAllowedFromWithinCallback;
import hla.rti1516e.exceptions.ConnectionFailed;
import hla.rti1516e.exceptions.CouldNotCreateLogicalTimeFactory;
import hla.rti1516e.exceptions.CouldNotOpenFDD;
import hla.rti1516e.exceptions.ErrorReadingFDD;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.FederationExecutionDoesNotExist;
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
import hla.rti1516e.exceptions.RTIinternalError;
import hla.rti1516e.exceptions.RestoreInProgress;
import hla.rti1516e.exceptions.SaveInProgress;
import hla.rti1516e.exceptions.UnsupportedCallbackModel;
import model.Position;
import model.SpaceElevator;
import model.SpaceElevatorLauncher;
import skf.config.ConfigurationFactory;
import skf.exception.PublishException;
import skf.exception.SubscribeException;
import skf.exception.UnsubscribeException;

public class SpaceElevatorMain {

	private static final File conf = new File("config/conf.json");
	private static Scanner sc = null;
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, ConnectionFailed, InvalidLocalSettingsDesignator, UnsupportedCallbackModel, CallNotAllowedFromWithinCallback, RTIinternalError, CouldNotCreateLogicalTimeFactory, FederationExecutionDoesNotExist, InconsistentFDD, ErrorReadingFDD, CouldNotOpenFDD, SaveInProgress, RestoreInProgress, NotConnected, MalformedURLException, FederateNotExecutionMember, InstantiationException, IllegalAccessException, NameNotFound, InvalidObjectClassHandle, AttributeNotDefined, ObjectClassNotDefined, InvalidInteractionClassHandle, InteractionClassNotDefined, InteractionClassNotPublished, InteractionParameterNotDefined, SubscribeException, InterruptedException, UnsubscribeException, PublishException, IOException {
		
		//BASE
		SpaceElevator spaceElevator = new SpaceElevator("FACENS_SpaceElevator_Base", 
				siso.smackdown.frame.FrameType.AitkenBasinLocalFixed.toString(), "Space Elevator", 
				200.0, 200.0, 200.0, 
				new Position(SpaceElevatorConstant.SPACE_ELEVATOR.getX(), 
						SpaceElevatorConstant.SPACE_ELEVATOR.getY(),
						SpaceElevatorConstant.SPACE_ELEVATOR.getZ()));
		
		
		//ROCKET
		SpaceElevatorLauncher rocket = new SpaceElevatorLauncher("FACENS_SpaceElevator_Elevator", 
				siso.smackdown.frame.FrameType.AitkenBasinLocalFixed.toString(), "Space Elevator Launcher", 
				new Position(SpaceElevatorConstant.LAUNCHER_LOWEST_POSITION.getX(), 
						SpaceElevatorConstant.LAUNCHER_LOWEST_POSITION.getY(),
						SpaceElevatorConstant.LAUNCHER_LOWEST_POSITION.getZ()));
		
		SpaceElevatorFederateAmbassador ambassador = new SpaceElevatorFederateAmbassador();
		SpaceElevatorFederate federate = new SpaceElevatorFederate(ambassador, spaceElevator, rocket);
				
		federate.configureAndStart(new ConfigurationFactory().importConfiguration(conf));
		
		sc = new Scanner(System.in);
		String currValue = null;
		while (true) {

			currValue = sc.next();

			// if 'u' is pressed, the Launcher move up
			if (currValue.equals("u")) {
				federate.requestBringUp();
			}
			// if 'd' is pressed, the Launcher go move Down
			if (currValue.equals("d")) {
				federate.requestDown();
			}
			// if 'c' to lost the control of the Launcher or gain
			if (currValue.equals("c")) {
				federate.changeControle();
			}
		}


	}

}
