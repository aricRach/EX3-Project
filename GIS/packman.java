package GIS;

import Geom.Point3D;
import Map.path;

public class packman {

	private Point3D position;
	private metaDataPack data;
	private path track; // all the fruis postions
	
	
	public packman(metaDataPack data,Point3D position) {
	
		this.data=data;
		this.position=position;
		this.track=new path();
		initializePath(); // initial path with packman location
	}
	
	
	public void initializePath() {
		
		Point3D startPoint=getPosotion();
		track.getPath().add(startPoint);
	}

	public Point3D getPosotion() {

		return position;
	}
	
	public path getPath() {
		
		return track;
	}
	
	public double getSpeed() {
		
		return data.getSpeed();
	}
	public double getRadius() {
		
		return data.getRadius();
	}
	
	public void setPosition(Point3D p) {

		position.set_x(p.x());
		position.set_y(p.y());
		position.set_z(p.z());
	}

	public String getId() {
		return data.getId();
	}

	public void setId(String id) {
		data.setId(id);
	}

	public double getX() {
		return position.get_x();
	}

	public void setX(double x) {
		position.set_x(x);
	}

	public double getY() {
		return position.get_y();
	}

	public void setY(double y) {
		position.set_y(y);
	}

	public double getZ() {
		return position.get_z();
	}

	public void setZ(double z) {
		position.set_z(z);
	}

	public void setSpeed(double speed) {
		data.setSpeed(speed);	
	}

	public void setRadius(double radius) {
		data.setRadius(radius);	
	}
	
	public double getPoints() {
		return data.getScore();
	}

	public void setPoints(double points) {
		data.setScore(points);
	}
	public path getTrack() {
		return track;
	}

	public void setTrack(path track) {
		this.track = track;
	}
	public void addToTrack(Point3D postion) {// add the postion the to path
		
		track.getPath().add(postion);
	}
	

	public double getTime() {
		return data.getTime();
	}



	public void setTime(double time) {
		data.setTime(time);
	}

	@Override
	public String toString() {
		return "packman [position=" + position + ", data=" + data +"]";
	}

}