package helper;

import java.util.List;

import constant.MarsHabitatConstant;
import interactionClass.RequestResource;
import model.LunarHabitatAstronaut;
import model.MarsHabitatBase;
import objectClass.Resource;
import objectClass.ResourceType;

public class CaloriesMananger {
	
	private MarsHabitatBase luha = null;
	private double currentFood = MarsHabitatConstant.INITIAL_CALORIES;
	
	public CaloriesMananger(MarsHabitatBase luha){
		this.luha = luha;
	}
	
	public void calculateCalories(List<LunarHabitatAstronaut> astronauts){
		double currentDecrease = 0;
		for(int i=0;i<astronauts.size();i++){
			LunarHabitatAstronaut astronaut = astronauts.get(i);
			currentDecrease+=astronaut.getAmount_calories();
		}
		if(!(currentFood<MarsHabitatConstant.MINIMUM_CALORIES)){//If it is not necessary request food
			currentFood -= currentDecrease;
		}
		//System.out.println("Current Food Level: "+currentFood);
	}
	
	/**
	 * 
	 * @param request
	 * @return if the request is necessary.
	 */
	public boolean requestResource(RequestResource request){
		
		if(currentFood<MarsHabitatConstant.MINIMUM_CALORIES){//If it is necessary request Food			
			
			double requestQuantity = MarsHabitatConstant.INITIAL_CALORIES - currentFood;			
			System.out.println("Requesting Food: "+requestQuantity);			
			request.setResource(new Resource(requestQuantity, ResourceType.FOOD));
			return true;
		}
		return false;
	}
	
	/**
	 * Put food received.
	 * @param resource
	 */
	public void putResource(Resource resource){
		
		if(resource.getResourceType() == ResourceType.FOOD){
			
			currentFood += resource.getQuantity();
			System.out.println("Got Food!");
			System.out.println("Current Volume: "+currentFood);
		}
	}

}
