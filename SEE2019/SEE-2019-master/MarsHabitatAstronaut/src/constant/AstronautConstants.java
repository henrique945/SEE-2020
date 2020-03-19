package constant;

import java.util.Map;

import model.Position;
import scanner.XMLReader;

public final class AstronautConstants {
	
	private static Map<String, String> map = XMLReader.getValues(); 
	
	public static final String MARS_ASTRONAUT = "facens_luha_astro_1";
	public static final Position HABITAT_POSITION = new Position(-700, -350, -5581.0); //new Position(-800, -600, -5581.0);
	public static final String MARS_ASTRONAUT_TYPE = "Lunar Habitat Astronaut";
	
}
