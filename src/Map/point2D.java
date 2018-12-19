package Map;

// כרגע לא בשימוש!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
public class point2D {

	private double lat;
	private double lon;
	private long time;

	public point2D(double lat,double lon) {

		this.lat=lat;
		this.lon=lon;
		this.time=0;
	}

	public point2D(double lat,double lon,long time) {

		this.lat=lat;
		this.lon=lon;
		this.time=time;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	


}
