import java.io.File;

import org.apache.commons.math3.complex.Quaternion;

import core.SpaceProbeFederateAmbassador;
import core.SpaceProbeFederate;
import model.Position;
import model.SpaceProbe;
import skf.config.ConfigurationFactory;

public class SpaceProbeMain {
	
	private static final File conf = new File("config/conf.json");

	public static void main(String[] args) throws Exception {
		
		SpaceProbe spaceProbe = new SpaceProbe("SpaceProbe", new Quaternion(0, 0, 0.7071, 0.7071),  siso.smackdown.frame.FrameType.AitkenBasinLocalFixed.toString(), "SpaceProbe", new Position(100, 500, 800));
		
		SpaceProbeFederateAmbassador ambassador = new SpaceProbeFederateAmbassador();
		SpaceProbeFederate federate = new SpaceProbeFederate(ambassador, spaceProbe);
		
		//Start Execution
		federate.configureAndStart(new ConfigurationFactory().importConfiguration(conf));
	}
}