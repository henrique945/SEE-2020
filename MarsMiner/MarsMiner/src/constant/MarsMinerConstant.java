package constant;

import java.util.Map;

import org.apache.commons.math3.complex.Quaternion;

import scanner.XMLReader;

public final class MarsMinerConstant {

	private static Map<String, String> mapa = XMLReader.getValues();

	public static final Integer BATTERY_LEVEL =Integer.parseInt(mapa.get("BATTERY_LEVEL"));  /// The battery level ( mA/h ) of the Mars Miner.

	public static final Quaternion ROTATION_MARS_MINE = new Quaternion(0, 0, -0.7071, -0.7071);
	
	public static final Double MASS = Double.parseDouble(mapa.get("MASS")); /// The mass ( Kg ) of the Mars Miner.

	public static final Double HEIGHT = Double.parseDouble(mapa.get("HEIGHT")); 
	
	public static final Double WIDTH = Double.parseDouble(mapa.get("WIDTH")); 
	
	public static final Double LENGTH = Double.parseDouble(mapa.get("LENGTH")); 

	public static final Double LOADED_MATERIALS = Double.parseDouble(mapa.get("LOADED_MATERIALS")); /// The currently
																									/// loaded materials
																									/// ( Kg ) of the
																									/// Mars Miner

	public static final Double MAXIMUN_CAPACITY = Double.parseDouble(mapa.get("MAXIMUN_CAPACITY")); /// The maximum
																									/// capacity of
																									/// loaded materials
																									/// ( Kg ) of the
																									/// Mars Miner.

}
