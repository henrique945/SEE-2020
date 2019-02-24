package helper;

import constant.MarsHabitatConstant;

public class OxygenConsumption1 {

	/// This class represent the oxygen consumption inside the luha
	
	private double baseOxygenConsumption;		/// the consumption without astronauts, initial value
	private int currentNumberOfAstronauts;			/// current quantity of astronauts inside the luha
	private double currentOxygenVolumeAvailable;	/// current oxygen volume available inside the luha
	private double initialOxygenVolumeAvailable;	/// the initial volume of oxygen inside the base, it is used for measure the current volume
	private double currentCarbonDioxidEmission;		/// the current carbon dioxide  volume emission
	
	public OxygenConsumption1() {
		this.initialOxygenVolumeAvailable = MarsHabitatConstant.OXYGEN_VOLUME_INITIAL;
		this.baseOxygenConsumption = MarsHabitatConstant.BASE_OXYGEN_CONSUMPTION;
		this.currentOxygenVolumeAvailable = 0;
		updateOxygenConsumption(0);
	}
	
	public double updateOxygenConsumption(double oxygenConsumption) {
		this.currentOxygenVolumeAvailable += (this.baseOxygenConsumption
				+ oxygenConsumption);
		return initialOxygenVolumeAvailable - currentOxygenVolumeAvailable;
	}
	
	public double updateDioxideEmission(double dioxideEmission){
		this.currentCarbonDioxidEmission +=(this.baseOxygenConsumption
				+ dioxideEmission);
		return currentCarbonDioxidEmission;
	}
	
	public double getCurrentCarbonDioxideEmission(){
		return currentCarbonDioxidEmission;
	}
	
	public double getCurrentOxygenConsumption(){
		return initialOxygenVolumeAvailable - currentOxygenVolumeAvailable;
	}

	public double getBaseOxygenConsumption() {
		return baseOxygenConsumption;
	}

	public int getCurrentNumberOfAstronauts() {
		return currentNumberOfAstronauts;
	}

	public double getCurrentOxygenVolumeAvailable() {
		return currentOxygenVolumeAvailable;
	}

	public double getInitialOxygenVolumeAvailable() {
		return initialOxygenVolumeAvailable;
	}
}
