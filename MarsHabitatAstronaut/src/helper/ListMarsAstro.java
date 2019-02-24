package helper;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import model.Astronaut;
import model.Position;

public class ListMarsAstro {

	//map where do control all instances LuHaAstro
		private Map<Integer, Astronaut> mapAstro;
		
		//map where is saved all list of position the LuHaAstro
		private Map<Integer,ArrayList<Position>> mapPosition;
		
		//map where do control if LuHaAstro its Walking or Idle
		private Map<Integer,Boolean> mapGoIn;
		
		public ListMarsAstro(){
			mapAstro = new TreeMap<Integer,Astronaut>();
			mapPosition = new TreeMap<Integer, ArrayList<Position>>();
			mapGoIn = new TreeMap<Integer, Boolean>();
		}
		
		public void addLuHaAstro(Astronaut l,int index){
			mapAstro.put(index,l);
			mapGoIn.put(index, false);
			mapPosition.put(index, new ArrayList<Position>());
		}
		
		public void addArrayList(ArrayList<Position> a , int index){
			mapPosition.put(index, a);
		}
		
		public Astronaut GetLuHaAstro(int index){
			return mapAstro.get(index);
		}
		
		public ArrayList<Position> GetArrayList(int index){
			return mapPosition.get(index);
		}
		
		public Boolean GetGoIn(int index){
			return mapGoIn.get(index);
		}
		
		public Map<Integer,Astronaut> getMapAstro() {
			return mapAstro;
		}
		
		public void setListAstro(Map<Integer,Astronaut> mapAstro) {
			this.mapAstro = mapAstro;
		}
		
		public Map<Integer, ArrayList<Position>> getMapPosition() {
			return mapPosition;
		}
		
		public void setMapPosition(Map<Integer, ArrayList<Position>> mapPosition) {
			this.mapPosition = mapPosition;
		}
		
		public Map<Integer, Boolean> getMapGoIn() {
			return mapGoIn;
		}
		
		public void setMapGoIn(Map<Integer, Boolean> mapGoIn) {
			this.mapGoIn = mapGoIn;
		}
		
		public void setGoIn(int index,Boolean flag) {
			mapGoIn.put(index,flag);
		}
		
		public void setArrayList(int index,ArrayList<Position> l){
			mapPosition.put(index, l);
		}
		
		public int size(){
			return mapAstro.size();
		}

		@Override
		public String toString() {
			return "ListMarsAstro [mapAstro=" + mapAstro + ", mapPosition=" + mapPosition + ", mapGoIn=" + mapGoIn
					+ "]";
		}
		
		
}
