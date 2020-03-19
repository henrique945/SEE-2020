package constant;

import java.util.Map;

import org.apache.commons.math3.complex.Quaternion;

import model.Position;
import scanner.XMLReader;

public final class MarsHabitatConstant {
	
	private static Map<String,String> mapa = XMLReader.getValues(); 
	
	//public static final Position LUNAR_HABITAT = new Position(160, -71, -5605.3);		 													/// Lunar Habitat Position
	public static final Position MARS_HABITAT = new Position(-700, -350, -5581.0);		 													/// Lunar Habitat Position
	public static final Quaternion ROTATION_MARS_HABITAT = new Quaternion(0, 0, 0, 1);
	public static final Position AIRLOCKPOSITION = new Position(0, 0, 0);																/// Airlock position
	public static final Boolean AIRLOCK_STATUS = Boolean.getBoolean(mapa.get("AIRLOCK_STATUS"));										///airlock opened/closed		
	public static final Double AIR_HUMIDITY = Double.parseDouble(mapa.get("AIR_HUMIDITY"));												///air_humidity levels
	public static final Double OXYGEN_VOLUME_INITIAL = Double.parseDouble(mapa.get("OXYGEN_VOLUME_INITIAL")); 							/// Initial oxygen volume in Liters
	public static final Double OXYGEN_MINIMUM_VOLUME = Double.parseDouble(mapa.get("OXYGEN_MINIMUM_VOLUME"));							/// Minimum oxygen.
	public static final Double CARBON_DIOXIDE_VOLUME_INITIAL = Double.parseDouble(mapa.get("CARBON_DIOXIDE_VOLUME_INITIAL")); 							/// Initial carbon dioxide volume in Liters
	public static final Double CARBON_DIOXIDE_MAXIMUM_VOLUME = Double.parseDouble(mapa.get("CARBON_DIOXIDE_MAXIMUM_VOLUME"));							/// MAXIMUM carbon dioxide.
	public static final Double BASE_OXYGEN_CONSUMPTION = Double.parseDouble(mapa.get("BASE_OXYGEN_CONSUMPTION"));						/// Initial oxygen consumption in Liters
	public static final Double ASTRONAUT_OXYGEN_CONSUMPTION = Double.parseDouble(mapa.get("ASTRONAUT_OXYGEN_CONSUMPTION")); 			/// Amount of oxygen consumption per day by an astrounaut in Liters
	public static final Double ASTRONAUT_CARBON_DIOXIDE_EMISSION = Double.parseDouble(mapa.get("ASTRONAUT_CARBON_DIOXIDE_EMISSION"));	///Amount of carbon dioxide per day by an astrounaut in Liters
	public static final Double PRESSURE = Double.parseDouble(mapa.get("PRESSURE"));														/// Environment pressure
	public static final Double TEMPERATURE = Double.parseDouble(mapa.get("TEMPERATURE"));												/// Environment Temperature in Celsius (ºC)
	public static final Double AREA = Double.parseDouble(mapa.get("AREA"));																/// Environment area in square meters (m²)
	public static final Double VOLUME = Double.parseDouble(mapa.get("VOLUME"));															/// Environment volume in cubic meters(m³)
	public static final Short MAXIMUS = Short.parseShort(mapa.get("MAXIMUS"));															/// Maximum quantity of astrounauts in Lunar Habitat
	public static final Double INITIAL_CALORIES = Double.parseDouble(mapa.get("INITIAL_CALORIES"));
	public static final Double MINIMUM_CALORIES = Double.parseDouble(mapa.get("MINIMUM_CALORIES"));

}
