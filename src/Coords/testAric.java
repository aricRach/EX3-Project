package Coords;

import Geom.Point3D;

public class testAric {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MyCoords m=new MyCoords();

		//32.101923, 35.212451
		Point3D start=new Point3D( 32.105835, 35.202219,0);
		Point3D end=new Point3D(  32.101923, 35.212451,0);
		/*Point3D start=new Point3D(32.105903, 35.202288,0);
		Point3D end=new Point3D(32.100885,35.211984,0);  */
		//Point3D end=new Point3D(32.101525, 35.212132,0);

		//Point3D answer= m.add(start, vm);
		//System.out.println(answer);

		bla(604,357,start,end);
		//two(32.103812,35.206049,start,end);
		two(32.104984,35.208300,start,end);

		// 32.102457, 35.207459 expectetd
		//101689° 
		//// 32.105835, 35.202219
	}

	public static void bla(double x,double y,Point3D p1,Point3D p2) {

		double dx=x/1433;
		double dy=y/642;

		double totalLon=p2.y()-p1.y();
		double totalLat=p2.x()-p1.x();

		System.out.println("total Lon:"+totalLon);
		System.out.println("total lat: "+totalLat);

		double goLon=totalLon*dx;
		double goLat=totalLat*dy;


		System.out.println("go Lon:"+goLon);
		System.out.println("go lat: "+goLat);


		double answer1=p1.y()+goLon;
		double answer2=p1.x()+goLat;


		System.out.println(answer2 +","+answer1);

	}


	//public static void two(double x,double y,Point3D p1,Point3D p2) {
	public static void two(double x,double y,Point3D p1,Point3D p2) {

		double distLat = p1.x()-p2.x();
		double distLon = p2.y()-p1.y();
		double dx=(p1.x()-x)/(distLat);
		double dy=(y-p1.y())/(distLon);

		double totalPixelX=1433;
		double totalPixelY=642;

		//			System.out.println("total Lon:"+totalLon);
		//			System.out.println("total lat: "+totalLat);

		double goY=(totalPixelY*dx);
		double goX=(totalPixelX*dy);

		//157 435
		//			System.out.println("go Lon:"+goLon);
		//			System.out.println("go lat: "+goLat);


		double answer1=goY;
		double answer2=goX;


		System.out.println(answer2 +","+answer1);

	
	}

}


