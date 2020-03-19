package model;

import skf.coder.HLAunicodeStringCoder;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.siso.spacefom.frame.SpaceTimeCoordinateState;

import coder.SpaceTimeCoordinateStateCoder;
import skf.coder.HLAfloat64LECoder;
import skf.model.object.annotations.Attribute;
import skf.model.object.annotations.ObjectClass;

@ObjectClass(name = "PhysicalEntity.LunarHabitatAstronaut")
public class LunarHabitatAstronaut {

	@Attribute(name = "name", coder = HLAunicodeStringCoder.class)
	private String name = null;

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
	
	public LunarHabitatAstronaut() {
	}

	public LunarHabitatAstronaut(String name, Position position, String type) {
		this.name = name;
		this.state = new SpaceTimeCoordinateState();
		this.setPosition(position);
		this.type = type;
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

	@Override
	public String toString() {
		return "LunarHabitatAstronaut [name=" + name + ", state=" + state + ", type=" + type + ", amount_oxygen="
				+ amount_oxygen + ", amount_carbon_dioxide=" + amount_carbon_dioxide + ", amount_calories="
				+ amount_calories + "]";
	}
	
}
