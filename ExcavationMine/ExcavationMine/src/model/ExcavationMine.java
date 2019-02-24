package model;

import org.apache.commons.math3.complex.Quaternion;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.siso.spacefom.frame.SpaceTimeCoordinateState;

import coder.SpaceTimeCoordinateStateCoder;
import skf.coder.HLAfloat64LECoder;
import skf.coder.HLAinteger16LECoder;
import skf.coder.HLAunicodeStringCoder;
import skf.model.object.annotations.Attribute;
import skf.model.object.annotations.ObjectClass;

@ObjectClass(name = "PhysicalEntity.FACENS_Excavation_Mine")
public class ExcavationMine {

	@Attribute(name = "name", coder = HLAunicodeStringCoder.class)
	private String name = null;

	@Attribute(name = "parent_reference_frame", coder = HLAunicodeStringCoder.class)
	private String parent_name = null;

	@Attribute(name = "state", coder = SpaceTimeCoordinateStateCoder.class)
	private SpaceTimeCoordinateState state = null;

	@Attribute(name = "type", coder = HLAunicodeStringCoder.class)
	private String type = null;

	@Attribute(name = "diameter", coder = HLAfloat64LECoder.class)
	private Double diameter = null;

	@Attribute(name = "depth", coder = HLAfloat64LECoder.class)
	private Double depth = null;

	@Attribute(name = "maximun_capacity", coder = HLAinteger16LECoder.class)
	private Short maximun_capacity = null;

	@Attribute(name = "number_of_mars_miner", coder = HLAinteger16LECoder.class)
	private Short number_of_mars_miner = null;

	@Attribute(name = "pressure", coder = HLAfloat64LECoder.class)
	private Double pressure = null;

	@Attribute(name = "temperature", coder = HLAfloat64LECoder.class)
	private Double temperature = null;

	@Attribute(name = "area", coder = HLAfloat64LECoder.class)
	private Double area = null;

	@Attribute(name = "volume", coder = HLAfloat64LECoder.class)
	private Double volume = null;

	public ExcavationMine() {
	}

	public ExcavationMine(String name, String parent_name, String type, Position position, Double diameter,
			Double depth, Double pressure, Double temperature, Double area, Double volume, Short maximun_capacity,
			Short number_of_mars_miner, Quaternion rotation) {
		this.name = name;
		this.parent_name = parent_name;
		this.type = type;
		this.state = new SpaceTimeCoordinateState();
		this.setPosition(position);
		this.state.getRotationState().setAttitudeQuaternion(rotation);
		this.pressure = pressure;
		this.temperature = temperature;
		this.area = area;
		this.volume = volume;
		this.maximun_capacity = maximun_capacity;
		this.number_of_mars_miner = number_of_mars_miner;
		this.diameter = diameter;
		this.depth = depth;
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

	public Double getDiameter() {
		return diameter;
	}

	public void setDiameter(Double diameter) {
		this.diameter = diameter;
	}

	public Double getDepth() {
		return depth;
	}

	public void setDepth(Double depth) {
		this.depth = depth;
	}

	public Quaternion getRotation() {
		return this.state.getRotationState().getAttitudeQuaternion();
	}

	public Short getMaximun_capacity() {
		return maximun_capacity;
	}

	public void setMaximun_capacity(Short maximun_capacity) {
		this.maximun_capacity = maximun_capacity;
	}

	public Short getNumber_of_mars_miner() {
		return number_of_mars_miner;
	}

	public void setNumber_of_mars_miner(Short number_of_mars_miner) {
		this.number_of_mars_miner = number_of_mars_miner;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getPressure() {
		return pressure;
	}

	public void setPressure(Double pressure) {
		this.pressure = pressure;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	@Override
	public String toString() {
		return name + ", parent_name=" + parent_name + ", position=" + state.getTranslationalState().getPosition()
				+ ",rotation=" + state.getRotationState().getAttitudeQuaternion() + ", type=" + type + ", diameter="
				+ diameter + ", depth=" + depth + ", maximun_capacity=" + maximun_capacity + ", number_of_mars_miner="
				+ number_of_mars_miner + ", pressure=" + pressure + ", temperature=" + temperature + ", area=" + area
				+ ", volume=" + volume + "]";
	}

}
