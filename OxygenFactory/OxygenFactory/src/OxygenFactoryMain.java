import java.io.File;
import java.util.Scanner;

import constant.OxygenConstant;
import core.OxygenFactoryFederate;
import core.OxygenFactoryFederateAmbassador;
import graph.PathFind;
import helper.PositionOfOxygenFactory;
import model.OxygenFactory;
import model.OxygenFactoryRooftop;
import model.Position;
import skf.config.ConfigurationFactory;

public class OxygenFactoryMain {

	private static final File conf = new File("config/conf.json");

	private static Scanner sc = null;

	public static void main(String[] args) throws Exception {

		PathFind map = new PathFind("/figures/map.png");

		Position positionOxygenFactory = new Position(OxygenConstant.OXYGEN_FACTORY.getX(),
				OxygenConstant.OXYGEN_FACTORY.getY(), OxygenConstant.OXYGEN_FACTORY.getZ());

		Position positionOxygenFactoryRooftop = new Position(OxygenConstant.OXYGEN_FACTORY.getX(),
				OxygenConstant.OXYGEN_FACTORY.getY(), OxygenConstant.OXYGEN_FACTORY.getZ());

		PositionOfOxygenFactory.setReference(positionOxygenFactory);

		OxygenFactory oxygenFactory = new OxygenFactory("facens_oxygen_factory",
				siso.smackdown.frame.FrameType.AitkenBasinLocalFixed.toString(), "Oxygen Factory",
				OxygenConstant.MAX_WATER_VOLUME, OxygenConstant.CURRENT_WATER_VOLUME, OxygenConstant.MAX_OXYGEN_VOLUME,
				OxygenConstant.CURRENT_OXYGEN_VOLUME, OxygenConstant.MAX_RAW_MATERIAL_VOLUME,
				OxygenConstant.CURRENT_RAW_MATERIAL_VOLUME, OxygenConstant.ENERGY_CONSUMPTION,
				OxygenConstant.OXYGEN_PRODUCTION, OxygenConstant.FACTORY_AREA, OxygenConstant.FACTORY_VOLUME,
				OxygenConstant.TEMPERATURE, OxygenConstant.PRESSURE, positionOxygenFactory);

		OxygenFactoryRooftop oxygenFactoryRooftop = new OxygenFactoryRooftop("facens_oxygen_factory_rooftop",
				siso.smackdown.frame.FrameType.AitkenBasinLocalFixed.toString(), "Oxygen Factory Rooftop",
				positionOxygenFactoryRooftop);

		OxygenFactoryFederateAmbassador ambassador = new OxygenFactoryFederateAmbassador();
		OxygenFactoryFederate federate = new OxygenFactoryFederate(ambassador, oxygenFactory, oxygenFactoryRooftop,
				map);

		// start execution
		federate.configureAndStart(new ConfigurationFactory().importConfiguration(conf));

		sc = new Scanner(System.in);
		String currValue = null;
		while (true) {

			currValue = sc.next();

			// if 'u' is pressed, the Rooftop move up
			if (currValue.equals("u")) {
				federate.requestBringUp();
			}
			// if 'd' is pressed, the Rooftop go move Down
			if (currValue.equals("d")) {
				federate.requestDown();
			}
			// if 'c' to lost the control of the rooftop (or gain
			if (currValue.equals("c")) {
				federate.changeControle();
			}
		}

	}

}
