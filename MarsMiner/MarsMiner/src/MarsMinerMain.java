import java.io.File;

import constant.MarsMinerConstant;
import core.MarsMinerFederate;
import core.MarsMinerFederateAmbassador;
import helper.ListMarsMiner;
import skf.config.ConfigurationFactory;
import model.MarsMiner;
import model.Position;

public class MarsMinerMain {

	private static final File conf = new File("config/conf.json");

	public static void main(String[] args) throws Exception {
		System.out.println("Publishing 3 Mars Miner.");
		int value = 2;
		ListMarsMiner listMarsMiner = new ListMarsMiner();

		// Instance for N Mars Miner and add in listMarsMiner
		for (int i = 1; i <= value; i++) {
			// Initialize the first MarsMiner position
			Position mine = new Position(i, 0, 0);
			MarsMiner marsMiner = new MarsMiner("facens_mars_miner_" + i,
					siso.smackdown.frame.FrameType.AitkenBasinLocalFixed.toString(), "Mars Miner", mine,
					MarsMinerConstant.BATTERY_LEVEL, MarsMinerConstant.MASS, MarsMinerConstant.LENGTH,
					MarsMinerConstant.WIDTH, MarsMinerConstant.HEIGHT, MarsMinerConstant.LOADED_MATERIALS,
					MarsMinerConstant.MAXIMUN_CAPACITY,MarsMinerConstant.ROTATION_MARS_MINE);
			listMarsMiner.addMarsMiner(marsMiner, i);
		}

		MarsMinerFederateAmbassador ambassador = new MarsMinerFederateAmbassador();
		MarsMinerFederate federate = new MarsMinerFederate(ambassador, listMarsMiner);

		// start execution
		federate.configureAndStart(new ConfigurationFactory().importConfiguration(conf));

	}

}
