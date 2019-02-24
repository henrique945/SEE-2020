package helper;

import constant.OxygenConstant;
import model.OxygenFactory;

public class OxygenManager {
	
	private OxygenFactory factory;
	private double currentVolume = OxygenConstant.CURRENT_OXYGEN_VOLUME;
	
	public OxygenManager(OxygenFactory factory) {
		this.setFactory(factory);
	}
	
	public void calculateOxygen(double materials) {
		
		setCurrentVolume(getCurrentVolume() + materials * OxygenConstant.OXYGEN_MULT);
	}

	public double getCurrentVolume() {
		return currentVolume;
	}

	public void setCurrentVolume(double currentVolume) {
		this.currentVolume = currentVolume;
	}

	public OxygenFactory getFactory() {
		return factory;
	}

	public void setFactory(OxygenFactory factory) {
		this.factory = factory;
	}
}

