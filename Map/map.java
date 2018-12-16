package Map;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Coords.MyCoords;
import Geom.Point3D;

public class map {

	Image image;
	int height; //pixel of the image
	int width; //pixel of the image
	Point3D start;
	Point3D end;


	public map() throws IOException {

		image= ImageIO.read(new File("Ariel1.png"));
		width=image.getWidth(null);
		height=image.getHeight(null);
		start=new Point3D(32.105835, 35.202219,0);
		end=new Point3D(32.101923, 35.212451,0);
	}


	public Point3D pixel2Coords(double x,double y) {

		double dx=x/width;
		double dy=y/height;

		double totalLon=end.y()-start.y();
		double totalLat=end.x()-start.x();

//		System.out.println("total Lon:"+totalLon);
//		System.out.println("total lat: "+totalLat);

		double goLon=totalLon*dx;
		double goLat=totalLat*dy;


//		System.out.println("go Lon:"+goLon);
//		System.out.println("go lat: "+goLat);


		double answer1=start.y()+goLon;
		double answer2=start.x()+goLat;

//		System.out.println(answer2 +","+answer1);

		return new Point3D(answer2,answer1,0);
	}

	public Point3D coords2Pixel(double x,double y) {

		double distLat = start.x()-end.x();
		double distLon = end.y()-start.y();
		double dx=(start.x()-x)/(distLat);
		double dy=(y-start.y())/(distLon);

		//			System.out.println("total Lon:"+totalLon);
		//			System.out.println("total lat: "+totalLat);

		double goY=(height*dx);
		double goX=(width*dy);

		//157 435
		//			System.out.println("go Lon:"+goLon);
		//			System.out.println("go lat: "+goLat);


		int answer1=(int)(goY);
		int answer2=(int)(goX);

//		System.out.println(answer2 +","+answer1);
		return new Point3D(answer2,answer1,0);	
	}

	public double distanceBet2Pixels(pix a,pix b) {

		Point3D p1=pixel2Coords(a.x(),a.y());
		Point3D p2=pixel2Coords(b.x(),b.y());

		//או להשתמש במרחק רגיל!
		MyCoords m =new MyCoords();
		double distance=m.distance3d(p1, p2);

		return distance;
	}

	public double angleBet2Pixels(pix a,pix b) {

		Point3D p1=pixel2Coords(a.x(),a.y());
		Point3D p2=pixel2Coords(b.x(),b.y());

		MyCoords m =new MyCoords();
		double angle=m.azimuth(p1, p2);

		return angle;
	}
}
