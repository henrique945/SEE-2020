package helper;

import java.util.List;

import constant.MarsHabitatConstant;
import interactionClass.RequestResource;
import model.LunarHabitatAstronaut;
import model.MarsHabitatBase;
import objectClass.Resource;
import objectClass.ResourceType;

public class OxygenMananger {
	
	private MarsHabitatBase luha;
	private double currentVolume = MarsHabitatConstant.OXYGEN_VOLUME_INITIAL;
	
	public OxygenMananger(MarsHabitatBase luha){
		this.luha = luha;
		
	}
	
	public void calculateOxygen(List<LunarHabitatAstronaut> astronauts){
		double currentDecrease = 0;
		for(int i=0;i<astronauts.size();i++){
			LunarHabitatAstronaut astronaut = astronauts.get(i);
			currentDecrease+=astronaut.getAmount_oxygen();
		}
		if(!(currentVolume<MarsHabitatConstant.OXYGEN_MINIMUM_VOLUME)){//If it is not necessary request oxygen
			currentVolume -= currentDecrease;
		}
		//System.out.println("Current Oxygen Level: "+currentVolume);
	}
	
	/**
	 * 
	 * @param request
	 * @return if the request is necessary.
	 */
	public boolean requestResource(RequestResource request){
		
		if(currentVolume<MarsHabitatConstant.OXYGEN_MINIMUM_VOLUME){//If it is necessary request oxygen			
			
			double requestQuantity = MarsHabitatConstant.OXYGEN_VOLUME_INITIAL - currentVolume;			
			System.out.println("Requesting oxygen: "+requestQuantity);			
			request.setResource(new Resource(requestQuantity, ResourceType.OXYGEN));
			return true;
		}
		return false;
	}
	
	/**
	 * Put oxygen received.
	 * @param resource
	 */
	public void putResource(Resource resource){
		
		if(resource.getResourceType() == ResourceType.OXYGEN){
			
			currentVolume += resource.getQuantity();
			System.out.println("Got Oxygen!");
			System.out.println("Current Volume: "+currentVolume);
		}
	}

}
