package model;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PositionCollection {

	private List<Vector3D> array;
	
	public PositionCollection(){
		array = new ArrayList<>();
	}
	
	public PositionCollection(List<Vector3D> list){
		
		this.array = list;
		
	}
	
	public List<Vector3D> getArrayList(){
		return Collections.unmodifiableList(array);
	}
	
	public void setArrayList(List<Vector3D> array){
		this.array=array;
	}

	@Override
	public String toString() {
		return "PositionCollection [array=" + array + "]";
	}
	
	
}
