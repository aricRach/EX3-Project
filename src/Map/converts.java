package Map;

import java.io.IOException;
import java.util.ArrayList;
import Coords.MyCoords;
import GIS.fruit;
import GIS.metaDataFruit;
import GIS.metaDataPack;
import GIS.packman;
import GIS.solution;
import Geom.Point3D;

/**
 * this class contains all converts from pixels to Coordinates and vice versa 
 * @author Tal and Aric
 */

public class converts {

	static map m ;
	
	// Constructor //
	public converts() throws IOException {

		m= new map();
	}

	/**
	 * this function convert Pixels to Coordinates
	 * @param x pixel on X-axis
	 * @param y pixel on Y-axis
	 * @return Point3D after convert to [lat,lon,alt]
	 */
	public static Point3D pixel2Coords(double x,double y) {

		double dx=x/m.width;
		double dy=y/m.height;

		double totalLon=m.end.y()-m.start.y();
		double totalLat=m.end.x()-m.start.x();

		double goLon=totalLon*dx;
		double goLat=totalLat*dy;

		double answer1=m.start.y()+goLon;
		double answer2=m.start.x()+goLat;

		return new Point3D(answer2,answer1,0);
	}
	
	/**
	 * this function convert Coordinates to Pixels
	 * @param x coordinate value
	 * @param y coordinate value
	 * @return Point3D after convert to [lat,lon,alt]
	 */
	public static Point3D coords2Pixel(double x,double y) {

		double distLat = m.start.x()-m.end.x();
		double distLon = m.end.y()-m.start.y();
		double dx=(m.start.x()-x)/(distLat);
		double dy=(y-m.start.y())/(distLon);

		double goY=(m.height*dx);
		double goX=(m.width*dy);

		int answer1=(int)(goY);
		int answer2=(int)(goX);

		return new Point3D(answer2,answer1,0);	
	}
		
	/**
	 * this function calculate the distance between two pixels
	 * @param x pixel on X-axis
	 * @param y pixel on Y-axis
	 * @return the distance
	 */
	public static double distanceBet2Pixels(pix a,pix b) {

		Point3D p1=pixel2Coords(a.x(),a.y());
		Point3D p2=pixel2Coords(b.x(),b.y());

		MyCoords m =new MyCoords();
		double distance=m.distance3d(p1, p2);

		return distance;
	}
	
	/**
	 * this function calculate the angle/azimuth between two pixels
	 * @param x pixel on X-axis
	 * @param y pixel on Y-axis
	 * @return the angle
	 */
	public static double angleBet2Pixels(pix a,pix b) {

		Point3D p1=pixel2Coords(a.x(),a.y());
		Point3D p2=pixel2Coords(b.x(),b.y());

		MyCoords m =new MyCoords();
		double angle=m.azimuth(p1, p2);

		return angle;
	}

	/**
	 * this function scan all the elements in the current ArrayList and convert all the packmans data 
	 * from Coordinates to Pixels
	 * @param pp ArrayList that contains packmans
	 * @return new arrayList of packmans with data in Pixels
	 */
	public static ArrayList<packman> Coords2PixelPack(ArrayList<packman> pp ) {

		int packSize=pp.size();
		int pIndex=0;
		// create fruit and packmans collection with PIXEL coords
		// in order to send the collections to createGuiAndShow		

		ArrayList<packman> packPix=new ArrayList<packman>();
		while(pIndex<packSize) {

			double x = pp.get(pIndex).getX();
			double y=pp.get(pIndex).getY();
			Point3D temp =coords2Pixel(x,y);
			String id=Integer.toString(pIndex);
			double speed = pp.get(pIndex).getSpeed();
			double radius = pp.get(pIndex).getRadius();
			metaDataPack data=new metaDataPack(id,speed,radius);
			Point3D position =new Point3D(temp.x(),temp.y(),temp.z());
			packman tempPack=new packman(data,position);
			packPix.add(tempPack);
			pIndex++;
			System.out.println(tempPack);
		}

		return packPix;
	}

	/**
	 * this function scan all the elements in the current ArrayList and convert all the fruits data 
	 * from Coordinates to Pixels
	 * @param ff ArrayList that contains fruits
	 * @return new arrayList of fruits with data in Pixels
	 */
	public static ArrayList<fruit> Coords2PixelFruit(ArrayList<fruit> ff ) throws IOException {


		int fruitSize=ff.size();
		int fIndex=0;
		// create fruit and packmans collection with PIXEL coords
		// in order to send the collections to createGuiAndShow
		ArrayList<fruit> fruitPix=new ArrayList<fruit>();
		while(fIndex<fruitSize) {

			Point3D temp =coords2Pixel(ff.get(fIndex).getX(),ff.get(fIndex).getY());
			String id=Integer.toString(fIndex);
			metaDataFruit data=new metaDataFruit(id,1);
			Point3D position =new Point3D(temp.x(),temp.y(),temp.z());
			fruit tempF=new fruit(data,position);
			fruitPix.add(tempF);
			fIndex++;
			System.out.println(tempF);
		}
		return fruitPix;
	}
	
	/**
	 * this function scan all the elements in the current ArrayList and convert all the fruits data 
	 * from Pixels to Coordinates
	 * @param ff ArrayList that contains fruits
	 * @return new arrayList of fruits with data in Coordinates
	 */
	public static ArrayList<fruit> pixels2CoordsFruit(ArrayList<fruit> ff ) throws IOException {

		int fruitSize=ff.size();
		int fIndex=0;
		// create fruit and packmans collection with PIXEL coords
		// in order to send the collections to createGuiAndShow
		ArrayList<fruit> fruitCoords=new ArrayList<fruit>();
		while(fIndex<fruitSize) {

			Point3D temp =pixel2Coords(ff.get(fIndex).getX(),ff.get(fIndex).getY());
			String id=Integer.toString(fIndex);
			metaDataFruit data=new metaDataFruit(id,1);
			Point3D position =new Point3D(temp.x(),temp.y(),temp.z());
			fruit tempF=new fruit(data,position);
			fruitCoords.add(tempF);
			fIndex++;
			System.out.println(tempF);
		}
		return fruitCoords;
	}

	/**
	 * this function scan all the elements in the current ArrayList and convert all the packmans data 
	 * from Pixels to Coordinates
	 * @param pp ArrayList that contains packmans
	 * @return new arrayList of packmans with data in Coordinates
	 */
	public static ArrayList<packman> pixels2CoordsPack(ArrayList<packman> pp ) {

		int packSize=pp.size();
		int pIndex=0;
		// create fruit and packmans collection with PIXEL coords
		// in order to send the collections to createGuiAndShow		

		ArrayList<packman> packCoords=new ArrayList<packman>();
		while(pIndex<packSize) {

			double x = pp.get(pIndex).getX();
			double y=pp.get(pIndex).getY();
			Point3D temp =pixel2Coords(x,y);
			String id=Integer.toString(pIndex);
			double speed = pp.get(pIndex).getSpeed();
			double radius = pp.get(pIndex).getRadius();
			metaDataPack data=new metaDataPack(id,speed,radius);
			Point3D position =new Point3D(temp.x(),temp.y(),temp.z());
			packman tempPack=new packman(data,position);
			packCoords.add(tempPack);
			pIndex++;
			System.out.println(tempPack);
		}

		return packCoords;
	}
		
	/**
	 * this function scan all path Collections in the solution and convert from Coordinates to pixels
	 * @param s Solution that contains all packmans path
	 * @return new Solution in pixels
	 */
	public static solution solutionToPixel(solution s){
		
		int size=s.getPathCollection().size();
		int i=0;
		solution pixSolution =new solution();
		while(size>i) {
				
			//create new path
			path p=new path();
			
			for(int j=0;j<s.getPathCollection().get(i).getPath().size();j++) {
				
				Point3D temp=s.getPathCollection().get(i).getPathI(j);
				temp=coords2Pixel(temp.x(), temp.y());
			
				//add point to path
				p.add(temp);
			}
			
			//add path to solution
			pixSolution.add(p);
			
			i++;
		}
		return pixSolution;
	}
}