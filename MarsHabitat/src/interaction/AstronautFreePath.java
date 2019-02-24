package interaction;

import coder.PositionCollectionCoder;
import model.PositionCollection;
import skf.coder.HLAunicodeStringCoder;
import skf.model.interaction.annotations.InteractionClass;
import skf.model.interaction.annotations.Parameter;

import java.util.ArrayList;

@InteractionClass(name = "AstronautFreePath")
public class AstronautFreePath {

	@Parameter(name="FreePosition",coder = PositionCollectionCoder.class)
	private PositionCollection path = null;
	
	@Parameter(name="AstronautID",coder= HLAunicodeStringCoder.class)
    private String astronautId = null;
	
	public AstronautFreePath(){
		astronautId = "";
		path = new PositionCollection(new ArrayList<>());
	}
	
	public AstronautFreePath(PositionCollection collection,String astronautID){
		this.setPath(collection);
		this.astronautId = astronautID;
	}

	public PositionCollection getPath() {
		return path;
	}

	public void setPath(PositionCollection path) {
		this.path = path;
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
//				+ ((astronautId == null) ? 0 : astronautId.hashCode());
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
//		AstronautFreePath other = (AstronautFreePath) obj;
//		if (path != other.path || astronautId !=other.astronautId)
//			return false;
//		return true;
//	}

	@Override
	public String toString() {
		return "AstronautFreePath [path=" + path + ", astronautId=" + astronautId + "]";
	}


}
