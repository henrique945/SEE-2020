package model;

import org.apache.commons.math3.complex.Quaternion;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.siso.spacefom.frame.SpaceTimeCoordinateState;

import coder.WaterFinderStatusCoder;
import coder.SpaceTimeCoordinateStateCoder;
import skf.coder.HLAfloat64LECoder;
import skf.coder.HLAinteger32BECoder;
import skf.coder.HLAunicodeStringCoder;
import skf.model.object.annotations.Attribute;
import skf.model.object.annotations.ObjectClass;

@ObjectClass(name = "PhysicalEntity.FACENS_Water_Finder")
public class WaterFinder {

	@Attribute(name = "name", coder = HLAunicodeStringCoder.class)
	private String name = null;

	@Attribute(name = "parent_reference_frame", coder = HLAunicodeStringCoder.class)
	private String parent_name = null;

	@Attribute(name = "state", coder = SpaceTimeCoordinateStateCoder.class)
	private SpaceTimeCoordinateState state = null;

	@Attribute(name = "type", coder = HLAunicodeStringCoder.class)
	private String type = null;

	@Attribute(name = "battey_level", coder = HLAfloat64LECoder.class)
	private Double battery_level = null;

	@Attribute(name = "mass", coder = HLAfloat64LECoder.class)
	private Double mass = null;

	@Attribute(name = "remaning_tubes", coder = HLAinteger32BECoder.class)
	private Integer remaining_tubes = null;

	@Attribute(name = "total_tubes", coder = HLAinteger32BECoder.class)
	private Integer total_tubes = null;

	@Attribute(name = "temperature", coder = HLAfloat64LECoder.class)
	private Double temperature = null;

	@Attribute(name = "length", coder = HLAfloat64LECoder.class)
	private Double length = null;

	@Attribute(name = "width", coder = HLAfloat64LECoder.class)
	private Double width = null;

	@Attribute(name = "height", coder = HLAfloat64LECoder.class)
	private Double height = null;

	@Attribute(name = "water_finder_status", coder = WaterFinderStatusCoder.class)
	private WaterFinderStatus water_finder_status;

	@Attribute(name = "speed", coder = HLAfloat64LECoder.class)
	private Double speed = null;

	private boolean referenced = false;
	
	private boolean returno2fac = false;

	public WaterFinder() {

	}

	public WaterFinder(String name, Quaternion quaternion, String parent_name, String type, Position position,
			Double battery_level, Double mass, Integer total_tubes, Integer remaining_tubes,
			Double length, Double width, Double height) {
		this.name = name;
		this.parent_name = parent_name;
		this.type = type;
		this.state = new SpaceTimeCoordinateState();
		this.state.getRotationState().setAttitudeQuaternion(quaternion);
		this.setPosition(position);
		this.battery_level = battery_level;
		this.mass = mass;
		this.total_tubes = total_tubes;
		this.remaining_tubes = remaining_tubes;
		this.length = length;
		this.width = width;
		this.height = height;
		this.water_finder_status = WaterFinderStatus.EMPTY_LOAD;
		this.isIdle();
	}

	public void isWalking() {
		this.setTemperature((double) 20);
		this.setSpeed((double) 70);
	}

	public void isIdle() {
		this.setTemperature((double) -50);
		this.setSpeed((double) 0);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Double getBattery_level() {
		return battery_level;
	}

	public void setBattery_level(Double battery_level) {
		this.battery_level = battery_level;
	}

	public Double getMass() {
		return mass;
	}

	public void setMass(Double mass) {
		this.mass = mass;
	}

	public Integer getRemaining_tubes() {
		return remaining_tubes;
	}

	public void setRemaining_tubes(Integer remaining_tubes) {
		this.remaining_tubes = remaining_tubes;
	}

	public Integer getTotal_tubes() {
		return total_tubes;
	}

	public void setTotal_tubes(Integer total_tubes) {
		this.total_tubes = total_tubes;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the parent_name
	 */
	public String getParent_name() {
		return parent_name;
	}

	/**
	 * @param parent_name
	 *            the parent_name to set
	 */
	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}

	/**
	 * @return the position
	 */
	public void setPosition(Position position) {
		Vector3D vector3d = new Vector3D(position.getX(), position.getY(), position.getZ());
		this.state.getTranslationalState().setPosition(vector3d);
	}

	public Position getPosition() {
		Vector3D vector3d = state.getTranslationalState().getPosition();
		return new Position(vector3d.getX(), vector3d.getY(), vector3d.getZ());

	}

	public SpaceTimeCoordinateState getState() {
		return state;
	}

	public void setState(SpaceTimeCoordinateState state) {
		this.state = state;
	}

	public boolean isReferenced() {
		return referenced;
	}

	public void setReferenced(boolean referenced) {
		this.referenced = referenced;
	}

	public void setRotation(String value) {
		Quaternion rotation = new Quaternion(0, 0, 0.7071, -0.7071);
		if (value.equals("begin"))
			state.getRotationState().setAttitudeQuaternion(rotation);
		else if (value.equals("90"))
			state.getRotationState().setAttitudeQuaternion(new Quaternion(0, 0, -0.7071, -0.7071));
		else if (value.equals("180"))
			state.getRotationState().setAttitudeQuaternion(new Quaternion(0, 0, 1, 0));
		else if (value.equals("270"))
			state.getRotationState().setAttitudeQuaternion(new Quaternion(0, 0, 0.7071, -0.7071));
		else if (value.equals("0"))
			state.getRotationState().setAttitudeQuaternion(new Quaternion(0, 0, 0, 1));
	}

	public Quaternion getRotation() {
		return this.state.getRotationState().getAttitudeQuaternion();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getWidth() {
		return width;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeigth() {
		return height;
	}

	public void setHeigth(Double height) {
		this.height = height;
	}

	public WaterFinderStatus getWater_finder_status() {
		return water_finder_status;
	}

	public void setWater_finder_status(WaterFinderStatus water_finder_status) {
		this.water_finder_status = water_finder_status;
	}

	public boolean isReturno2fac() {
		return returno2fac;
	}

	public void setReturno2fac(boolean returno2fac) {
		this.returno2fac = returno2fac;
	}

	@Override
	public String toString() {
		return name + ", state=" + state + ", battery_level=" + battery_level + ", mass=" + mass + ", remaining_tubes="
				+ remaining_tubes + ", total_tubes=" + total_tubes + ", temperature=" + temperature + ", length="
				+ length + ", width=" + width + ", height=" + height + ", water_finder_status=" + water_finder_status
				+ ", speed=" + speed + ", referenced=" + referenced;
	}

}
