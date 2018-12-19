package GIS;

public class metaDataFruit {

	private String id; // id of Fruit
	private double weight; // Weight of fruit 
	
	public metaDataFruit(String id,double weight) {
		
		this.id=id;
		this.weight=weight;
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	@Override
	public String toString() {
		return "metaDataFruit [id=" + id + ", weight=" + weight + "]";
	}
	
	
	
}
