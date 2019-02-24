package model;

import org.apache.commons.math3.complex.Quaternion;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.siso.spacefom.frame.SpaceTimeCoordinateState;

import coder.SpaceTimeCoordinateStateCoder;
import skf.coder.HLAfloat64LECoder;
import skf.coder.HLAinteger32BECoder;
import skf.coder.HLAunicodeStringCoder;
import skf.model.object.annotations.Attribute;
import skf.model.object.annotations.ObjectClass;

@ObjectClass(name = "PhysicalEntity.LunarHabitatAstronaut")
public class Astronaut {

	@Attribute(name = "name", coder = HLAunicodeStringCoder.class)
	private String name = null;
	
	@Attribute(name = "parent_reference_frame", coder = HLAunicodeStringCoder.class)
	private String parent_name = null;

	@Attribute(name = "state", coder = SpaceTimeCoordinateStateCoder.class)
	private SpaceTimeCoordinateState state = null;

	@Attribute(name = "type", coder = HLAunicodeStringCoder.class)
	private String type = null;

	@Attribute(name = "amount_oxygen", coder = HLAfloat64LECoder.class)
	private Double amount_oxygen = null;
	
	@Attribute(name = "amount_carbon_dioxide", coder = HLAfloat64LECoder.class)
	private Double amount_carbon_dioxide = null;
	
	@Attribute(name = "amount_calories", coder = HLAfloat64LECoder.class)
	private Double amount_calories = null;
	
	@Attribute(name = "pulse_oximetry", coder = HLAinteger32BECoder.class)
	private Integer pulse_oximetry = null;

	@Attribute(name = "heart_rate", coder = HLAinteger32BECoder.class)
	private Integer heart_rate = null;

	@Attribute(name = "temperature", coder = HLAfloat64LECoder.class)
	private Double temperature = null;

	@Attribute(name = "blood_pressure_systolic", coder = HLAinteger32BECoder.class)
	private Integer blood_pressure_systolic = null;

	@Attribute(name = "blood_pressure_diastolic", coder = HLAinteger32BECoder.class)
	private Integer blood_pressure_diastolic = null;
	
	public Astronaut() {
	}

	public Astronaut(String name, Position position, String type) {
		this.name = name;
		parent_name = siso.smackdown.frame.FrameType.AitkenBasinLocalFixed.toString();
		this.state = new SpaceTimeCoordinateState();
		this.setPosition(position);
		this.type = type;
		idle();
	}
	
	public void itsWaking() {
		this.pulse_oximetry = 97;
		this.heart_rate = 90;
		this.temperature = (double) 37.5;
		this.blood_pressure_systolic = 125;
		this.blood_pressure_diastolic = 85;
		this.amount_oxygen = (double) 0.15; //0.50 liters
		this.amount_carbon_dioxide = (double) 0.15;
	}

	public void idle() {
		this.pulse_oximetry = 100;
		this.heart_rate = 80;
		this.temperature = (double) 37.2;
		this.blood_pressure_systolic = 110;
		this.blood_pressure_diastolic = 75;
		this.amount_oxygen = (double) 0.13;
		this.amount_carbon_dioxide = (double) 0.13;
		this.amount_calories = (double) 0.003; //3 times in Kcal
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Position getPosition() {
		Vector3D vector3d = state.getTranslationalState().getPosition();
		return new Position(vector3d.getX(), vector3d.getY(), vector3d.getZ());
		
	}

	public void setPosition(Position position) {
		Vector3D vector3d = new Vector3D(position.getX(), position.getY(), position.getZ());
		this.state.getTranslationalState().setPosition(vector3d);
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

	public Double getAmount_oxygen() {
		return amount_oxygen;
	}

	public void setAmount_oxygen(Double amount_oxygen) {
		this.amount_oxygen = amount_oxygen;
	}

	public Double getAmount_carbon_dioxide() {
		return amount_carbon_dioxide;
	}

	public void setAmount_carbon_dioxide(Double amount_carbon_dioxide) {
		this.amount_carbon_dioxide = amount_carbon_dioxide;
	}

	public String getParent_name() {
		return parent_name;
	}

	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}

	public Double getAmount_calories() {
		return amount_calories;
	}

	public void setAmount_calories(Double amount_calories) {
		this.amount_calories = amount_calories;
	}
	
	public SpaceTimeCoordinateState getState() {
		return state;
	}

	public void setState(SpaceTimeCoordinateState state) {
		this.state = state;
	}
	
	public Integer getPulse_oximetry() {
		return pulse_oximetry;
	}

	public void setPulse_oximetry(Integer pulse_oximetry) {
		this.pulse_oximetry = pulse_oximetry;
	}

	public Integer getHeart_rate() {
		return heart_rate;
	}

	public void setHeart_rate(Integer heart_rate) {
		this.heart_rate = heart_rate;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Integer getBlood_pressure_systolic() {
		return blood_pressure_systolic;
	}

	public void setBlood_pressure_systolic(Integer blood_pressure_systolic) {
		this.blood_pressure_systolic = blood_pressure_systolic;
	}

	public Integer getBlood_pressure_diastolic() {
		return blood_pressure_diastolic;
	}

	public void setBlood_pressure_diastolic(Integer blood_pressure_diastolic) {
		this.blood_pressure_diastolic = blood_pressure_diastolic;
	}

	@Override
	public String toString() {
		return "Astronaut [name=" + name + ", parent_name=" + parent_name + ", state=" + state + ", type=" + type
				+ ", amount_oxygen=" + amount_oxygen + ", amount_carbon_dioxide=" + amount_carbon_dioxide
				+ ", amount_calories=" + amount_calories + ", pulse_oximetry=" + pulse_oximetry + ", heart_rate="
				+ heart_rate + ", temperature=" + temperature + ", blood_pressure_systolic=" + blood_pressure_systolic
				+ ", blood_pressure_diastolic=" + blood_pressure_diastolic + "]";
	}

	
	
	
}
