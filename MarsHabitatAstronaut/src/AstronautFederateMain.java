import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import constant.AstronautConstants;
import constant.MessageConstant;
import core.AstronautFederate;
import core.AstronautFederateAmbassador;
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
import model.Astronaut;
import skf.config.ConfigurationFactory;
import skf.exception.PublishException;
import skf.exception.SubscribeException;
import skf.exception.UnsubscribeException;

public class AstronautFederateMain {
	
	private static final File conf = new File("config/conf.json");
	private static Scanner sc = null;
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, ConnectionFailed, InvalidLocalSettingsDesignator, UnsupportedCallbackModel, CallNotAllowedFromWithinCallback, RTIinternalError, CouldNotCreateLogicalTimeFactory, FederationExecutionDoesNotExist, InconsistentFDD, ErrorReadingFDD, CouldNotOpenFDD, SaveInProgress, RestoreInProgress, NotConnected, MalformedURLException, FederateNotExecutionMember, InstantiationException, IllegalAccessException, NameNotFound, InvalidObjectClassHandle, AttributeNotDefined, ObjectClassNotDefined, InvalidInteractionClassHandle, InteractionClassNotDefined, InteractionClassNotPublished, InteractionParameterNotDefined, SubscribeException, InterruptedException, UnsubscribeException, PublishException, IOException {
		
		Astronaut astronaut = new Astronaut("facens_luha_astro_1", AstronautConstants.HABITAT_POSITION, "Astro");//new Position(-700, -350, -5581.0), "Astro");
		AstronautFederateAmbassador ambassador = new AstronautFederateAmbassador();
		AstronautFederate federate = new AstronautFederate(ambassador, astronaut);
		
		federate.configureAndStart(new ConfigurationFactory().importConfiguration(conf));
		
		sc = new Scanner(System.in);
		String currValue = null;

		while (true) {

			currValue = sc.next();
			int index = 0;
			// if 'a' Breaks the complete simulation
			if (currValue.equals("a")) {
				//federate.sendGoToShutdown();
				break;
			}

			// if 'q' Disconnect LuHaAstro from simulation
			if (currValue.equals("q")) {
				//federate.diconnectFromRTI();
				break;
			}

			// if 'r' LuHaAstro go to room
			if (currValue.charAt(0) == 'r') {
				//index = Integer.parseInt(currValue.charAt(1) + "");//Convert.toInteger(currValue.charAt(1));
				federate.requestPath(MessageConstant.REQUEST_PATH_ROOM);
			}

			// if 'l' LuHaAstro go to lab
			if (currValue.charAt(0) == 'l') {
				//index = Integer.parseInt(currValue.charAt(1) + "");//Convert.toInteger(currValue.charAt(1));
				federate.requestPath(MessageConstant.REQUEST_PATH_LAB);
			}

			// if 'h' LuHaAstro go to center a luHa
			if (currValue.charAt(0) == 'c') {
				//index = Integer.parseInt(currValue.charAt(1) + "");//Convert.toInteger(currValue.charAt(1));
				federate.requestPath(MessageConstant.REQUEST_PATH_CENTER);
			}

			// if LuHaAstro go to machine room
			if (currValue.charAt(0) == 'm') {
				//index = Integer.parseInt(currValue.charAt(1) + "");//Convert.toInteger(currValue.charAt(1));
				federate.requestPath(MessageConstant.REQUEST_PATH_MACHINE_ROOM);
			}

			// if LuHaAstro go to greenhouse
			if (currValue.charAt(0) == 'g') {
				federate.requestPath(MessageConstant.REQUEST_PATH_GREENHOUSE);
			}
		}
	}
	
	
}
