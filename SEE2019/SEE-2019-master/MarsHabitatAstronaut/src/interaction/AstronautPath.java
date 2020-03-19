package interaction;

import java.util.ArrayList;

import coder.PositionCollectionCoder;
import model.PositionCollection;
import skf.coder.HLAunicodeStringCoder;
import skf.model.interaction.annotations.InteractionClass;
import skf.model.interaction.annotations.Parameter;

@InteractionClass(name = "AstronautPath")
public class AstronautPath{
	
	@Parameter(name="Path",coder = PositionCollectionCoder.class)
	private PositionCollection path = null;
	
	@Parameter(name="AstronautID",coder= HLAunicodeStringCoder.class)
    protected String astronautId;

	
	public AstronautPath(){
		astronautId = "";
		path = new PositionCollection(new ArrayList<>());
	}
	
	public AstronautPath(PositionCollection collection,String astronautID){
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
//				+ ((path == null) ? 0 : path.hashCode());
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
//		AstronautPath other = (AstronautPath) obj;
//		if (path != other.path || astronautId !=other.astronautId)
//			return false;
//		return true;
//	}

	@Override
	public String toString() {
		return "AstronautPath [path=" + path + ", astronautId=" + astronautId + "]";
	}
	
}
