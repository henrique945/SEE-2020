package constant;

public final class MessageConstant {
	/**
	 * Source: WaterFinder
	 * Destination: OxygenFactory
	 */
	public static final String REQUEST_GET_IN = "request-get-in";
	public static final String REQUEST_NORTH = "request-north";
	public static final String REQUEST_SOUTH = "request-south";
	public static final String REQUEST_WEST = "request-west";
	public static final String REQUEST_EAST = "request-east";
	public static final String REQUEST_O2FAC = "request-o2fac";
	public static final String SEND_PATH_DONE = "send-path-done";
	
	/**
	 * Source: OxygenFactory
	 * Destination: WaterFinder
	 */
	public static final String REQUEST_GET_IN_FULL = "request-oxygen-factory-full";
	public static final String REQUEST_GET_IN_OK = "request-oxygen-factory-ok";
	public static final String SEND_PATH = "send-path";
	
}
