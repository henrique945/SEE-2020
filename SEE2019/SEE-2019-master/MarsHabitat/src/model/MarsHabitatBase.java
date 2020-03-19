package model;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.siso.spacefom.frame.SpaceTimeCoordinateState;
import org.siso.spacefom.physicalEntity.PhysicalEntity;

import coder.SpaceTimeCoordinateStateCoder;
import constant.MarsHabitatConstant;
import skf.coder.HLAfloat64BECoder;
import skf.coder.HLAfloat64LECoder;
import skf.coder.HLAinteger16LECoder;
import skf.coder.HLAunicodeStringCoder;
import skf.model.object.annotations.Attribute;
import skf.model.object.annotations.ObjectClass;

@ObjectClass(name = "PhysicalEntity.Luha")
public class MarsHabitatBase {

	@Attribute(name = "name", coder = HLAunicodeStringCoder.class)
	private String name = null;

	@Attribute(name = "parent_reference_frame", coder = HLAunicodeStringCoder.class)
	private String parent_name = null;

	@Attribute(name = "state", coder = SpaceTimeCoordinateStateCoder.class)
	private SpaceTimeCoordinateState state = null;

	@Attribute(name = "type", coder = HLAunicodeStringCoder.class)
	private String type = null;

	@Attribute(name = "pressure", coder = HLAfloat64LECoder.class)
	private Double pressure = null;

	@Attribute(name = "temperature", coder = HLAfloat64LECoder.class)
	private Double temperature = null;

	@Attribute(name = "area", coder = HLAfloat64LECoder.class)
	private Double area = null;

	@Attribute(name = "volume", coder = HLAfloat64LECoder.class)
	private Double volume = null;

	@Attribute(name = "energy_consumption", coder = HLAfloat64BECoder.class)
	private Double energy_consumption = null;

	@Attribute(name = "oxygen_consumption", coder = HLAfloat64LECoder.class)
	private Double oxygen_consumption = null;

	@Attribute(name = "carbon_dioxide_emission", coder = HLAfloat64LECoder.class)
	private Double carbon_dioxide_emission = null;

	@Attribute(name = "air_humidity", coder = HLAfloat64LECoder.class)
	private Double air_humidity = null;
	
	@Attribute(name = "calories_consumption", coder = HLAfloat64LECoder.class)
	private Double calories_consumption = null;
	
	@Attribute(name = "maximun_capacity", coder = HLAinteger16LECoder.class)
	private Short maximun_capacity = null;

	@Attribute(name = "number_of_astrounauts", coder = HLAinteger16LECoder.class)
	private Short number_of_astrounauts = null;
		
	public MarsHabitatBase() {
	}

	public MarsHabitatBase(String name, String parent_name, String type, Position position, Double pressure,
			Double temperature, Double area, Double volume, Double energy_consumption, Double oxygen_consumption
			, Double carbon_dioxide_emission, Double air_humidity,Double calories_consumption, Short maximun_capacity, Short number_of_astrounauts
			) {
		//super(name);
		this.name = name;
		this.parent_name = parent_name;
		this.type = type;
		this.state = new SpaceTimeCoordinateState();
		this.setPosition(position);
		this.state.getRotationState().setAttitudeQuaternion(MarsHabitatConstant.ROTATION_MARS_HABITAT);
		this.pressure = pressure;
		this.temperature = temperature;
		this.area = area;
		this.volume = volume;
		this.energy_consumption = energy_consumption;
		this.oxygen_consumption = oxygen_consumption;
		this.carbon_dioxide_emission = carbon_dioxide_emission;
		this.air_humidity = air_humidity;
		this.calories_consumption = calories_consumption;
		this.maximun_capacity = maximun_capacity;
		this.number_of_astrounauts = number_of_astrounauts;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParent_name() {
		return parent_name;
	}

	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}

	public Position getPosition() {
		Vector3D vector3d = state.getTranslationalState().getPosition();
		return new Position(vector3d.getX(), vector3d.getY(), vector3d.getZ());
		
	}

	public void setPosition(Position position) {
		Vector3D vector3d = new Vector3D(position.getX(), position.getY(), position.getZ());
		this.state.getTranslationalState().setPosition(vector3d);
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

	public Double getEnergy_consumption() {
		return energy_consumption;
	}

	public void setEnergy_consumption(Double energy_consumption) {
		this.energy_consumption = energy_consumption;
	}
	

	public Double getOxygen_consumption() {
		return oxygen_consumption;
	}

	public void setOxygen_consumption(Double oxygen_consumption) {
		this.oxygen_consumption = oxygen_consumption;
	}

	public Double getCalories_consumption() {
		return calories_consumption;
	}

	public void setCalories_consumption(Double calories_consumption) {
		this.calories_consumption = calories_consumption;
	}

	public Short getMaximun_capacity() {
		return maximun_capacity;
	}

	public void setMaximun_capacity(Short maximun_capacity) {
		this.maximun_capacity = maximun_capacity;
	}

	public Short getNumber_of_astrounauts() {
		return number_of_astrounauts;
	}

	public void setNumber_of_astrounauts(Short number_of_astrounauts) {
		this.number_of_astrounauts = number_of_astrounauts;
	}

	public double getCarbonDioxide_Emission() {
		return carbon_dioxide_emission;
	}

	public void setCarbonDioxide_Emission(double currentCarbonDioxideEmission) {
		this.carbon_dioxide_emission = currentCarbonDioxideEmission;
	}
	
	public Double getCarbon_dioxide_emission() {
		return carbon_dioxide_emission;
	}

	public void setCarbon_dioxide_emission(Double carbon_dioxide_emission) {
		this.carbon_dioxide_emission = carbon_dioxide_emission;
	}

	public Double getAir_humidity() {
		return air_humidity;
	}

	public void setAir_humidity(Double air_humidity) {
		this.air_humidity = air_humidity;
	}
	public SpaceTimeCoordinateState getState() {
		return state;
	}

	public void setState(SpaceTimeCoordinateState state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "MarsHabitatBase [name=" + name + ", parent_name=" + parent_name + ", state=" + state + ", type=" + type
				+ ", pressure=" + pressure + ", temperature=" + temperature + ", area=" + area + ", volume=" + volume
				+ ", energy_consumption=" + energy_consumption + ", oxygen_consumption=" + oxygen_consumption
				+ ", carbon_dioxide_emission=" + carbon_dioxide_emission + ", air_humidity=" + air_humidity
				+ ", calories_consumption=" + calories_consumption + ", maximun_capacity=" + maximun_capacity
				+ ", number_of_astrounauts=" + number_of_astrounauts + "]";
	}

	
}
