package helper;

public class CaloriesConsumption1 {

	
	private double currentCalories;		
	
	public CaloriesConsumption1() {
		currentCalories=0;
	}
	
	
	public double updateCalories(double Calories){
		this.currentCalories += Calories;
		return currentCalories;
	}


	public double getCurrentCalories() {
		return currentCalories;
	}


	public void setCurrentCalories(double currentCalories) {
		this.currentCalories = currentCalories;
	}
	
	
}
