package algorithm;

import java.util.ArrayList;

import Coords.MyCoords;
import GIS.fruit;
import GIS.game;
import GIS.metaDataFruit;
import GIS.packman;
import GIS.solution;
import Geom.Point3D;

public class algo {

	//need to improve the position?!
		public void packEatFruit(fruit f,packman p) {
			
			
			MyCoords m =new MyCoords();
			System.out.println("packman " +p.getId() +" eats fruit "+ f.getId());

			// CHECK THE TIME IT WILL TAKE TO EAT TO FRUIT AFTER CHANGING THE POSTION OF FRUIT BY RADIUS=newFruitPosition
			p.setPoints(p.getPoints()+f.getWeight());//update the points of the packman
			Point3D tempPointFruit=changeCoordsAcordnigToRadius(f.getPosition(), p.getPosotion(), p.getRadius()); 
			metaDataFruit data=new metaDataFruit(f.getId(),f.getWeight());
			fruit newFruitPosition =new fruit(data,tempPointFruit);
			
			double timeToEat=getTime(p,newFruitPosition);
			game.setTotalTime(game.getTotalTime()+timeToEat);
			p.setTime(p.getTime()+getTime(p,newFruitPosition));// add to packman's time agg the time to eat this fruit
			double i=0;// the additional time 
			while(m.distance3d(f.getPosition(), p.getPosotion())>p.getRadius()) {// send the original location  fruit
				i++;// one second per point path
				proceedToFruit(p,f,i);
//				if (i<timeToEat) {
	//
//					proceedToFruit(p,f,i);
	//
//				}
				
			}
			//p.addToTrack(f.getPosition()); // add the position of the fruit the the path
			//p.setPosition(f.getPosition()); // update the position of the packman to fruit's postion
			System.out.println("total time: "+game.getTotalTime());
			game.setScore(game.getScore()+f.getWeight());
			
		}
	
		
		public void proceedToFruit(packman p,fruit f,double i) {
			
			Point3D temp=changeCoordsAcordnigToRadius(p.getPosotion(),f.getPosition(),p.getRadius());
			p.setPosition(temp);
			temp.setTime((long) (game.getStartTimer()+i*1000));// add second  to any point in the path for kml showing
			p.addToTrack(temp); // add to track the current position afeter procceding to fruit
		}

		// f is fruit that closer in raduis to packman
		private double getTime(packman p,fruit f) {

			MyCoords m= new MyCoords(); 
			Point3D packmanPoint=p.getPosotion();
			Point3D friutPoint=f.getPosition();

			double speedPack=p.getSpeed();
			double dist=m.distance3d(packmanPoint,friutPoint);
			if (dist<p.getRadius()) {
				
				return 0;
			}
			return dist/speedPack;
		}

	//****calcAll**
	

	public solution calcAll(ArrayList<fruit> fruits,ArrayList<packman> packs) {

		MyCoords m= new MyCoords(); 

		solution answer=new solution();

		//ArrayList<fruit> copy =copy();
		int size=fruits.size();

		int i=0;
		while(i<size) {

			fruit current = fruits.get(i);
			packman toEat=eatFruit(current,packs);// check which packman should eat this fruit
			packEatFruit(current,toEat); //make the packman eat this fruit
	
			//fruits.remove(i);
			i++;
		}

		// add the paths of the packmans to the solution
		for(int j=0;j<packs.size();j++) {

			answer.getPathCollection().add(packs.get(j).getPath());
		}

		return answer;
	}

/**
 * This function get one fruit and collection of packmans and return the packman
 * who should eat the fruit.
 * @param f
 * @param p
 * @return
 */
public packman eatFruit(fruit f,ArrayList<packman> p) {

	double min=Double.MAX_VALUE;
	int current=-1;
	for(int i=0;i<p.size();i++) {

		Point3D tempPointFruit=changeCoordsAcordnigToRadius(f.getPosition(), p.get(i).getPosotion(), p.get(i).getRadius()); 	
		metaDataFruit data= new metaDataFruit(f.getId(), f.getWeight());
		
		fruit newFruitPosition =new fruit(data,tempPointFruit);
		
		packman temp=p.get(i);
		if (getTime(temp, newFruitPosition)<min) {

			min=getTime(temp, newFruitPosition);
			current=i;
		}

	}
	if (current!=-1) {
	
		return p.get(current);
	}
	System.out.println("error");
	return null;
	
}

public Point3D changeCoordsAcordnigToRadius(Point3D p1,Point3D p2,double radius) {
	
	MyCoords m=new MyCoords();
	
	double distance=m.distance3d(p1,p2);
	if (m.distance3d(p1, p2)!=0) {

		double ratio=radius/distance;
		double lat = p1.x() + ((p2.x() - p1.x()) * ratio);
		double lon= p1.y() + ((p2.y() - p1.y()) * ratio);
		
		//System.out.println(lat+","+lon);
		return new Point3D(lat,lon,p1.z());
	
	}
	return p1;// or p2 they equals
	

}

}
