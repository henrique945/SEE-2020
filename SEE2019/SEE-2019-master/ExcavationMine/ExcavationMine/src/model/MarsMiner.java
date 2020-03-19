package model;

import skf.coder.HLAunicodeStringCoder;
import skf.model.object.annotations.Attribute;
import skf.model.object.annotations.ObjectClass;
import org.siso.spacefom.frame.SpaceTimeCoordinateState;

import coder.MarsMinerStatusCoder;
import coder.SpaceTimeCoordinateStateCoder;

import org.apache.commons.math3.complex.Quaternion;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import skf.coder.HLAfloat64LECoder;
import skf.coder.HLAinteger32BECoder;

@ObjectClass(name = "PhysicalEntity.FACENS_Mars_Miner")
public class MarsMiner {

	@Attribute(name = "name", coder = HLAunicodeStringCoder.class)
	private String name = null;

	@Attribute(name = "parent_reference_frame", coder = HLAunicodeStringCoder.class)
	private String parent_name = null;

	@Attribute(name = "state", coder = SpaceTimeCoordinateStateCoder.class)
	private SpaceTimeCoordinateState state = null;

	@Attribute(name = "type", coder = HLAunicodeStringCoder.class)
	private String type = null;

	@Attribute(name = "battery_level", coder = HLAinteger32BECoder.class)
	private Integer battery_level = null;

	@Attribute(name = "mass", coder = HLAfloat64LECoder.class)
	private Double mass = null;

	@Attribute(name = "length", coder = HLAfloat64LECoder.class)
	private Double length = null;

	@Attribute(name = "width", coder = HLAfloat64LECoder.class)
	private Double width = null;

	@Attribute(name = "height", coder = HLAfloat64LECoder.class)
	private Double height = null;

	@Attribute(name = "loaded_materials", coder = HLAfloat64LECoder.class)
	private Double loaded_materials = null;

	@Attribute(name = "temperature", coder = HLAfloat64LECoder.class)
	private Double temperature = null;

	@Attribute(name = "speed", coder = HLAfloat64LECoder.class)
	private Double speed = null;

	@Attribute(name = "maximun_capacity", coder = HLAfloat64LECoder.class)
	private Double maximun_capacity = null;

	@Attribute(name = "mars_miner_status", coder = MarsMinerStatusCoder.class)
	private MarsMinerStatus mars_miner_status;

	private boolean referenced = false;

	private int timeLimitRequest = -1;

	private boolean returno2fac = false;

	private String target = "";

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public MarsMiner() {
	}

	public MarsMiner(String name, String parent_name, String type, Position position, Integer battery_level,
			Double mass, Double length, Double width, Double height, Double loaded_materials, Double maximun_capacity,
			Quaternion rotation) {
		this.name = name;
		this.parent_name = parent_name;
		this.type = type;
		this.state = new SpaceTimeCoordinateState();
		this.state.getRotationState().setAttitudeQuaternion(rotation);
		this.setPosition(position);
		this.battery_level = battery_level;
		this.mass = mass;
		this.length = length;
		this.width = width;
		this.height = height;
		this.loaded_materials = loaded_materials;
		this.maximun_capacity = maximun_capacity;
		this.mars_miner_status = MarsMinerStatus.EMPTY_LOAD;
		isIdle();

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

	public Integer getBattery_level() {
		return battery_level;
	}

	public void setBattery_level(Integer battery_level) {
		this.battery_level = battery_level;
	}

	public Double getMass() {
		return mass;
	}

	public void setMass(Double mass) {
		this.mass = mass;
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

	public Double getLoaded_materials() {
		return loaded_materials;
	}

	public void setLoaded_materials(Double loaded_materials) {
		this.loaded_materials = loaded_materials;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Double getMaximun_capacity() {
		return maximun_capacity;
	}

	public void setMaximun_capacity(Double maximun_capacity) {
		this.maximun_capacity = maximun_capacity;
	}

	public MarsMinerStatus getMars_miner_status() {
		return mars_miner_status;
	}

	public void setMars_miner_status(MarsMinerStatus mars_miner_status) {
		this.mars_miner_status = mars_miner_status;
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

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public int getTimeLimitRequest() {
		return timeLimitRequest;
	}

	public void setTimeLimitRequest(int timeLimitRequest) {
		this.timeLimitRequest = timeLimitRequest;
	}

	public boolean isReturno2fac() {
		return returno2fac;
	}

	public void setReturno2fac(boolean returno2fac) {
		this.returno2fac = returno2fac;
	}

	@Override
	public String toString() {
		return name + ", parent_name=" + parent_name + ", position=" + state.getTranslationalState().getPosition()
				+ ",rotation=" + state.getRotationState().getAttitudeQuaternion() + ", type=" + type
				+ ", battery_level=" + battery_level + ", mass=" + mass + ", length=" + length + ", width=" + width
				+ ", height=" + height + ", loaded_materials=" + loaded_materials + ", temperature=" + temperature
				+ ", speed=" + speed + ", maximun_capacity=" + maximun_capacity + ", mars_miner_status="
				+ mars_miner_status;
	}

}
