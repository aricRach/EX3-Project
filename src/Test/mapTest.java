package Test;

import java.io.IOException;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Coords.MyCoords;
import Geom.Point3D;
import Map.converts;
import Map.pix;

class mapTest {
	
	pix p1;
	pix p2;
	MyCoords m;
	converts c;
	Point3D pitch;
	Point3D circle;
	double distPitchCirc;
	double anglePitchCirc;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		
		p1=new pix(850,130);// football pitch
		p2=new pix(732,544); // middle of the circle
		 m=new MyCoords();
		c=new converts();
		pitch=new Point3D(32.104937,  35.208280);
		circle=new Point3D(32.102477,  35.207464);
		distPitchCirc=m.distance3d(pitch, circle);//284.1
		anglePitchCirc=m.azimuth(pitch, circle);//195.69
	
	}

	@AfterEach
	void tearDown() throws Exception {
	}

//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}
	
	@Test
	public void distBet2Pix() throws IOException {
	
		double expected=distPitchCirc;
		
		double distance=converts.distanceBet2Pixels(p1, p2);// result= 291 the real is 284
		//System.out.println("the distance is "+distance);
		
		Assert.assertEquals(expected, distance,8);
		
	}
	
	@Test 
	public void angleBet2Pix() {
		
		double expected=anglePitchCirc;
		double angle=converts.angleBet2Pixels(p1, p2);// result=195.79 the real is 195.79
		
		//System.out.println("angle is: "+angle);
		Assert.assertEquals(expected, angle,1);
	}

	//in this function i check the distance after i convert to coords and expected to get 
	//the same distance i got when i use distance between two pixels
	@Test
	public void pixel2Coords(){
		
		double expected=converts.distanceBet2Pixels(p1, p2);

		Point3D p1Coords=converts.pixel2Coords(p1.x(), p1.y());
		Point3D p2Coords =converts.pixel2Coords(p2.x(), p2.y());
		
		// we got the same result as the distance between two pixels
		double distanceCurrent=m.distance3d(p1Coords, p2Coords);
		Assert.assertEquals(expected, distanceCurrent,1);
				
	}
	
	// in this fucntion we convert coords2Pixel and calculate the distance between the coords
	// expected to get the same result as distance between the two pixels means that the converts was precise
	@Test
	public void coords2Pixel() {
		
		Point3D p1Pixel=converts.coords2Pixel(pitch.x(),  pitch.y());
		Point3D p2Pixel =converts.coords2Pixel(circle.x(),  circle.y());
		
		pix p1Pix=new pix(p1Pixel.x(),p1Pixel.y());
		pix p2pix=new pix(p2Pixel.x(),p2Pixel.y());
		
	
		double distanceExpected =distPitchCirc;
		double distanceCurrent=converts.distanceBet2Pixels(p1Pix, p2pix);
		
		Assert.assertEquals(distanceExpected, distanceCurrent,1);
		
		/*System.out.println("p1"+p1Pixel.x()+","+p1Pixel.y());//result is 848,147 real is 850,130
		System.out.println("p2"+p2Pixel.x()+","+p2Pixel.y());// result is 734,551 real is 732,544
		System.out.println(distanceExpected+"expect");
		System.out.println(distanceCurrent+"current");
		*/
	}
	
}
