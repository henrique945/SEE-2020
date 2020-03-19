package helper;

import java.util.List;

import constant.MarsHabitatConstant;
import interactionClass.RequestResource;
import interactionClass.SendResource;
import model.LunarHabitatAstronaut;
import model.MarsHabitatBase;
import objectClass.Resource;
import objectClass.ResourceType;

public class CarbonDioxideMananger {
	
	private MarsHabitatBase luha;
	private double currentVolume = MarsHabitatConstant.CARBON_DIOXIDE_VOLUME_INITIAL;
	
	public CarbonDioxideMananger(MarsHabitatBase luha){
		this.luha = luha;
		
	}
	
	public void calculateCarbonDioxide(List<LunarHabitatAstronaut> astronauts){
		double currentIncrease = 0;
		for(int i=0;i<astronauts.size();i++){
			LunarHabitatAstronaut astronaut = astronauts.get(i);
			currentIncrease+=astronaut.getAmount_carbon_dioxide();
		}
		if(!(currentVolume<MarsHabitatConstant.CARBON_DIOXIDE_MAXIMUM_VOLUME)){
			currentVolume += currentIncrease;
		}		
	}
	
	/**
	 * Update sendResource with carbon dioxide if necessary.
	 * @param request
	 * @param sendReosource
	 * @return if was a request of carbon dioxide.
	 */
	public boolean sendResource(RequestResource request,SendResource sendReosource){
		
		if(request.getResource().getResourceType() == ResourceType.CARBON_DIOXIDE){
			
			double requestQuantity = request.getResource().getQuantity();
			double avaibleVolume = currentVolume-MarsHabitatConstant.CARBON_DIOXIDE_VOLUME_INITIAL;
			double sendQuantity = (avaibleVolume<requestQuantity?avaibleVolume:requestQuantity);			
			System.out.println("Sending carbon dioxide: "+sendQuantity);			
			sendReosource.setResource(new Resource(sendQuantity, ResourceType.CARBON_DIOXIDE));
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
