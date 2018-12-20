package Map;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import Coords.MyCoords;
import GIS.fruit;
import GIS.metaDataFruit;
import GIS.metaDataPack;
import GIS.packman;
import GIS.solution;
import GUI.guiGame;
import Geom.Point3D;

public class converts {

	static map m ;
	public converts() throws IOException {

		m= new map();
	}
	

	public static Point3D pixel2Coords(double x,double y) {

		double dx=x/m.width;
		double dy=y/m.height;

		double totalLon=m.end.y()-m.start.y();
		double totalLat=m.end.x()-m.start.x();

//		System.out.println("total Lon:"+totalLon);
//		System.out.println("total lat: "+totalLat);

		double goLon=totalLon*dx;
		double goLat=totalLat*dy;


//		System.out.println("go Lon:"+goLon);
//		System.out.println("go lat: "+goLat);


		double answer1=m.start.y()+goLon;
		double answer2=m.start.x()+goLat;

//		System.out.println(answer2 +","+answer1);

		return new Point3D(answer2,answer1,0);
	}

	public static Point3D coords2Pixel(double x,double y) {

		double distLat = m.start.x()-m.end.x();
		double distLon = m.end.y()-m.start.y();
		double dx=(m.start.x()-x)/(distLat);
		double dy=(y-m.start.y())/(distLon);

		//			System.out.println("total Lon:"+totalLon);
		//			System.out.println("total lat: "+totalLat);

		double goY=(m.height*dx);
		double goX=(m.width*dy);

		//157 435
		//			System.out.println("go Lon:"+goLon);
		//			System.out.println("go lat: "+goLat);


		int answer1=(int)(goY);
		int answer2=(int)(goX);

//		System.out.println(answer2 +","+answer1);
		return new Point3D(answer2,answer1,0);	
	}

	public static double distanceBet2Pixels(pix a,pix b) {

		Point3D p1=pixel2Coords(a.x(),a.y());
		Point3D p2=pixel2Coords(b.x(),b.y());

		MyCoords m =new MyCoords();
		double distance=m.distance3d(p1, p2);

		return distance;
	}

	public static double angleBet2Pixels(pix a,pix b) {

		Point3D p1=pixel2Coords(a.x(),a.y());
		Point3D p2=pixel2Coords(b.x(),b.y());

		MyCoords m =new MyCoords();
		double angle=m.azimuth(p1, p2);

		return angle;
	}

	// 2 Function of Coords to Pixels
	
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
	
	// 2 Function of Pixel to Coords

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
		
	public static solution solutionToPixel(solution s){
		
		int size=s.getPathCollection().size();
		int i=0;
		solution pixSolution =new solution();
		while(size>i) {
				
			path p=new path();
			//create new path
			for(int j=0;j<s.getPathCollection().get(i).getPath().size();j++) {
				
				Point3D temp=s.getPathCollection().get(i).getPathI(j);
				temp=coords2Pixel(temp.x(), temp.y());
			
				p.add(temp);
				//add point to path
				
				//s.getPathCollection().get(i).getPathI(j).setLocationXY(temp); //change the coords point to pixel point
			}
			
			//add path to solution
			pixSolution.add(p);
			
			i++;
		}
		
		return pixSolution;
		
	}



}
