package Threads;

import java.awt.Color;
import GUI.guiGame;
import Map.path;

public class MyThread extends Thread {
	private String _name;
	private path p;
	private Color c;
	private int index;
	
	public MyThread(String name,Color c,path p,int index ) {
	super(name);
	_name = name;
	this.p=p;
	this.c=c;
	this.index=index;
	}
	
	
	@Override
	public String toString() {
		return "MyThread [_name=" + _name + ", p=" + p + ", c=" + c + "]";
	}

	/** this is the parallel thing */

	// pass all the path and send the coordinates function that paint the points on the screen
	public void run() {
		 
		 for(int i=0;i<p.getPath().size();i++) {
			 
			int x=(int) p.getPathI(i).x();
			int y= (int) p.getPathI(i).y();
			
			guiGame.callToPaintT(x,y,c,index);
			
		 }
     }
}