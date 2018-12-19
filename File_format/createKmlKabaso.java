package File_format;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Coords.MyCoords;
import GIS.fruit;
import GIS.game;
import GIS.packman;
import GIS.solution;
import Geom.Point3D;
import algorithm.algo;

public class createKmlKabaso {
	
	private static long startTimer;
	private static int x=1;
	private final BufferedWriter writer;

	public createKmlKabaso(String output) throws IOException {

		writer = new BufferedWriter(new FileWriter(output));
		this.startTimer=System.currentTimeMillis();
	}
	
	//str , id,speed,radius,time,lat,lon,alt,weight
	public void writeIcon(String str,String id,double speed,double radius,
						  String time,double weight,double latitude,
						  double longitude,double alt) throws IOException {
		if(str=="P") {
			writer.write("<Placemark>\n" + 
					"<name><![CDATA["+"Packman" +id+"]]></name>\n" +
					"<description><![CDATA[Speed: <b>"+speed+"</b><br/>Radius: <b>"
					+radius+"</b><br/>Date: <b>" // Timestamp: <b>"+Timestamp+"</b><br/>
					+time+"</b>]]></description><styleUrl>"+"pack"+"</styleUrl>\n" + 
					"<Point>\n" + 
					"<coordinates>"+latitude+","+longitude+","+alt+"</coordinates></Point>\n" + 
					"</Placemark>\n" + 
					"");
		}
		
		else if(str=="F") {
			time = time.replace(" ", "T");
			time+="Z";
			String timeStart = timestampToDate(startTimer);
			
			timeStart = timeStart.replace(" ", "T");
			timeStart+="Z";
			
			writer.write("<Placemark>\n" + "<TimeSpan><begin>"+timeStart+"</begin>"+"<end>"+time+"</end></TimeSpan>"+
					"<name><![CDATA["+"Fruit "+id+"]]></name>\n" +
					"<description><![CDATA[Weight: <b>"+weight+"</b><br/>]]></description><styleUrl>"+"fruit"+"</styleUrl>\n" + 
					"<Point>\n" + 
					"<coordinates>"+latitude+","+longitude+","+alt+"</coordinates></Point>\n" + 
					"</Placemark>\n" + 
					"");
		}
		else { // for Path
			
			time = time.replace(" ", "T");
			time+="Z";
			
			writer.write("<Placemark>\n" + 
					"<name><![CDATA["+"Path" +id+"]]></name>\n" + "<TimeStamp><when>" + time + "</when></TimeStamp>" +
					"<description><![CDATA[Speed: <b>"+speed+"</b><br/>Radius: <b>"
					+radius+"</b><br/>Date: <b>" 
					+time+"</b>]]></description><styleUrl>"+"pack"+"</styleUrl>\n" + 
					"<Point>\n" + 
					"<coordinates>"+latitude+","+longitude+","+alt+"</coordinates></Point>\n" + 
					"</Placemark>\n" + 
					"");
		}
	}
	private void writeStart() throws IOException {

		writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		writer.write("<kml xmlns=\"http://www.opengis.net/kml/2.2\">");
		writer.write("<Document>\n");
		writer.write("<Style id=\"fruit\"><IconStyle><Icon><href>http://www.interload.co.il/upload/2414752.png</href></Icon></IconStyle></Style>\r\n" + 
				"\r\n" + 
				"<Style id=\"pack\"><IconStyle><Icon><href>http://www.interload.co.il/upload/4532827.png</href></Icon></IconStyle></Style>\r\n");
	}
	
	private void writeEnd() throws IOException {
		writer.write("</Document>\n</kml>\n");
	}
	
	public static String timestampToDate(long timeStamp) {
		
		String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date (timeStamp));
		return date;
	}
	
	public static void run(ArrayList<fruit> ff,ArrayList<packman> pp,solution s,String save,ArrayList<packman> pEaten) throws IOException {
		MyCoords m = new MyCoords();
		if (pp.size()==0 && ff.size()==0) {
			
			throw new RuntimeException("the game is empty please enter elements or open file");
		}
//		ArrayList<packman> pp=new ArrayList<packman>();
//		ArrayList<fruit> ff=new ArrayList<fruit>();
		createKmlKabaso k =new createKmlKabaso(save);
//		game g=new game(ff,pp);
//		game.createGameCollection("C:\\Users\\aric\\Desktop\\לימודים\\מונחה עצמים\\Ex 3\\Ex3_data\\game_1543684662657.csv"); 
//		
		k.writeStart();
		
		for(int i=0; i<pp.size(); i++) {
			String id = pp.get(i).getId();
			double speed = pp.get(i).getSpeed();
			double radius=pp.get(i).getRadius();
			double lat = pp.get(i).getPosition().x();
			double lon = pp.get(i).getPosition().y();
			double alt = pp.get(i).getPosition().z();
			
			Point3D start = pp.get(i).getPosition(); // Start
			long currentTime = startTimer;
			for(int j=0; j<pEaten.get(i).getEatenFruits().size();j++) {
				
				fruit f = pEaten.get(i).getEatenFruits().get(j); // first Fruit
				double dist = m.distance3d(start, f.getPosition())-pEaten.get(i).getRadius();
				long timeStamp = (long) (dist/pEaten.get(i).getSpeed());
				currentTime+=timeStamp*1000;
				pEaten.get(i).getEatenFruits().get(j).getPosition().setTimeStamp(currentTime); //maybe add one sec
				
				 id = pEaten.get(i).getEatenFruits().get(j).getId();
				double weight = pEaten.get(i).getEatenFruits().get(j).getWeight();
				double lat1 = pEaten.get(i).getEatenFruits().get(j).getX();
				double lon1 = pEaten.get(i).getEatenFruits().get(j).getY();
				double alt1 = pEaten.get(i).getEatenFruits().get(j).getZ();
				 String time = timestampToDate(pEaten.get(i).getEatenFruits().get(j).getPosition().getTimeStamp());
				 k.writeIcon("F",id,0,0,time,weight,lon1,lat1,alt1);
				 
				start = f.getPosition();
			}
			
			k.writeIcon("P",id,speed,radius,"0",0,lon,lat,alt);
		}
		for(int i=0; i<ff.size(); i++) {
//			String id = ff.get(i).getId();
//			double weight = ff.get(i).getWeight();
//			double lat = ff.get(i).getX();
//			double lon = ff.get(i).getY();
//			double alt = ff.get(i).getZ();
			
//			k.writeIcon("F",id,0,0,"0",weight,lon,lat,alt);
		}
		
		//algo.calcAll(ff, pp);// start the game to create the paths
		for(int i=0; i<s.getPathCollection().size();i++) {
			
			int t=x;
			
			for(int j=1; j<s.getPathCollection().get(i).getPath().size();j++) {
			
				// set timeStamp by temp.setTime((long) (game.getStartTimer()+j*1000));
				s.getPathCollection().get(i).getPath().get(j).setTimeStamp(createKmlKabaso.getStartTimer()+(t++)*1000);
				//pp.get(i).getPath().getPathI(j).setTimeStamp(createKmlKabaso.getStartTimer()+(t++)*1000);
				 double lat=s.getPathCollection().get(i).getPathI(j).x();
				 double lon=s.getPathCollection().get(i).getPathI(j).y();
				 double alt=s.getPathCollection().get(i).getPathI(j).z();
				 		 
//				double lat = pp.get(i).getPath().getPathI(j).x();
//				double lon = pp.get(i).getPath().getPathI(j).y();
//				double alt = pp.get(i).getPath().getPathI(j).z();
				String time = timestampToDate(s.getPathCollection().get(i).getPathI(j).getTimeStamp());
				k.writeIcon("N",""+pp.get(i).getId(),0,0,time,0,lon,lat,alt);
			}
			x++;
		}
		k.writeEnd();
		k.writer.flush();
	}

	
	public static long getStartTimer() {
		return startTimer;
	}


	public static void setStartTimer(long startTimer) {
		createKmlKabaso.startTimer = startTimer;
	}
	
	public static void main(String[] args) throws IOException {
		
		//optional to create kml file from this class
		
		ArrayList<packman> pp=new ArrayList<packman>();
		ArrayList<fruit> ff=new ArrayList<fruit>();
		
		String name = "enter the file name here"+".kml";
		createKmlKabaso k =new createKmlKabaso(name);
		game g=new game(ff,pp);
		String pathFile = "C:\\Users\\aric\\Desktop\\לימודים\\מונחה עצמים\\Ex 3\\Ex3_data\\game_1543684662657.csv";
		game.createGameCollection(pathFile); 
		ArrayList<fruit> copyFruit=game.copyFruit();//copy beacuse the fruits delete in calc all function
		ArrayList<packman> copyPack=game.copyPack();//copy beacuse the fruits delete in calc all function
		
		solution s = algo.calcAll(ff, pp);
		
		createKmlKabaso.run(copyFruit,copyPack,s,name,pp);
	}
		
}
