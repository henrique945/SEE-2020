package model;

import org.apache.commons.math3.complex.Quaternion;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.siso.spacefom.frame.SpaceTimeCoordinateState;

import coder.SpaceTimeCoordinateStateCoder;
import skf.coder.HLAfloat64LECoder;
import skf.coder.HLAunicodeStringCoder;
import skf.model.object.annotations.Attribute;
import skf.model.object.annotations.ObjectClass;

@ObjectClass(name = "PhysicalEntity.FACENS_Space_Elevator_Base")
public class SpaceRocket {

	@Attribute(name = "name", coder = HLAunicodeStringCoder.class)
	private String name = null;
	
	@Attribute(name = "parent_reference_frame", coder = HLAunicodeStringCoder.class)
	private String parent_name = null;
	
	@Attribute(name = "state", coder = SpaceTimeCoordinateStateCoder.class)
	private SpaceTimeCoordinateState state = null;

	@Attribute(name = "type", coder = HLAunicodeStringCoder.class)
	private String type = null;
	
	@Attribute(name = "Power_Supply", coder = HLAfloat64LECoder.class)
	private Double power_supply = null;
	
	@Attribute(name = "Power_Consumption", coder = HLAfloat64LECoder.class)
	private Double power_consumption = null;
	
	@Attribute(name = "Battery_Level", coder = HLAfloat64LECoder.class)
	private Double battery_level = null;
	
	public SpaceRocket(){}
	
	public SpaceRocket(String name, String parent_name, String type,
			Double power_supply, Double power_consumption, Double battery_level,
			Position position) {
		super();
		this.name = name;
		this.parent_name = parent_name;
		this.state = new SpaceTimeCoordinateState();
		this.type = type;
		this.battery_level = battery_level;
		this.power_consumption = power_consumption;
		this.power_supply = power_supply;
		this.state.getRotationState().setAttitudeQuaternion(new Quaternion(0, 0, -0.7071, -0.7071));
		this.setPosition(position);
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

	public Double getPower_supply() {
		return power_supply;
	}

	public void setPower_supply(Double power_supply) {
		this.power_supply = power_supply;
	}

	public Double getPower_consumption() {
		return power_consumption;
	}

	public void setPower_consumption(Double power_consumption) {
		this.power_consumption = power_consumption;
	}

	public Double getBattery_level() {
		return battery_level;
	}

	public void setBattery_level(Double battery_level) {
		this.battery_level = battery_level;
	}
	
	public void setPosition(Position position) {
		Vector3D vector3d = new Vector3D(position.getX(), position.getY(), position.getZ());
		this.state.getTranslationalState().setPosition(vector3d);
	}

	public Position getPosition() {
		Vector3D vector3d = state.getTranslationalState().getPosition();
		return new Position(vector3d.getX(), vector3d.getY(), vector3d.getZ());

	}

	@Override
	public String toString() {
		return "SpaceRocket [name=" + name + ", parent_name=" + parent_name + ", state=" + state + ", type=" + type
				+ ", power_supply=" + power_supply + ", power_consumption=" + power_consumption + ", battery_level="
				+ battery_level + "]";
	}
	
	
}
