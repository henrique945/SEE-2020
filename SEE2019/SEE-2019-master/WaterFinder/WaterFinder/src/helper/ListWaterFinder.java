package helper;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import model.WaterFinder;
import model.Position;

public class ListWaterFinder {
	// map where do control all instances MarsMiner
	private Map<Integer, WaterFinder> mapWaterFinder;

	// map where is saved all list of position the MarsMiner
	private Map<Integer, ArrayList<Position>> mapPosition;

	// map where do control if MarsMiners its Walking or Idle
	private Map<Integer, Boolean> mapGoIn;
	
	private Map<Integer,TargetPosition> mapTargetPosition;
	
	private boolean enableRequestPath = true;

	public ListWaterFinder() {
		mapWaterFinder = new TreeMap<Integer, WaterFinder>();
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

	public void addWaterFinder(WaterFinder m, int index) {
		mapWaterFinder.put(index, m);
		mapGoIn.put(index, false);
		mapPosition.put(index, new ArrayList<Position>());
		mapTargetPosition.put(index,new TargetPosition());
	}

	public WaterFinder GetWaterFinder(int index) {
		return mapWaterFinder.get(index);
	}

	public int size() {
		return mapWaterFinder.size();
	}

	public Map<Integer, WaterFinder> getMapWaterFinder() {
		return mapWaterFinder;
	}

	public Boolean GetGoIn(int index) {
		return mapGoIn.get(index);
	}
	
	public TargetPosition GetTargetPosition(int index) {
		enableRequestPath=false;
		return mapTargetPosition.get(index);
	}

	public void setMapWaterFinder(Map<Integer, WaterFinder> mapMarsMiner) {
		this.mapWaterFinder = mapMarsMiner;
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
}
