package GIS;


public class metaDataPack { // for not creating objects of 

	private String id;
	private double speed; // Speed of Packman
	private double radius; // radius
	private double score; // Score of fruits
	private double time;// agg time to eat fruit
	
	
	public metaDataPack(String id,double speed,double radius) {
		this.id=id;
		this.speed=speed;
		this.radius=radius;
		this.time=0;
		this.score=0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "metaDataPack [id=" + id + ", speed=" + speed + ", radius=" + radius + ", score=" + score + ", time="
				+ time + "]";
	}
	
	
	
	
}
