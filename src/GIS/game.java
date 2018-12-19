package GIS;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.text.Position;

import Coords.MyCoords;
import File_format.readCsv;
import GUI.guiGame;
import Geom.Point3D;
import Map.converts;
import algorithm.algo;

public class game {

	private static ArrayList<fruit> fruits;
	private static ArrayList<packman> packmans;
	private static double totalTime;
	private static double score;
	private static long startTimer;


	public game(ArrayList<fruit>f,ArrayList<packman>p) {
		//static access
		game.fruits=f;
		game.packmans=p;
		game.totalTime=0;
		game.score=0;
		game.startTimer=System.currentTimeMillis();
	}

	/**
	 * This function initialize the game data, its read from csv file and then create
	 * Arraylist of fruits and packmans that store all the data
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
	
	public static ArrayList<packman> copyPack(){
		
		ArrayList<packman> pp=new ArrayList<packman>();

		for(int i=0;i<packmans.size();i++) {
			metaDataPack tempData= new metaDataPack(packmans.get(i).getId(), packmans.get(i).getSpeed(), packmans.get(i).getRadius());
			Point3D postionTemp = new Point3D(packmans.get(i).getX(), packmans.get(i).getY(), packmans.get(i).getZ());
			packman temp = new packman(tempData, postionTemp);
			pp.add(temp);
		}
		return pp;
	}
	
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
	public static void main(String[] args) throws IOException { 

		ArrayList<packman> pack=new ArrayList<packman>();
		ArrayList<fruit> fruits=new ArrayList<fruit>();

		game g=new game(fruits,pack);

		game.createGameCollection("C:\\Users\\aric\\Desktop\\מד"
				+ "עי המחשב\\שנה ב\\תכנות מונחה עצמים\\מטלה 3\\מטלה 3 pdf\\Ex3\\Ex3_data\\data\\game_1543693911932_a.csv");
		
		//solution s=al.calcAll(fCoords, pCoords);
		algo.calcAll(fruits, pack);
		/*solution answer2=
		for(int i=0;i<answer2.getPathCollection().size();i++) {
			System.out.println("path of packman number " +pack.get(i).getId()+":");
			System.out.println(answer2.getPathCollection().get(i));
		}*/
		
		for(int i=0;i<pack.size();i++) {
			
			System.out.println("the score of packman number: "+pack.get(i).getId()+" is:"
					+ " "+pack.get(i).getPoints()+" the time it takes is: "+pack.get(i).getTime());
		}
		
						
		System.out.println(game.totalTime);
		System.out.println(game.score);
		
    	//g.paintGame(); //if we want to start this fucniton we need to init and show gui
	}
}