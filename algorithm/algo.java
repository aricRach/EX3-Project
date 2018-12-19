package algorithm;

import java.util.ArrayList;
import Coords.MyCoords;
import GIS.fruit;
import GIS.game;
import GIS.metaDataFruit;
import GIS.metaDataPack;
import GIS.packman;
import GIS.solution;
import Geom.Point3D;

public abstract class algo {

	//need to improve the position?!
	public static void packEatFruit(fruit f,packman p) {
		
		MyCoords m =new MyCoords();
		System.out.println("packman " +p.getId() +" eats fruit "+ f.getId());
		p.getEatenFruits().add(f);
		// CHECK THE TIME IT WILL TAKE TO EAT TO FRUIT AFTER CHANGING THE POSTION OF FRUIT BY RADIUS=newFruitPosition
		p.setPoints(p.getPoints()+f.getWeight());//update the points of the packman
		Point3D tempPointFruit=changeCoordsAcordnigToRadius(f.getPosition(), p.getPosition(), p.getRadius()); 
		metaDataFruit data=new metaDataFruit(f.getId(),f.getWeight());
		fruit newFruitPosition =new fruit(data,tempPointFruit);

		double timeToEat=getTime(p,newFruitPosition);

		game.setTotalTime(game.getTotalTime()+timeToEat);

		p.setTime(p.getTime()+getTime(p,newFruitPosition));// add to packman's time agg the time to eat this fruit
		//double i=0;// the additional time 
		while(m.distance3d(f.getPosition(), p.getPosition())>p.getRadius()) {// send the original location  fruit
			proceedToFruit(p,f);
		}
	//	f.getPosition().setEaten(true);//delete
		//p is raduis p.getPos.flag=isFruit
		//p.addToTrack(f.getPosition()); // add the position of the fruit the the path
		//p.setPosition(f.getPosition()); // update the position of the packman to fruit's postion
		System.out.println("total time: "+game.getTotalTime());
		game.setScore(game.getScore()+f.getWeight());

	}


	public static void proceedToFruit(packman p,fruit f) {

		Point3D temp=changeCoordsAcordnigToRadius(p.getPosition(),f.getPosition(),p.getRadius());
		p.setPosition(temp);
		p.addToTrack(temp); // add to track the current position afeter procceding to fruit
	}

	// f is fruit that closer in raduis to packman
	private static double getTime(packman p,fruit f) {

		MyCoords m= new MyCoords(); 
		Point3D packmanPoint=p.getPosition();
		Point3D friutPoint=f.getPosition();

		double speedPack=p.getSpeed();
		double dist=m.distance3d(packmanPoint,friutPoint);
		if (dist<p.getRadius()) {

			return 0;
		}
		//System.out.println("the time to eat check: "+dist/speedPack);
		return dist/speedPack;
	}

	//****calcAll**


	public static solution calcAll(ArrayList<fruit> fruits,ArrayList<packman> packs) {

		solution answer=new solution();
		
		int size=fruits.size();

		int k=0;
		while(k<size) { //pass all the fruits

		
			double min=Double.MAX_VALUE;
			int index=-1;
			 int packSize=packs.size();
			 int minToEat=0;
			for(int i=0;i<packSize;i++) { // pass all the packmans and check which packman should eat
				
				packman current = packs.get(i);
				int toEat=eatFruit(fruits,current);//return the index of fruit the packman should eat
				
				Point3D tempPointPackman=changeCoordsAcordnigToRadius(current.getPosition(), fruits.get(toEat).getPosition(), current.getRadius()); 	
				metaDataPack data= new metaDataPack(current.getId(), current.getSpeed(), current.getRadius());

				packman newPackPosition =new packman(data,tempPointPackman);// the current packman after radius change
				
				double timeToEat=getTime(newPackPosition, fruits.get(toEat));
				if (timeToEat<min) {
					
					min=timeToEat;
					index=i;// the packman that will eat fastest
					minToEat=toEat;
				}
				
			}
			System.out.println("packman: "+packs.get(index).getId()+" win");
			packman current = packs.get(index); // the packman that should eat the fruit stores in "toEat"
			//int toEat=eatFruit(fruits,current);//return the index of fruit the packman should eat
			//create temp fruit beacuse i want to remove the original fruit from the collection
			fruit temp=new fruit(fruits.get(minToEat).getData(), fruits.get(minToEat).getPosition());
			fruits.remove(minToEat);
			packEatFruit(temp,current); //make the packman eat this fruit
			
			k++;
		}

		// add the paths of the packmans to the solution
		for(int j=0;j<packs.size();j++) {

			answer.getPathCollection().add(packs.get(j).getPath());
		}
		
		System.out.println(answer);

		return answer;
	}

	/**
	 * This function get one packman and collection of fruits and return the fruit
	 * who should be eaten the packman.
	 * @param f
	 * @param p
	 * @return
	 */
	public static int eatFruit(ArrayList<fruit> f,packman p) {

		double min=Double.MAX_VALUE;
		int current=-1;
		for(int i=0;i<f.size();i++) {

			Point3D tempPointPackman=changeCoordsAcordnigToRadius(p.getPosition(), f.get(i).getPosition(), p.getRadius()); 	
			metaDataPack data= new metaDataPack(p.getId(), p.getSpeed(), p.getRadius());

			packman newPackPosition =new packman(data,tempPointPackman);

			fruit temp=f.get(i);
			if (getTime(newPackPosition,temp)<min) {

				min=getTime(newPackPosition,temp);
				current=i;
			}

		}
		return current; // the index of the fruit the packman should eat

	}



	public static Point3D changeCoordsAcordnigToRadius(Point3D p1,Point3D p2,double radius) {

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
	


