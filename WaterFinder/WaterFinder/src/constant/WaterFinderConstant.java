package constant;

import java.util.Map;

import org.apache.commons.math3.complex.Quaternion;

import scanner.XMLReader;

public final class WaterFinderConstant {

	private static Map<String, String> mapa = XMLReader.getValues();

	public static final Double BATTERY_LEVEL = Double.parseDouble(mapa.get("BATTERY_LEVEL")); /// The battery level (
																								/// mA/h ) of the Mars
																								/// Miner.
	public static final Double MASS = Double.parseDouble(mapa.get("MASS")); /// The machine fluids (
																			/// mL ) of the Mars
																			/// Miner.
	public static final Integer TOTAL_TUBES = Integer.parseInt(mapa.get("TOTAL_TUBES")); /// The currently
																							/// loaded materials
																							/// ( Kg ) of the
																							/// Mars Miner

	public static final Integer REMAINING_TUBES = Integer.parseInt(mapa.get("REMAINING_TUBES")); /// The maximum
																									/// capacity of

	public static final Double LENGTH = Double.parseDouble(mapa.get("LENGTH"));

	public static final Double WIDTH = Double.parseDouble(mapa.get("WIDTH"));

	public static final Double HEIGHT = Double.parseDouble(mapa.get("HEIGHT"));

	// TODO: Learn how to Convert a Strng from XML to Quaternion
	public static final Quaternion ROTATION_EXCAVATION_MINE = new Quaternion(0, 0, 0.7071, 0.7071);
}
