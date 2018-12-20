package GIS;

import java.io.IOException;
import java.util.ArrayList;
import File_format.readCsv;
import GUI.guiGame;
import Geom.Point3D;
import Map.converts;
import algorithm.algo;

/**
 * This class represents game that contains Fruits and Packmans each of them has position and
 * meta data associated with them,
 * the game also include general score according to the Weight of the eaten fruits and the start time
 * @author Aric and Tal
 */

public class game {

	private static ArrayList<fruit> fruits;
	private static ArrayList<packman> packmans;
	private static double totalTime;
	private static double score;
	private static long startTimer;

	// Constructor //
	public game(ArrayList<fruit>f,ArrayList<packman>p) {
	
		// static access:
		game.fruits=f;
		game.packmans=p;
		game.totalTime=0;
		game.score=0;
		game.startTimer=System.currentTimeMillis();
	}
	
	/**
	 * this function create deep copy to ArrayList of packmans in this game
	 * @return new ArrayList 
	 */
	public static ArrayList<packman> copyPack(){
		
		ArrayList<packman> pp=new ArrayList<packman>();

		for(int i=0;i<packmans.size();i++) {
			metaDataPack tempData= new metaDataPack(packmans.get(i).getId(),
					packmans.get(i).getSpeed(), packmans.get(i).getRadius());
			Point3D postionTemp = new Point3D(packmans.get(i).getX(), packmans.get(i).getY(), packmans.get(i).getZ());
			packman temp = new packman(tempData, postionTemp);
			pp.add(temp);
		}
		return pp;
	}
	
	/**
	 * this function create deep copy to ArrayList of fruits in this game
	 * @return new ArrayList 
	 */
	public static ArrayList<fruit> copyFruit(){
		
		ArrayList<fruit> ff=new ArrayList<fruit>();

		for(int i=0;i<fruits.size();i++) {
			metaDataFruit tempData= new metaDataFruit(fruits.get(i).getId(), fruits.get(i).getWeight());
			Point3D postionTemp = new Point3D(fruits.get(i).getX(),fruits.get(i).getY(),fruits.get(i).getZ());
			fruit temp = new fruit(tempData, postionTemp);
			ff.add(temp);
		}
		return ff;
	}

	/**
	 * This function initialize the game data, read from csv file and then create
	 * ArrayList of fruits and packmans that store all the data
	 * @param path the csv input file
	 * @throws IOException
	 */
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

	/**
	 * this function call for open Gui window with packmans and fruits ArrayLists
	 * @throws IOException
	 */
	public void paintGame() throws IOException {

		guiGame demo = new guiGame(); // create current Gui 

		// create fruit and packmans collection with Pixel coordinates
		// in order to send the collections to createGuiAndShow
		
		ArrayList<fruit> fruitPix=converts.Coords2PixelFruit(fruits);
		ArrayList<packman> packPix=converts.Coords2PixelPack(packmans);

		demo.openFileGUI(fruitPix,packPix);
	}

	// Getters and Setters //

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
	
	public static void main(String[] args) throws IOException { 

		ArrayList<packman> pack=new ArrayList<packman>();
		ArrayList<fruit> fruits=new ArrayList<fruit>();

		game g=new game(fruits,pack);
		String path = "D:\\מדעי המחשב\\שנה ב\\סמסטר א\\מונחה עצמים\\myMath Project\\Ex 3\\Ex3_data\\game_1543684662657.csv";

		game.createGameCollection(path);
		
		algo.calcAll(fruits, pack);
		
		for(int i=0;i<pack.size();i++) {
			
			System.out.println("the score of packman number: "+pack.get(i).getId()+" is:"
					+ " "+pack.get(i).getPoints()+" the time it takes is: "+pack.get(i).getTime());
		}
		
		System.out.println("the Total time is: "+game.totalTime);
		System.out.println("the game score is: "+game.score);
		
	}
}