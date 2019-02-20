package core;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.TimeZone;

import hla.rti1516e.exceptions.AttributeNotDefined;
import hla.rti1516e.exceptions.AttributeNotOwned;
import hla.rti1516e.exceptions.CallNotAllowedFromWithinCallback;
import hla.rti1516e.exceptions.ConnectionFailed;
import hla.rti1516e.exceptions.CouldNotCreateLogicalTimeFactory;
import hla.rti1516e.exceptions.CouldNotOpenFDD;
import hla.rti1516e.exceptions.ErrorReadingFDD;
import hla.rti1516e.exceptions.FederateNotExecutionMember;
import hla.rti1516e.exceptions.FederationExecutionDoesNotExist;
import hla.rti1516e.exceptions.IllegalName;
import hla.rti1516e.exceptions.InconsistentFDD;
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
import model.Position;
import model.SpaceProbe;
import siso.smackdown.frame.FrameType;
import skf.config.Configuration;
import skf.core.SEEAbstractFederate;
import skf.core.SEEAbstractFederateAmbassador;
import skf.exception.PublishException;
import skf.exception.UpdateException;

public class SpaceProbeFederate extends SEEAbstractFederate implements Observer {
	
	// Responsible for the manipulation of SpaceProbe
	private SpaceProbe spaceProbe = null;  
	
	// Responsible for the temporary storage of the path by SpaceProbe
	private String local_settings_designator = null;
	private SimpleDateFormat format = null;

	public SpaceProbeFederate(SEEAbstractFederateAmbassador seefedamb, SpaceProbe spaceProbe) {
		super(seefedamb);
		this.spaceProbe = spaceProbe;
		this.format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
	}
	
	@SuppressWarnings("resource")
	public void configureAndStart(Configuration config) throws ConnectionFailed, InvalidLocalSettingsDesignator,
			UnsupportedCallbackModel, CallNotAllowedFromWithinCallback, RTIinternalError,
			CouldNotCreateLogicalTimeFactory, FederationExecutionDoesNotExist, InconsistentFDD, ErrorReadingFDD,
			CouldNotOpenFDD, SaveInProgress, RestoreInProgress, NotConnected, MalformedURLException,
			FederateNotExecutionMember, NameNotFound, InvalidObjectClassHandle, AttributeNotDefined,
			ObjectClassNotDefined, InstantiationException, IllegalAccessException, IllegalName, ObjectInstanceNameInUse,
			ObjectInstanceNameNotReserved, ObjectClassNotPublished, AttributeNotOwned, ObjectInstanceNotKnown,
			PublishException, UpdateException {
		
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
		
		//5 - publish the SpaceProbe
		super.publishElement(spaceProbe);
		
		//6 - subscribe the mooncentricfixed reference
		super.subscribeReferenceFrame(FrameType.MoonCentricFixed);
				
		// 7 execution loop
		super.startExecution();
				
		try {
					
			System.out.println("Press any key to disconnect the Rover Federate from the SEE Federation Exectuion");
			new Scanner(System.in).next(); 			
			super.unsubscribeSubject(this);
		}catch(Exception e) {
			e.printStackTrace();
		}
			
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
		System.out.println("The spaceProbe has received an update");
		System.out.println(arg1);
	}

	@Override
	protected void doAction() {
		
		Position curr_pos = this.spaceProbe.getPosition();
		curr_pos.setX(curr_pos.getX()+10); // update the x coordinate
		
		try {
			
			super.updateElement(this.spaceProbe);
			
		} catch (FederateNotExecutionMember | NotConnected | AttributeNotOwned
				| AttributeNotDefined | ObjectInstanceNotKnown | SaveInProgress
				| RestoreInProgress | RTIinternalError | IllegalName
				| ObjectInstanceNameInUse | ObjectInstanceNameNotReserved
				| ObjectClassNotPublished | ObjectClassNotDefined
				| UpdateException e) {
			e.printStackTrace();
		}
	}
}