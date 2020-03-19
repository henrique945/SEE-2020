package model;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.siso.spacefom.frame.SpaceTimeCoordinateState;

import coder.RooftopStatusCoder;
import coder.SpaceTimeCoordinateStateCoder;
import constant.OxygenConstant;
import skf.coder.HLAunicodeStringCoder;
import skf.model.object.annotations.Attribute;
import skf.model.object.annotations.ObjectClass;

@ObjectClass(name = "PhysicalEntity.FACENS_Oxygen_Factory_Rooftop")
public class OxygenFactoryRooftop {

	@Attribute(name = "name", coder = HLAunicodeStringCoder.class)
	private String name = null;

	@Attribute(name = "parent_reference_frame", coder = HLAunicodeStringCoder.class)
	private String parent_name = null;

	@Attribute(name = "state", coder = SpaceTimeCoordinateStateCoder.class)
	private SpaceTimeCoordinateState state = null;

	@Attribute(name = "type", coder = HLAunicodeStringCoder.class)
	private String type = null;

	@Attribute(name = "rooftopStatus", coder = RooftopStatusCoder.class)
	private OxygenFactoryRooftopStatus rooftopStatus;

	public OxygenFactoryRooftop() {
	}

	public OxygenFactoryRooftop(String name, String parent_name, String type, Position position) {
		this.name = name;
		this.parent_name = parent_name;
		this.type = type;
		this.state = new SpaceTimeCoordinateState();
		this.setPosition(position);
		this.state.getRotationState().setAttitudeQuaternion(OxygenConstant.ROTATION_OXYGEN_FACTORY);
		this.rooftopStatus = OxygenFactoryRooftopStatus.STOPPED_UP;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public OxygenFactoryRooftopStatus getRooftopStatus() {
		return rooftopStatus;
	}

	public void setRooftopStatus(OxygenFactoryRooftopStatus rooftopStatus) {
		this.rooftopStatus = rooftopStatus;
	}

	@Override
	public String toString() {
		return name + ", parent_name=" + parent_name + ", state=" + state + ", type=" + type + ", rooftopStatus="
				+ rooftopStatus;
	}

}
