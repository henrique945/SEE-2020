package model;

import org.apache.commons.math3.complex.Quaternion;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.siso.spacefom.frame.SpaceTimeCoordinateState;

import coder.HLAPositionCoder;
import coder.SpaceTimeCoordinateStateCoder;
import skf.coder.HLAfloat64LECoder;
import skf.coder.HLAunicodeStringCoder;
import skf.model.object.annotations.Attribute;
import skf.model.object.annotations.ObjectClass;

@ObjectClass(name = "PhysicalEntity.FACENS_Space_Probe")
public class SpaceProbe {
	
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
	
	@Attribute(name = "temperature", coder = HLAfloat64LECoder.class)
	private Double temperature = null;

	@Attribute(name = "length", coder = HLAfloat64LECoder.class)
	private Double length = null;

	@Attribute(name = "width", coder = HLAfloat64LECoder.class)
	private Double width = null;

	@Attribute(name = "height", coder = HLAfloat64LECoder.class)
	private Double height = null;
	
	@Attribute(name = "position", coder = HLAPositionCoder.class)
	private Position position = null;
	
	public SpaceProbe(String name, String parent_name, String type) {
		
		this.name = name;
		this.parent_name = parent_name;
		this.type = type;		
		this.isIdle();
	}
	
	public SpaceProbe(String name, String parent_name, String type, Position position) {
		
		this.name = name;
		this.parent_name = parent_name;
		this.type = type;		
		this.setPosition(position);		
		this.isIdle();
	}

	public SpaceProbe(String name, String parent_name, String type, Double battery_level, Double temperature, 
			Double length, Double width, Double height, Position position) {
		
		this.name = name;
		this.parent_name = parent_name;
		this.type = type;
		this.state = null;
		this.setPosition(position);
		this.battery_level = battery_level;
		this.temperature = temperature;
		this.length = length;
		this.width = width;
		this.height = height;
		this.isIdle();
	}
	
	public void isIdle() {
		this.setTemperature((double) -30);
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

	public Double getBattery_level() {
		return battery_level;
	}

	public void setBattery_level(Double battery_level) {
		this.battery_level = battery_level;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
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

	public void setPosition(Position position) {
		Vector3D vector3d = new Vector3D(position.getX(), position.getY(), position.getZ());
		this.state.getTranslationalState().setPosition(vector3d);
	}

	public Position getPosition() {
		Vector3D vector3d = state.getTranslationalState().getPosition();
		return new Position(vector3d.getX(), vector3d.getY(), vector3d.getZ());

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

	@Override
	public String toString() {
		return name + ", state=" + state + ", battery_level=" + battery_level + ", temperature=" + temperature + ", length="
				+ length + ", width=" + width + ", height=" + height + ", position=" + getPosition();
	}	
	
}