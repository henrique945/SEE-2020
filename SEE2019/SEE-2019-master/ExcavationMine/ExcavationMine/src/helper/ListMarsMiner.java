package helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import model.MarsMiner;

public class ListMarsMiner {
	private ConcurrentHashMap<String,MarsMiner> list = null;
	
	public ListMarsMiner(){
		list = new ConcurrentHashMap<String, MarsMiner>();
	}
	public void addMarsMiner(String name,MarsMiner marsMiner){
		list.put(name, marsMiner);
	}
	public void addMarsMiner(MarsMiner marsMiner){
		list.put(marsMiner.getName(), marsMiner);
	}
	public void attMarsMiner(MarsMiner marsMiner){
		list.remove(marsMiner.getName());
		addMarsMiner(marsMiner);
	}
	public boolean contains(MarsMiner marsMiner){
		return list.containsKey(marsMiner.getName());
	}
	public List<MarsMiner> getMarsMiners(){
		
		return Collections.unmodifiableList(new ArrayList<MarsMiner>(list.values()));
	}
	
}
