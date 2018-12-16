package GIS;

import java.io.IOException;
import java.util.ArrayList;

import File_format.readCsv;
import GUI.guiGame;
import Geom.Point3D;
import Map.converts;

public class game {

	private static ArrayList<fruit> fruits;
	private static ArrayList<packman> packmans;
	private static double totalTime;
	private static double score;
	private static long startTimer;

	
	public game(ArrayList<fruit>f,ArrayList<packman>p) {

		this.fruits=f;
		this.packmans=p;
		this.totalTime=0;
		this.score=0;
		this.startTimer=System.currentTimeMillis();
	}


	public static void createGameCollection(String path) throws IOException{

		System.out.println("create game");
		readCsv csv=new readCsv(path); // this path is the input csv file
		ArrayList<String> arr=csv.readCsvGame(); // create arrayList of all the lines as a string

		for(int i=0;i<arr.size();i++) {

			String [] parsed=arr.get(i).split(",");
			String id=parsed[1];
			double x=Double.parseDouble(parsed[2]);
			double y=Double.parseDouble(parsed[3]);
			double z=Double.parseDouble(parsed[4]);
			double speedOrWeight=Double.parseDouble(parsed[5]);

			if (parsed[0].charAt(0)=='P') {

				double radius=Double.parseDouble(parsed[6]);
				metaDataPack data=new metaDataPack(id,speedOrWeight,radius);
				Point3D position=new Point3D(x,y,z);
				packman p=new packman(data,position);
				packmans.add(p);
			}
			else {
				metaDataFruit data=new metaDataFruit(id,speedOrWeight);
				Point3D position=new Point3D(x,y,z);
				fruit f=new fruit(data,position);
				fruits.add(f);
			}
		}
	}
	
	
	//getters and setters
	
	public static double getTotalTime() {
		return totalTime;
	}

	public static void setTotalTime(double totalTime) {
		game.totalTime = totalTime;
	}

	public static double getScore() {
		return score;
	}

	public static void setScore(double score) {
		game.score = score;
	}


	public static long getStartTimer() {
		return startTimer;
	}


	public static void setStartTimer(long startTimer) {
		game.startTimer = startTimer;
	}


	/**
	 * this function get collection of fruits and sole packman and calculate the 
	 * the fruit the packman should eat(the closest fruit)
	 * @param fruits
	 * @param pack
	 * @return
	 *//*
	public fruit findClosest(ArrayList<fruit> fruits,packman pack) {

		//!!!!!!!!!!!
		// use getTime but we changed this fucntion
		double minTime=0; // initialize
		int currentIndex=0; //initialize
		if (!fruits.isEmpty()) {

			minTime=getTime(pack,fruits.get(0)); //get fruit and pack and get the time to eat
			currentIndex=0;
		}else {

			return null; // the fruits collection is empty!
		}

		int i=1;
		while(i<fruits.size()) {

			double temp=getTime(pack,fruits.get(i));
			if (temp<minTime) {

				minTime=temp;
				currentIndex=i;
			}
			i++;
		}
		return fruits.get(currentIndex);		

	}
	public ArrayList<fruit> copy() {

		ArrayList<fruit> copy=new ArrayList<fruit>();
		int i=0;
		while(i<fruits.size()) {

			copy.add(fruits.get(i));
			i++;
		}
		return copy;	
	}


	public path calc1(ArrayList<fruit> fruits,packman pack) {

		MyCoords m= new MyCoords(); 

		//***copy the arrayList in order not to delete the fruits from the original arrayList
		ArrayList<fruit> copy=new ArrayList<fruit>();
		int i=0;
		while(i<fruits.size()) {

			copy.add(fruits.get(i));
			i++;
		}
		//***
		path answer=new path();//bulid path 
		answer.getPath().add(pack.getPosotion());// add the postion of the packman to the path
		while(!copy.isEmpty()) {
			fruit f=findClosest(copy,pack);
			totalTime+=getTime(pack,f);
			answer.getPath().add(f.getPosition());// add the postion of the fruit to the path
			pack.setPosition(f.getPosition());
			score+=f.getWeight();
			copy.remove(f);
		}

		return answer;
	}
	//**


	//tests
	public static void main(String[] args) throws IOException { 

		MyCoords m= new MyCoords(); 

		ArrayList<packman> pack=new ArrayList<packman>();
		ArrayList<fruit> fruits=new ArrayList<fruit>();

		game g=new game(fruits,pack);

		g.createGameCollection("C:\\Users\\aric\\Desktop\\מדעי המחשב\\שנה ב\\תכנות מונחה עצמים\\מטלה 3\\מטלה 3 pdf\\Ex3\\Ex3_data\\data\\game_1543684662657.csv");
		/*		System.out.println(fruits.size());
		System.out.println(pack.size());


		System.out.println("packman 1 "+pack.get(0));
		System.out.println("fruit 1 "+ fruits.get(0));
		System.out.println("the distance is: "+m.distance3d(pack.get(0).getPosotion(), fruits.get(0).getPosition()));
		System.out.println("the time it will take to eat "+g.getTime(pack.get(0),fruits.get(0)));


		//		fruit toEat=g.findClosest(fruits,pack.get(0));
		//		System.out.println(toEat);

		path answer=g.calc1(fruits,pack.get(0));
		System.out.println(answer);

		System.out.println(g.totalTime);
		System.out.println(g.score);
		 */
		
		/*solution answer2=g.calcAll(fruits, pack);
		for(int i=0;i<answer2.getPathCollection().size();i++) {
			System.out.println("path of packman number " +pack.get(i).getId()+":");
			System.out.println(answer2.getPathCollection().get(i));
		}
		
		for(int i=0;i<pack.size();i++) {
			
			System.out.println("the score of packman number: "+pack.get(i).getId()+" is:"
					+ " "+pack.get(i).getPoints()+" the time it takes is: "+pack.get(i).getTime());
		}
		
		
				Point3D old_fruit=new Point3D( 32.105468, 35.206183,0);
				Point3D packman=new Point3D( 32.103989, 35.208964,0);
				//218.15
				g.changeCoordsAcordnigToRadius(packman,old_fruit, 275.68);
		//				//valid is : from fruit to packman , radius from fruit to packman
				
		System.out.println(g.totalTime);
		System.out.println(g.score);
		
    	//g.paintGame(); //if we want to start this fucniton we need to init and show gui
	}*/
	
	
	public void paintGame() throws IOException {
		
		guiGame demo = new guiGame();
		converts c=new converts();

		// create fruit and packmans collection with PIXEL coords
		// in order to send the collections to createGuiAndShow
		ArrayList<fruit> fruitPix=c.Coords2PixelFruit(fruits);
		
		ArrayList<packman> packPix=c.Coords2PixelPack(packmans);
		
		
		//now i will send to createAndShowGUI2 the colections with the pixel coordinates
		demo.createAndShowGUI2(fruitPix,packPix);
		
	}

}
