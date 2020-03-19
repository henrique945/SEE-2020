package helper;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import model.MarsMiner;
import model.Position;

public class ListMarsMiner {
	// map where do control all instances MarsMiner
	private Map<Integer, MarsMiner> mapMarsMiner;

	// map where is saved all list of position the MarsMiner
	private Map<Integer, ArrayList<Position>> mapPosition;

	// map where do control if MarsMiners its Walking or Idle
	private Map<Integer, Boolean> mapGoIn;
	
	private Map<Integer,TargetPosition> mapTargetPosition;
	
	private boolean enableRequestPath = true;

	public ListMarsMiner() {
		mapMarsMiner = new TreeMap<Integer, MarsMiner>();
		mapPosition = new TreeMap<Integer, ArrayList<Position>>();
		mapGoIn = new TreeMap<Integer, Boolean>();
		mapTargetPosition =  new TreeMap<Integer, TargetPosition>();
	}

	public void addArrayList(ArrayList<Position> a, int index) {
		mapPosition.put(index, a);
	}

	public ArrayList<Position> GetArrayList(int index) {
		return mapPosition.get(index);
	}

	public void setArrayList(int index, ArrayList<Position> l) {
		mapPosition.put(index, l);
	}

	public void addMarsMiner(MarsMiner m, int index) {
		mapMarsMiner.put(index, m);
		mapGoIn.put(index, false);
		mapPosition.put(index, new ArrayList<Position>());
		mapTargetPosition.put(index,new TargetPosition());
	}

	public MarsMiner GetMarsMiner(int index) {
		return mapMarsMiner.get(index);
	}

	public int size() {
		return mapMarsMiner.size();
	}

	public Map<Integer, MarsMiner> getMapMarsMiner() {
		return mapMarsMiner;
	}

	public Boolean GetGoIn(int index) {
		return mapGoIn.get(index);
	}
	
	public TargetPosition GetTargetPosition(int index) {
		enableRequestPath=false;
		return mapTargetPosition.get(index);
	}

	public void setMapMarsMiner(Map<Integer, MarsMiner> mapMarsMiner) {
		this.mapMarsMiner = mapMarsMiner;
	}

	public Map<Integer, ArrayList<Position>> getMapPosition() {
		return mapPosition;
	}

	public void setMapPosition(Map<Integer, ArrayList<Position>> mapPosition) {
		this.mapPosition = mapPosition;
	}

	public Map<Integer, Boolean> getMapGoIn() {
		return mapGoIn;
	}

	public void setGoIn(int index, Boolean flag) {
		mapGoIn.put(index, flag);
	}

	public void setMapGoIn(Map<Integer, Boolean> mapGoIn) {
		this.mapGoIn = mapGoIn;
	}

	public Map<Integer, TargetPosition> getMapTargetPosition() {
		return mapTargetPosition;
	}

	public void setMapTargetPosition(Map<Integer, TargetPosition> mapTargetPosition) {
		this.mapTargetPosition = mapTargetPosition;
	}
	
	public boolean isEnableRequestPath() {
		return enableRequestPath;
	}

	public void setEnableRequestPath(boolean enableRequestPath) {
		this.enableRequestPath = enableRequestPath;
	}

	@Override
	public String toString() {
		return "ListMarsMiner [mapMarsMiner=" + mapMarsMiner + ", mapPosition=" + mapPosition + ", mapGoIn=" + mapGoIn
				+ ", mapTargetPosition=" + mapTargetPosition + ", enableRequestPath=" + enableRequestPath + "]";
	}
	
	

}
