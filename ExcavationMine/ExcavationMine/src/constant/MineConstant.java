package constant;

import java.util.Map;

import org.apache.commons.math3.complex.Quaternion;

import model.Position;
import scanner.XMLReader;

public final class MineConstant {

	private static Map<String, String> mapa = XMLReader.getValues();

	public static final Position EXCAVATION_MINE = new Position(-900, -700, -5581.0); /// Excavation Mine Position
//	public static final Position EXCAVATION_MINE = new Position(-817582.939286128,-29614.93633636,-1510977.52696795); /// Excavation Mine Position
	public static final Quaternion ROTATION_EXCAVATION_MINE = new Quaternion(0, 0, -0.7071, -0.7071);
	public static final Double PRESSURE = Double.parseDouble(mapa.get("PRESSURE")); /// Environment Pressure
	public static final Double TEMPERATURE = -1*Double.parseDouble(mapa.get("TEMPERATURE")); /// Environment Temperature in
																							/// Celsius (ºC)
	public static final Double AREA = Double.parseDouble(mapa.get("AREA")); /// Environment area in square meters (m²)
	public static final Double VOLUME = Double.parseDouble(mapa.get("VOLUME")); /// Environment volume in cubic
																				/// meters(m³)
	public static final Short MAXIMUS = Short.parseShort(mapa.get("MAXIMUS")); /// Maximum quantity of rovers in
																				/// Excavation Mine
	public static final Double DIAMETER = Double.parseDouble(mapa.get("DIAMETER")); /// The diameter (in m) of the Excavation Mine.
	public static final Double DEPTH = Double.parseDouble(mapa.get("DEPTH"));/// The depth (in m) of the Excavation Mine.
}
