package Map;

import java.io.IOException;

import Geom.Point3D;

public class testMap {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		map m=new map();

		Point3D p=m.coords2Pixel( 32.104939, 35.208279);
	    System.out.println(p.x()+" , "+p.y());
	    pix one=new pix(p.x(),p.y());// create a pixle object from the values of p

	    Point3D p2=m.pixel2Coords(851, 139);	    
	    System.out.println(p2.x()+" , "+p2.y());
	    
	    Point3D a=m.coords2Pixel(  32.103525, 35.207204);
	    System.out.println(a.x()+" , "+a.y());
	    pix two=new pix(a.x(),a.y());// create a pixle object from the values of a

	    
	    Point3D a2=m.pixel2Coords(693.0,379.0);	    
	    System.out.println(a2.x()+" , "+a2.y());
	    
	    System.out.println("the distance between the two pixels is: ");
	    System.out.println(m.distanceBet2Pixels(one, two));
	    
	    System.out.println("the angle between the two pixels is: ");
	    System.out.println(m.angleBet2Pixels(one, two));
	    
	    
		
	}

}
