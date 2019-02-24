package interaction;

import java.util.ArrayList;

import coder.PositionCollectionCoder;
import model.PositionCollection;
import skf.coder.HLAunicodeStringCoder;
import skf.model.interaction.annotations.InteractionClass;
import skf.model.interaction.annotations.Parameter;

@InteractionClass(name = "ResponseFinderNewRoute")
public class ResponseFinderNewRoute{
	
	@Parameter(name="Path",coder = PositionCollectionCoder.class)
	private PositionCollection path = null;
	
	@Parameter(name="RoverID",coder= HLAunicodeStringCoder.class)
    protected String roverID = null;

	
	public ResponseFinderNewRoute(){
		roverID = "";
		path = new PositionCollection(new ArrayList<>());
	}
	
	public ResponseFinderNewRoute(PositionCollection collection,String roverID){
		this.path = collection;
		this.roverID = roverID;
	}

	public PositionCollection getPath() {
		return path;
	}

	public void setPath(PositionCollection path) {
		this.path = path;
	}

	public String getRoverID() {
		return roverID;
	}

	public void setRoverID(String roverID) {
		this.roverID = roverID;
	}

	@Override
	public String toString() {
		return "ResponseFinderNewRoute [path=" + path + ", roverID=" + roverID + "]";
	}

}
