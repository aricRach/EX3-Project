package Map;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import GIS.fruit;
import GIS.metaDataFruit;
import GIS.metaDataPack;
import GIS.packman;
import GIS.solution;
import GUI.guiGame;
import Geom.Point3D;

public class converts {

	map m ;
	public converts() throws IOException {

		m= new map();
	}

	// 2 Function of Coords to Pixels
	
	public ArrayList<packman> Coords2PixelPack(ArrayList<packman> pp ) {

		int packSize=pp.size();
		int pIndex=0;
		// create fruit and packmans collection with PIXEL coords
		// in order to send the collections to createGuiAndShow		

		ArrayList<packman> packPix=new ArrayList<packman>();
		while(pIndex<packSize) {

			double x = pp.get(pIndex).getX();
			double y=pp.get(pIndex).getY();
			Point3D temp =m.coords2Pixel(x,y);
			String id=Integer.toString(pIndex);
			metaDataPack data=new metaDataPack(id,1,1);
			Point3D position =new Point3D(temp.x(),temp.y(),temp.z());
			packman tempPack=new packman(data,position);
			packPix.add(tempPack);
			pIndex++;
			System.out.println(tempPack);
		}

		return packPix;
	}

	public ArrayList<fruit> Coords2PixelFruit(ArrayList<fruit> ff ) throws IOException {


		int fruitSize=ff.size();
		int fIndex=0;
		// create fruit and packmans collection with PIXEL coords
		// in order to send the collections to createGuiAndShow
		ArrayList<fruit> fruitPix=new ArrayList<fruit>();
		while(fIndex<fruitSize) {

			Point3D temp =m.coords2Pixel(ff.get(fIndex).getX(),ff.get(fIndex).getY());
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

	public ArrayList<fruit> pixels2CoordsFruit(ArrayList<fruit> ff ) throws IOException {

		int fruitSize=ff.size();
		int fIndex=0;
		// create fruit and packmans collection with PIXEL coords
		// in order to send the collections to createGuiAndShow
		ArrayList<fruit> fruitCoords=new ArrayList<fruit>();
		while(fIndex<fruitSize) {

			Point3D temp =m.pixel2Coords(ff.get(fIndex).getX(),ff.get(fIndex).getY());
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

	public ArrayList<packman> pixels2CoordsPack(ArrayList<packman> pp ) {

		int packSize=pp.size();
		int pIndex=0;
		// create fruit and packmans collection with PIXEL coords
		// in order to send the collections to createGuiAndShow		

		ArrayList<packman> packCoords=new ArrayList<packman>();
		while(pIndex<packSize) {

			double x = pp.get(pIndex).getX();
			double y=pp.get(pIndex).getY();
			Point3D temp =m.pixel2Coords(x,y);
			String id=Integer.toString(pIndex);
			metaDataPack data=new metaDataPack(id,1,1);
			Point3D position =new Point3D(temp.x(),temp.y(),temp.z());
			packman tempPack=new packman(data,position);
			packCoords.add(tempPack);
			pIndex++;
			System.out.println(tempPack);
		}

		return packCoords;
	}
	
	//delete
	
	public solution solutionToPixel(solution s){
		
		int size=s.getPathCollection().size();
		int i=0;
		solution pixSolution =new solution();
		while(size>i) {
				
			path p=new path();
			//create new path
			for(int j=0;j<s.getPathCollection().get(i).getPath().size();j++) {
				
				Point3D temp=s.getPathCollection().get(i).getPathI(j);
				temp=m.coords2Pixel(temp.x(), temp.y());
			
				p.add(temp);
				//add point to path
				
				s.getPathCollection().get(i).getPathI(j).setLocationXY(temp); //change the coords point to pixel point
			}
			
			//add path to solution
			pixSolution.add(p);
			
			i++;
		}
		
		return pixSolution;
		
	}



}
