package interaction;

import skf.coder.HLAunicodeStringCoder;
import skf.model.interaction.annotations.InteractionClass;
import skf.model.interaction.annotations.Parameter;

@InteractionClass(name = "ResponseGoToOxygenFactory")
public class ResponseGoToOxygenFactory {

	@Parameter(name = "RoverID", coder = HLAunicodeStringCoder.class)
	protected String roverID = null;

	@Parameter(name = "PermissionLevel", coder = HLAunicodeStringCoder.class)
	private String permissionLevel = null;

	public ResponseGoToOxygenFactory() {
		roverID = "";
		permissionLevel = "";
	}

	public ResponseGoToOxygenFactory(String permissionLevel, String roverID) {

		this.setPermissionLevel(permissionLevel);
		this.roverID = roverID;
	}

	public String getRoverID() {
		return roverID;
	}

	public void setRoverID(String roverID) {
		this.roverID = roverID;
	}

	public String getPermissionLevel() {
		return permissionLevel;
	}

	public void setPermissionLevel(String permissionLevel) {
		this.permissionLevel = permissionLevel;
	}

	@Override
	public String toString() {
		return "ResponseGoToOxygenFactory [roverID=" + roverID + ", permissionLevel=" + permissionLevel + "]";
	}

}
