package interaction;

import coder.HLAPositionCoder;
import model.Position;
import skf.coder.HLAunicodeStringCoder;
import skf.model.interaction.annotations.InteractionClass;
import skf.model.interaction.annotations.Parameter;

@InteractionClass(name = "AstronautRequestPath")
public class RequestPath{
	
    @Parameter(name="AstronautID",coder= HLAunicodeStringCoder.class)
    protected String astronautId = null;
	
	@Parameter(name = "CurrentPosition",coder = HLAPositionCoder.class)
	private Position currentPostition = null;
	
	@Parameter(name = "TargetRoom", coder = HLAunicodeStringCoder.class)
	private String targetRoom = null;


	public RequestPath(){
		astronautId = "";
		currentPostition = new Position(0, 0, 0);
		targetRoom = "";
	}
	
	public RequestPath(Position currentPosition, String targetRoom, String astronautID){
		
		this.setCurrentPostition(currentPosition);
		this.targetRoom = targetRoom;
		this.astronautId = astronautID;
	}

	public String getTargetRoom() {
		return targetRoom;
	}

	public void setTargetRoom(String targetRoom) {
		this.targetRoom = targetRoom;
	}
	
    public String getAstronautId(){
        return astronautId;
    }

    public void setAstronautId(String astronautId){
        this.astronautId = astronautId;
    }

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result
//				+ ((getCurrentPostition() == null) ? 0 : getCurrentPostition().hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		RequestPath other = (RequestPath) obj;
//		if (getCurrentPostition() != other.getCurrentPostition() || astronautId !=other.astronautId
//				|| targetRoom != other.targetRoom)
//			return false;
//		return true;
//	}

	public Position getCurrentPostition() {
		return currentPostition;
	}

	public void setCurrentPostition(Position currentPostition) {
		this.currentPostition = currentPostition;
	}

	@Override
	public String toString() {
		return "RequestPath [astronautId=" + astronautId + ", currentPostition=" + currentPostition + ", targetRoom="
				+ targetRoom + "]";
	}
	
}
