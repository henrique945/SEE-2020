import java.io.File;

import constant.WaterFinderConstant;
import core.WaterFinderFederate;
import core.WaterFinderFederateAmbassador;
import helper.ListWaterFinder;
import skf.config.ConfigurationFactory;
import model.WaterFinder;
import model.Position;

public class WaterFinderMain {

	private static final File conf = new File("config/conf.json");

	public static void main(String[] args) throws Exception {
		System.out.println("Publishing 3 Water Finder(s).");
		int value = 3;
		ListWaterFinder listWaterFinder = new ListWaterFinder();

		// Instance for N Mars Miner and add in listWaterFinder
		for (int index = 1; index <= value; index++) {
			// Initialize the first WaterFinder position
			Position mine = new Position(index, 0, 0);
			WaterFinder waterFinder = new WaterFinder("facens_water_finder_" + index,
					WaterFinderConstant.ROTATION_EXCAVATION_MINE,
					siso.smackdown.frame.FrameType.AitkenBasinLocalFixed.toString(), "WaterFinder", mine,
					WaterFinderConstant.BATTERY_LEVEL, WaterFinderConstant.MASS, WaterFinderConstant.TOTAL_TUBES,
					WaterFinderConstant.REMAINING_TUBES, WaterFinderConstant.LENGTH, WaterFinderConstant.WIDTH,
					WaterFinderConstant.HEIGHT);

			listWaterFinder.addWaterFinder(waterFinder, index);
		}

		WaterFinderFederateAmbassador ambassador = new WaterFinderFederateAmbassador();
		WaterFinderFederate federate = new WaterFinderFederate(ambassador, listWaterFinder);

		// start execution
		federate.configureAndStart(new ConfigurationFactory().importConfiguration(conf));
	}
}