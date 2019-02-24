import java.io.File;

import constant.MineConstant;
import core.ExcavationMineFederate;
import core.ExcavationMineFederateAmbassador;
import graph.PathFind;
import helper.PositionOfMine;
import model.ExcavationMine;
import model.Position;
import skf.config.ConfigurationFactory;

public class ExcavationMineMain {

	private static final File conf = new File("config/conf.json");

	public static void main(String[] args) throws Exception {

		// Initialize the map of Lunar Habitat
		PathFind map = new PathFind("/figures/map.png");

		Position positionExcavationMine = new Position(MineConstant.EXCAVATION_MINE.getX(),
				MineConstant.EXCAVATION_MINE.getY(), MineConstant.EXCAVATION_MINE.getZ());

		PositionOfMine.setReference(positionExcavationMine);

		ExcavationMine excavationMine = new ExcavationMine("facens_excavation_mine",
				siso.smackdown.frame.FrameType.AitkenBasinLocalFixed.toString(), "Excavation Mine",
				positionExcavationMine, MineConstant.DIAMETER, MineConstant.DEPTH, MineConstant.PRESSURE,
				MineConstant.TEMPERATURE, MineConstant.AREA, MineConstant.VOLUME, MineConstant.MAXIMUS,
				new Short((short) 0), MineConstant.ROTATION_EXCAVATION_MINE);

		ExcavationMineFederateAmbassador ambassador = new ExcavationMineFederateAmbassador();
		ExcavationMineFederate federate = new ExcavationMineFederate(ambassador, excavationMine, map);

		// start execution
		federate.configureAndStart(new ConfigurationFactory().importConfiguration(conf));
	}

}
