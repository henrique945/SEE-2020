package model;

import org.apache.commons.math3.complex.Quaternion;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.siso.spacefom.frame.SpaceTimeCoordinateState;

import coder.SpaceTimeCoordinateStateCoder;
import constant.OxygenConstant;
import skf.coder.HLAfloat64LECoder;
import skf.coder.HLAunicodeStringCoder;
import skf.model.object.annotations.Attribute;
import skf.model.object.annotations.ObjectClass;

@ObjectClass(name = "PhysicalEntity.FACENS_Oxygen_Factory")
public class OxygenFactory {

	@Attribute(name = "name", coder = HLAunicodeStringCoder.class)
	private String name = null;

	@Attribute(name = "parent_reference_frame", coder = HLAunicodeStringCoder.class)
	private String parent_name = null;

	@Attribute(name = "state", coder = SpaceTimeCoordinateStateCoder.class)
	private SpaceTimeCoordinateState state = null;

	@Attribute(name = "type", coder = HLAunicodeStringCoder.class)
	private String type = null;

	@Attribute(name = "Max_Water_Volume", coder = HLAfloat64LECoder.class)
	private Double max_water_volume = null;

	@Attribute(name = "Current_Water_Volume", coder = HLAfloat64LECoder.class)
	private Double current_water_volume = null;

	@Attribute(name = "Max_Oxygen_Volume", coder = HLAfloat64LECoder.class)
	private Double max_oxygen_volume = null;

	@Attribute(name = "Current_Oxygen_Volume", coder = HLAfloat64LECoder.class)
	private Double current_oxygen_volume = null;

	@Attribute(name = "Max_Raw_Material_Volume", coder = HLAfloat64LECoder.class)
	private Double max_raw_material_volume = null;

	@Attribute(name = "Current_Raw_Material_Volume", coder = HLAfloat64LECoder.class)
	private Double current_raw_material_volume = null;

	@Attribute(name = "Energy_Consumption", coder = HLAfloat64LECoder.class)
	private Double energy_comsumption = null;

	@Attribute(name = "Oxygen_Production", coder = HLAfloat64LECoder.class)
	private Double oxygen_production = null;

	@Attribute(name = "Factory_Area", coder = HLAfloat64LECoder.class)
	private Double factory_area = null;

	@Attribute(name = "Factory_Volume", coder = HLAfloat64LECoder.class)
	private Double factory_volume = null;

	@Attribute(name = "Temperature", coder = HLAfloat64LECoder.class)
	private Double temperature = null;

	@Attribute(name = "Pressure", coder = HLAfloat64LECoder.class)
	private Double pressure = null;

	public OxygenFactory() {

	}

	public OxygenFactory(String name, String parent_name, String type, Double max_water_volume,
			Double current_water_volume, Double max_oxygen_volume, Double current_oxygen_volume,
			Double max_raw_material_volume, Double current_raw_material_volume, Double energy_comsumption,
			Double oxygen_production, Double factory_area, Double factory_volume, Double temperature, Double pressure,
			Position position) {
		super();
		this.name = name;
		this.parent_name = parent_name;
		this.state = new SpaceTimeCoordinateState();
		this.type = type;
		this.max_water_volume = max_water_volume;
		this.current_water_volume = current_water_volume;
		this.max_oxygen_volume = max_oxygen_volume;
		this.current_oxygen_volume = current_oxygen_volume;
		this.max_raw_material_volume = max_raw_material_volume;
		this.current_raw_material_volume = current_raw_material_volume;
		this.energy_comsumption = energy_comsumption; // ok
		this.oxygen_production = oxygen_production;
		this.factory_area = factory_area;
		this.factory_volume = factory_volume;
		this.temperature = temperature;
		this.pressure = pressure;
		this.state.getRotationState().setAttitudeQuaternion(OxygenConstant.ROTATION_OXYGEN_FACTORY);
		this.setPosition(position);
	}

	public SpaceTimeCoordinateState getState() {
		return state;
	}

	public void setState(SpaceTimeCoordinateState state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getMax_water_volume() {
		return max_water_volume;
	}

	public void setMax_water_volume(Double max_water_volume) {
		this.max_water_volume = max_water_volume;
	}

	public Double getCurrent_water_volume() {
		return current_water_volume;
	}

	public void setCurrent_water_volume(Double current_water_volume) {
		this.current_water_volume = current_water_volume;
	}

	public Double getMax_oxygen_volume() {
		return max_oxygen_volume;
	}

	public void setMax_oxygen_volume(Double max_oxygen_volume) {
		this.max_oxygen_volume = max_oxygen_volume;
	}

	public Double getCurrent_oxygen_volume() {
		return current_oxygen_volume;
	}

	public void setCurrent_oxygen_volume(Double current_oxygen_volume) {
		this.current_oxygen_volume = current_oxygen_volume;
	}

	public Double getMax_raw_material_volume() {
		return max_raw_material_volume;
	}

	public void setMax_raw_material_volume(Double max_raw_material_volume) {
		this.max_raw_material_volume = max_raw_material_volume;
	}

	public Double getCurrent_raw_material_volume() {
		return current_raw_material_volume;
	}

	public void setCurrent_raw_material_volume(Double current_raw_material_volume) {
		this.current_raw_material_volume = current_raw_material_volume;
	}

	public Double getEnergy_comsumption() {
		return energy_comsumption;
	}

	public void setEnergy_comsumption(Double energy_comsumption) {
		this.energy_comsumption = energy_comsumption;
	}

	public Double getOxygen_production() {
		return oxygen_production;
	}

	public void setOxygen_production(Double oxygen_production) {
		this.oxygen_production = oxygen_production;
	}

	public Double getFactory_area() {
		return factory_area;
	}

	public void setFactory_area(Double factory_area) {
		this.factory_area = factory_area;
	}

	public Double getFactory_volume() {
		return factory_volume;
	}

	public void setFactory_volume(Double factory_volume) {
		this.factory_volume = factory_volume;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Double getPressure() {
		return pressure;
	}

	public void setPressure(Double pressure) {
		this.pressure = pressure;
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

	public Quaternion getRotation() {
		return this.state.getRotationState().getAttitudeQuaternion();
	}

	@Override
	public String toString() {
		return "OxygenFactory [name=" + name + ", parent_name=" + parent_name + ", state=" + state + ", type=" + type
				+ ", max_water_volume=" + max_water_volume + ", current_water_volume=" + current_water_volume
				+ ", max_oxygen_volume=" + max_oxygen_volume + ", current_oxygen_volume=" + current_oxygen_volume
				+ ", max_raw_material_volume=" + max_raw_material_volume + ", current_raw_material_volume="
				+ current_raw_material_volume + ", energy_comsumption=" + energy_comsumption + ", oxygen_production="
				+ oxygen_production + ", factory_area=" + factory_area + ", factory_volume=" + factory_volume
				+ ", temperature=" + temperature + ", pressure=" + pressure + "]";
	}

}
