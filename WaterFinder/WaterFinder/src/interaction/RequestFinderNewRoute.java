package interaction;

import coder.HLAPositonCoder;
import model.Position;
import skf.coder.HLAunicodeStringCoder;
import skf.model.interaction.annotations.InteractionClass;
import skf.model.interaction.annotations.Parameter;

@InteractionClass(name = "RequestFinderNewRoute")
public class RequestFinderNewRoute {

	@Parameter(name = "RoverID", coder = HLAunicodeStringCoder.class)
	protected String roverID = null;

	@Parameter(name = "CurrentPosition", coder = HLAPositonCoder.class)
	private Position currentPosition = null;
	
	@Parameter(name = "TargetPosition", coder = HLAunicodeStringCoder.class)
	private String targetPosition = null;

	public RequestFinderNewRoute() {
		roverID = "";
		currentPosition = new Position(0, 0, 0);
		targetPosition = "";
	}

	public RequestFinderNewRoute(Position currentPosition,String targetPosition, String roverID) {

		this.setCurrentPosition(currentPosition);
		this.targetPosition = targetPosition;
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

	public String getTargetPosition() {
		return targetPosition;
	}

	public void setTargetPosition(String targetPosition) {
		this.targetPosition = targetPosition;
	}

	@Override
	public String toString() {
		return "RequestFinderNewRoute [roverID=" + roverID + ", currentPosition=" + currentPosition
				+ ", targetPosition=" + targetPosition + "]";
	}
}
