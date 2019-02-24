package constant;

import java.util.Map;

import org.apache.commons.math3.complex.Quaternion;

import model.Position;
import scanner.XMLReader;

public final class OxygenConstant {
	
	private static Map<String, String> mapa = XMLReader.getValues();

	public static final Double HEIGHT = Double.parseDouble(mapa.get("HEIGHT"));
	
	public static final Position OXYGEN_FACTORY = new Position(-817331.939286128, -29535.93633636, -1510979.25); /// Oxygen Factory Position

	public static final Quaternion ROTATION_OXYGEN_FACTORY = new Quaternion(0, 0, -0.7071, -0.7071);
	
	public static final Double MAX_WATER_VOLUME = Double.parseDouble(mapa.get("MAX_WATER_VOLUME"));

	public static final Double CURRENT_WATER_VOLUME = Double.parseDouble(mapa.get("CURRENT_WATER_VOLUME"));

	public static final Double MAX_OXYGEN_VOLUME = Double.parseDouble(mapa.get("MAX_OXYGEN_VOLUME"));

	public static final Double CURRENT_OXYGEN_VOLUME = Double.parseDouble(mapa.get("CURRENT_OXYGEN_VOLUME"));

	public static final Double MAX_RAW_MATERIAL_VOLUME = Double.parseDouble(mapa.get("MAX_RAW_MATERIAL_VOLUME"));

	public static final Double CURRENT_RAW_MATERIAL_VOLUME = Double.parseDouble(mapa.get("CURRENT_RAW_MATERIAL_VOLUME"));

	public static final Double ENERGY_CONSUMPTION = Double.parseDouble(mapa.get("ENERGY_CONSUMPTION"));

	public static final Double OXYGEN_PRODUCTION = Double.parseDouble(mapa.get("OXYGEN_PRODUCTION"));

	public static final Double FACTORY_AREA = Double.parseDouble(mapa.get("FACTORY_AREA"));

	public static final Double FACTORY_VOLUME = Double.parseDouble(mapa.get("FACTORY_VOLUME"));

	public static final Double TEMPERATURE = Double.parseDouble(mapa.get("TEMPERATURE"));

	public static final Double PRESSURE = Double.parseDouble(mapa.get("PRESSURE"));
	
	public static final Double FACTORY_ENERGY_CONSUMPTION = Double.parseDouble(mapa.get("FACTORY_ENERGY_CONSUMPTION"));
	
	public static final Double ENERGY_CONSUMPTION_PER_MINUTE = Double.parseDouble(mapa.get("ENERGY_CONSUMPTION_PER_MINUTE"));

	public static final Double OXYGEN_MULT = Double.parseDouble(mapa.get("OXYGEN_MULT"));

	public static final Double DISPLACEMENT = Double.parseDouble(mapa.get("DISPLACEMENT"));
}