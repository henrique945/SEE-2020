package helper;

import org.apache.commons.math3.complex.Quaternion;

import model.Position;

public class ReferenceHabitat {

	public static Position reference;
	public static Quaternion referenceRotation;

	public static void setReference(Position p) {
		Position pos = new Position(p.getX(), p.getY(), p.getZ());
		reference = pos;
	}

	public static void setReferenceRotation(Quaternion pR) {
		referenceRotation = pR;
	}
}