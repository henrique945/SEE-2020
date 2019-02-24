package helper;

import org.apache.commons.math3.complex.Quaternion;

import model.Position;

public class ReferenceMine {
	
	//Local where is saved reference position of Excavation Mine
	public static Position reference;
	public static Quaternion referenceRotation;
	
	public static void setReference(Position p){
		Position pos = new Position(p.getX()+206, p.getY()+34, p.getZ()-1.72303205);
		reference = pos;
	}
	public static void setReferenceRotation(Quaternion pR){
		referenceRotation=pR;
	}
}
