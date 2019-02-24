package model;

import org.apache.commons.math3.complex.Quaternion;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.siso.spacefom.frame.SpaceTimeCoordinateState;
import org.siso.spacefom.physicalEntity.PhysicalEntity;

import coder.SpaceTimeCoordinateStateCoder;
import skf.coder.HLAunicodeStringCoder;
import skf.model.object.annotations.Attribute;
import skf.model.object.annotations.ObjectClass;

@ObjectClass(name = "PhysicalEntity.Luha")
public class LunarHabitatBase {
	
	@Attribute(name = "name", coder = HLAunicodeStringCoder.class)
	private String name = null;

	@Attribute(name = "state", coder = SpaceTimeCoordinateStateCoder.class)
	private SpaceTimeCoordinateState state = null;
	
	public LunarHabitatBase() {}
	
	public LunarHabitatBase(String name, Position position){
		//super(name);
		this.name = name;
		this.state = new SpaceTimeCoordinateState();
		this.state.getTranslationalState();
		this.setPosition(position);		
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
	public Quaternion getRotation(){
		return this.state.getRotationState().getAttitudeQuaternion();
	}
	public SpaceTimeCoordinateState getState() {
		return state;
	}

	public void setState(SpaceTimeCoordinateState state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "LunarHabitatBase [name=" + name + ", state=" + state + "]";
	}
	
	
}
