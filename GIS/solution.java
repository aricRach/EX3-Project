package GIS;

import java.util.ArrayList;

import Map.path;

public class solution {

	
	private ArrayList<path> pathCollection;
	
	public solution() {
		
		pathCollection=new ArrayList<path>();
	}
	
	public int getSize() {
		
		return pathCollection.size();
	}
	
	public void add(path p) {
		
		pathCollection.add(p);
	}
	
	public ArrayList<path> getPathCollection(){
		
		return pathCollection;
	}
	
	
}
