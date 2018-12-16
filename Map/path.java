package Map;

import java.util.ArrayList;

import Geom.Point3D;

public class path {

	ArrayList<Point3D> path; // each point3D has (lat,lon,time!!) change it
	
	
	public path() {
		
		path=new ArrayList<Point3D>();
	}
	
	public void add(Point3D p) {
		
		path.add(p);
		
	}
	
	public ArrayList<Point3D> getPath(){
		
		return this.path;
	}
	
	public Point3D getPathI(int i) {
		
		return path.get(i);
		
	}
	
	public String toString() {
		
		int i=0;
		String s="";
		while(i<path.size()) {
			//", Time Associated: "+path.get(i).getTimeStamp()
			s+=" "+i+")";
			s+=path.get(i).x()+", "+path.get(i).y()+", "+path.get(i).z()+", "+path.get(i).getTimeStamp()+"\n";
			i++;
		}
		return s;
	}
}
