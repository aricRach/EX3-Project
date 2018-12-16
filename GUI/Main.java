/*package GUI;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import GIS.fruit;
import GIS.packman;


public class Main 
{
	public static void main(String[] args) throws IOException
	{
		MainWindow window = new MainWindow();
		window.setVisible(true);
		window.setSize(window.myImage.getWidth(),window.myImage.getHeight());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ArrayList<fruit> fA=new ArrayList<fruit>();
		fruit tempF=new fruit("1", 350, 700, 0, 1);
		fruit tempF2=new fruit("1", 490, 600, 0, 1);
		fA.add(tempF);
		fA.add(tempF2);

		ArrayList<packman> pack=new ArrayList<packman>();
		packman tempp=new packman("1", 100, 200, 0,1,1);
		pack.add(tempp);

		window.play(fA, pack);




	}
}
*/