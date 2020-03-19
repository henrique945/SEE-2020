package helper;

public class EnergyConsumption {

	
	private double currentEnergyConsumption;		//current energy consumption
	private double factoryEnergyConsumption;		//energy when factory is not working
	private double energyConsumption;				//consumption in kW
	private double current; 					    //current time
	
	public EnergyConsumption(double factoryEnergyConsumption, double energyConsumption) {
		this.factoryEnergyConsumption = factoryEnergyConsumption;
		this.energyConsumption = energyConsumption;
		this.current = 0; 								//begin
		updateEnergyConsumption(this.current);
	}
	
	public double getFactoryEnergyConsumption() {
		return this.factoryEnergyConsumption;
	}

	public double getCurrentEnergyConsumption() {
		return this.currentEnergyConsumption;
	}

	public double getEnergyConsumption() {
		return this.energyConsumption;
	}
	
	public double getCurrent() {
		return this.current;
	}
	public double updateEnergyConsumption(double systemTime) {
		this.currentEnergyConsumption = this.factoryEnergyConsumption + this.energyConsumption * systemTime;
		return this.currentEnergyConsumption;
	}
	
}
