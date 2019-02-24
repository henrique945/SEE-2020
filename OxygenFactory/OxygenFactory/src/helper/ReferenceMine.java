package helper;

import org.apache.commons.math3.complex.Quaternion;

import model.Position;

public class ReferenceMine {
	
	//Local where is saved reference position of Excavation Mine
	public static Position reference;
	public static Quaternion referenceRotation;
	
	public static void setReference(Position p){
		Position pos = new Position(p.getX()+241, p.getY()+74, p.getZ()-1.72303205);
		//Position pos = new Position(p.getX(), p.getY() - 300, p.getZ());
		reference = pos;
	}
	public static void setReferenceRotation(Quaternion pR){
		referenceRotation=pR;
	}
}
