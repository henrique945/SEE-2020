package helper;

import java.util.Map;
import java.util.TreeMap;

import graph.Point;
import model.Position;

public class PositionOfMine {
	
	public static Map<String,Point> map = new TreeMap<String,Point>();
	public static Position reference;
	public static Position referenceRover;
	
	public static Point getPosition(String key){
		return map.get(key);
	} 
	public static void setPosition(String local,int x,int y){
		map.put(local,new Point(x, y));
	}
	public static void setReference(Position p){
		reference=p;
		referenceRover = new Position(p.getX()+206, p.getY()+34, p.getZ()-1.72303205);
	}
}
