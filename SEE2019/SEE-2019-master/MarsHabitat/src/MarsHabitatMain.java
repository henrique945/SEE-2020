import java.io.File;
import java.util.Scanner;

import constant.MarsHabitatConstant;
import skf.config.Configuration;
import skf.config.ConfigurationFactory;
import federate.MarsHabitatFederate;
import federate.MarsHabitatFederateAmbassador;
import grafo.PathFind;
import helper.PositionOfLuHa;
import model.MarsHabitatBase;
import model.MarsHabitatRooftop;
import model.Position;

public class MarsHabitatMain {

	private static final File conf = new File("conf/conf.json");

	private static Scanner sc = null;

	public static void main(String[] args) throws Exception {

		ConfigurationFactory factory = new ConfigurationFactory();
		Configuration configuration = factory.importConfiguration(conf);

		// Initialize the map of Lunar Habitat
		//PathFind map = new PathFind("/figures/map.png");
		PathFind map = new PathFind("/figures/mapa-grande.png");
		
		Position positionHabitat = new Position(MarsHabitatConstant.MARS_HABITAT.getX(), MarsHabitatConstant.MARS_HABITAT.getY(),
				MarsHabitatConstant.MARS_HABITAT.getZ());
		Position positionHabitatRooftop = new Position(MarsHabitatConstant.MARS_HABITAT.getX(),
				MarsHabitatConstant.MARS_HABITAT.getY(), MarsHabitatConstant.MARS_HABITAT.getZ());

		PositionOfLuHa.setReference(positionHabitat);

		// Initialize the LunarHabitatBase model
		MarsHabitatBase luHaBase = new MarsHabitatBase("FACENS_LuHa_Base",
				siso.smackdown.frame.FrameType.AitkenBasinLocalFixed.toString(), "Lunar Habitat Base", positionHabitat,
				MarsHabitatConstant.PRESSURE, MarsHabitatConstant.TEMPERATURE, MarsHabitatConstant.AREA, MarsHabitatConstant.VOLUME,
				new Double(75.0), MarsHabitatConstant.OXYGEN_VOLUME_INITIAL, new Double(0), MarsHabitatConstant.AIR_HUMIDITY,
				new Double(0), MarsHabitatConstant.MAXIMUS, new Short((short) 0));

		// Initialize the LunarHabitatRooftop model
		MarsHabitatRooftop luHaRooftop = new MarsHabitatRooftop("FACENS_LuHa_Rooftop",
				siso.smackdown.frame.FrameType.AitkenBasinLocalFixed.toString(), positionHabitatRooftop,
				"Lunar Habitat Rooftop");

		MarsHabitatFederateAmbassador amb = new MarsHabitatFederateAmbassador();
		MarsHabitatFederate federate = new MarsHabitatFederate(amb, luHaBase, luHaRooftop, map);

		federate.configureAndStart(configuration);

		sc = new Scanner(System.in);
		String currValue = null;
		while (true) {

			currValue = sc.next();

			if (currValue.equals("a")) {
				federate.sendGoToShutdown();
				break;
			}

			if (currValue.equals("q")) {
				//federate.//diconnectFromRTI();
				break;
			}
			// if 'u' the LuHaRooftop a will request BringUp for LuHaBase
			if (currValue.equals("u")) {
				federate.requestBringUp();
			}
			// if 'u' the LuHaRooftop a will request Down for LuHaBase
			if (currValue.equals("d")) {
				federate.requestDown();
			}
			// if 'c' the total control for rooftop change states
			if (currValue.equals("c")) {
				federate.changeControle();
			}
		}

	}// main

}
