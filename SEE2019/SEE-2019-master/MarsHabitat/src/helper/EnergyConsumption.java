package helper;

public class EnergyConsumption {

	///That class represents the energy consumption inside the luha
	
	private double currentEnergyConsumption; 			/// the current energy consumption 
	private double baseEnergyConsumption; 				/// the energy consumption without astrounauts
	private double energyConsumptionPerAstronaut;		/// the quantity in kW of consumption per astrounaut 
	private int currentNumberOfAstronauts;				/// current number of astrounauts in luha
	
	public EnergyConsumption(double baseEnergyConsumption, double energyConsumptionPerAstronaut) {

		this.baseEnergyConsumption = baseEnergyConsumption;
		this.energyConsumptionPerAstronaut = energyConsumptionPerAstronaut;
		this.currentNumberOfAstronauts = 0;
		updateEnergyConsumption(this.currentNumberOfAstronauts);

	}
	
	public double getBaseEnergyConsumption() {
		return this.baseEnergyConsumption;
	}

	public double getCurrentEnergyConsumption() {
		return this.currentEnergyConsumption;
	}

	public double getEnergyConsumptionPerAstronaut() {
		return this.energyConsumptionPerAstronaut;
	}

	public double getCurrentNumberOfAstronauts() {
		return this.currentNumberOfAstronauts;
	}

	public double updateEnergyConsumption(int numberOfAstronauts) {

		this.currentNumberOfAstronauts = numberOfAstronauts;
		this.currentEnergyConsumption = this.baseEnergyConsumption
				+ this.energyConsumptionPerAstronaut * numberOfAstronauts;
		return this.currentEnergyConsumption;

	}
}
