package File_format;

import java.io.IOException;
import java.util.ArrayList;
import GIS.fruit;
import GIS.packman;
import Map.converts;

/**
 * This class create String that will contains ArrayLists (Fruits and Packmans) data
 * @author Aric and Tal 
 */

public class writeCsv {
	
	/**
	 * This function receive 2 ArrayLists  
	 * @param ff ArrayList of fruits
	 * @param pp ArrayList of packmans
	 * @return String that contains all csv Data 
	 * @throws IOException
	 */
	public static String Write(ArrayList<fruit> ff,ArrayList<packman> pp) throws IOException {
		
	   converts c = new converts();

	   ArrayList<packman> pCoords=converts.pixels2CoordsPack(pp);
       ArrayList<fruit> fCoords=converts.pixels2CoordsFruit(ff);
		
		String str = "Type,id,Lat,Lon,Alt,Speed/Weight,Radius,"+pCoords.size()+","+fCoords.size()+"\n";
		for(int i=0;i<pCoords.size();i++) {
			
			str+="P,"+pCoords.get(i).getId()+","+pCoords.get(i).getX()+","+pCoords.get(i).getY()+","+
					pCoords.get(i).getZ()+","+pCoords.get(i).getSpeed()+","+pCoords.get(i).getRadius()+","+"\n";
		}

		for(int i=0;i<fCoords.size()-1;i++) {

			str+="F,"+fCoords.get(i).getId()+","+fCoords.get(i).getX()+","+fCoords.get(i).getY()+","+
					fCoords.get(i).getZ()+","+fCoords.get(i).getWeight()+","+"\n";
		}
		
		str+="F,"+fCoords.get(fCoords.size()-1).getId()+","+fCoords.get(fCoords.size()-1).getX()+","+
		fCoords.get(fCoords.size()-1).getY()+","+
				fCoords.get(fCoords.size()-1).getZ()+","+fCoords.get(fCoords.size()-1).getWeight();		
		
		return str;
	}
}