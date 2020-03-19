package interaction;

import coder.HLAPositonCoder;
import model.Position;
import skf.coder.HLAunicodeStringCoder;
import skf.model.interaction.annotations.InteractionClass;
import skf.model.interaction.annotations.Parameter;

@InteractionClass(name = "RequestOxygenFactoryRoute")
public class RequestOxygenFactoryRoute {
	
	@Parameter(name = "RoverID", coder = HLAunicodeStringCoder.class)
	protected String roverID = null;

	@Parameter(name = "CurrentPosition", coder = HLAPositonCoder.class)
	private Position currentPosition = null;

	public RequestOxygenFactoryRoute() {
		roverID = "";
		currentPosition = new Position(0, 0, 0);
	}

	public RequestOxygenFactoryRoute(Position currentPosition, String roverID) {

		this.setCurrentPosition(currentPosition);
		this.roverID = roverID;
	}

	public String getRoverID() {
		return roverID;
	}

	public void setRoverID(String roverID) {
		this.roverID = roverID;
	}

	public Position getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Position currentPosition) {
		this.currentPosition = currentPosition;
	}

	@Override
	public String toString() {
		return "RequestOxygenFactoryRoute [roverID=" + roverID + ", currentPosition=" + currentPosition + "]";
	}

}
