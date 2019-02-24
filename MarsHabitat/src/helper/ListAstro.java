package helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import model.LunarHabitatAstronaut;

public class ListAstro {
	private ConcurrentHashMap<String,LunarHabitatAstronaut> list = null;
	
	/// This class is for:
	/// Add astronaut, remove astronaut, update astronaut and his health care. 
	
	public ListAstro(){
		list = new ConcurrentHashMap<String, LunarHabitatAstronaut>();
	}
	public void addAstro(String name,LunarHabitatAstronaut astro){
		list.put(name, astro);
	}
	public void addAstro(LunarHabitatAstronaut astro){
		list.put(astro.getName(), astro);
	}
	public void attAstro(LunarHabitatAstronaut astro){
		list.remove(astro.getName());
		addAstro(astro);
	}
	public boolean contains(LunarHabitatAstronaut astro){
		return list.containsKey(astro.getName());
	}
	public double amountOxygen(){
		double total=0;
		Iterator<Map.Entry<String, LunarHabitatAstronaut> > it = list.entrySet().iterator();
		while(it.hasNext()){
			LunarHabitatAstronaut l = it.next().getValue();
			if(l.getName()!=null)
				total+=l.getAmount_oxygen();
		}
//		for(LunarHabitatAstronaut l : list.values()){
//			if(l.getName()!=null)
//				total+=l.getAmount_oxygen();
//		}
		return total;
	}
	public double amountCarbonDioxid(){
		double total=0;
		
		Iterator<Map.Entry<String, LunarHabitatAstronaut> > it = list.entrySet().iterator();
		while(it.hasNext()){
			LunarHabitatAstronaut l = it.next().getValue();
			if(l.getName()!=null)
				total+=l.getAmount_carbon_dioxide();
		}
//		for(LunarHabitatAstronaut l : list.values()){
//			if(l.getName()!=null)
//				total+=l.getAmount_carbon_dioxide();
//		}
		return total;
	}
	public double amountCalories(){
		double total=0;
		Iterator<Map.Entry<String, LunarHabitatAstronaut> > it = list.entrySet().iterator();
		while(it.hasNext()){
			LunarHabitatAstronaut l = it.next().getValue();
			if(l.getName()!=null)
				total+=l.getAmount_calories();
		}
//		for(LunarHabitatAstronaut l : list.values()){
//			if(l.getName()!=null)
//				total+=l.getAmount_calories();
//		}
		return total;
	}
	
	public List<LunarHabitatAstronaut> getAstronauts(){
		
		return Collections.unmodifiableList(new ArrayList(list.values()));
	}
	
}
