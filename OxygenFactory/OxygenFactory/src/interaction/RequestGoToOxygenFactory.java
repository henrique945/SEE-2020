package interaction;

import skf.coder.HLAfloat64LECoder;
import skf.coder.HLAunicodeStringCoder;
import skf.model.interaction.annotations.InteractionClass;
import skf.model.interaction.annotations.Parameter;

@InteractionClass(name = "RequestGoToOxygenFactory")
public class RequestGoToOxygenFactory {

	@Parameter(name = "RoverID", coder = HLAunicodeStringCoder.class)
	protected String roverID = null;

	@Parameter(name = "RawMaterial", coder = HLAfloat64LECoder.class)
	protected Double rawMaterial = null;

	public RequestGoToOxygenFactory() {
		roverID = "";
		rawMaterial = 0.0;
	}

	public RequestGoToOxygenFactory(String roverID, Double rawMaterial) {
		this.roverID = roverID;
		this.rawMaterial = rawMaterial;
	}

	public String getRoverID() {
		return roverID;
	}

	public void setRoverID(String roverID) {
		this.roverID = roverID;
	}

	public Double getRawMaterial() {
		return rawMaterial;
	}

	public void setRawMaterial(Double rawMaterial) {
		this.rawMaterial = rawMaterial;
	}

	@Override
	public String toString() {
		return "RequestGoToOxygenFactory [roverID=" + roverID + ", rawMaterial=" + rawMaterial + "]";
	}

}
