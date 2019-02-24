package helper;

import java.util.Map;
import java.util.TreeMap;

import constant.MarsHabitatConstant;
import constant.PositionConstant;
import grafo.Point;
import model.Position;

public class PositionOfLuHa {
	
	public static Map<String,Point> map = new TreeMap<String,Point>();
	public static Position reference;
	public static Position referenceAstro;
	public static Position heightLimit;
	
	public static Point getPosition(String key){
		return map.get(key);
	}
	public static void setPosition(String local,int x,int y){
		map.put(local,new Point(x, y));
	}
	public static void setReference(Position p){
		reference=p;
		//referenceAstro = new Position(p.getX(), p.getY(), p.getZ());
		referenceAstro = PositionConstant.ASTRONAUT_POSITION;
		heightLimit=new Position(reference.getX(),reference.getY(), reference.getZ()-100);
	}
}
