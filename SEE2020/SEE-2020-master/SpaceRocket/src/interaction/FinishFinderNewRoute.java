package interaction;

import coder.PositionCollectionCoder;
import model.PositionCollection;
import skf.coder.HLAunicodeStringCoder;
import skf.model.interaction.annotations.InteractionClass;
import skf.model.interaction.annotations.Parameter;

import java.util.ArrayList;

@InteractionClass(name = "FinishFinderNewRoute")
public class FinishFinderNewRoute {

	@Parameter(name="FreePosition",coder = PositionCollectionCoder.class)
	private PositionCollection path = null;
	
	@Parameter(name="RoverID",coder= HLAunicodeStringCoder.class)
    private String roverId = null;
	
	public FinishFinderNewRoute(){
		roverId = "";
		path = new PositionCollection(new ArrayList<>());
	}
	
	public FinishFinderNewRoute(PositionCollection collection,String roverId){
		this.setPath(collection);
		this.roverId = roverId;
	}

	public PositionCollection getPath() {
		return path;
	}

	public String getRoverId() {
		return roverId;
	}

	public void setRoverId(String roverId) {
		this.roverId = roverId;
	}

	public void setPath(PositionCollection path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "FinishFinderNewRoute [path=" + path + ", roverId=" + roverId + "]";
	}

}
