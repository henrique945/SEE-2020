package helper;

import java.util.Map;
import java.util.TreeMap;

import constant.OxygenConstant;
import graph.Point;
import model.Position;

public class PositionOfOxygenFactory {

	public static Map<String, Point> map = new TreeMap<String, Point>();
	public static Position reference;
	public static Position heightLimit;
	public static Position lowestHeightLimit;
	public static Position referenceRover;

	public static Point getPosition(String key) {
		return map.get(key);
	}

	public static void setPosition(String local, int x, int y) {
		map.put(local, new Point(x, y));
	}

	public static void setReference(Position p) {
		reference = p;
		heightLimit = new Position(reference.getX(), reference.getY(), reference.getZ() - OxygenConstant.HEIGHT);
		lowestHeightLimit = new Position(reference.getX(), reference.getY(), reference.getZ() - OxygenConstant.LOWEST_HEIGHT);
		referenceRover = new Position(p.getX(), p.getY(), p.getZ());
	}

}
