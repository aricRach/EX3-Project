/*package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import GIS.fruit;
import GIS.game;
import GIS.packman;
import GIS.solution;
import Geom.Point3D;
import Map.map;


public class MainWindow extends JFrame implements MouseListener
{
	//שמרנו אצ הפיקסלים בעצם מסוג פרי ולכן הם כאילו קורדינטות של לאט לון אלט
	//ברגע שנדע להמיר קורדינטות לפיקסלים נוכל להתיחס לקורדינטות כמס שלם ואז להשתמש בפונ שיוצרת עיגול

	public BufferedImage myImage;
	ArrayList<fruit> fruits=new ArrayList<fruit>();
	ArrayList<packman> packmans= new ArrayList<packman>();
	int x = -1; //beacuse the oval function recieve int
	int y = -1;
	//for the long press
	long pressedTime;
	long timeClicked;

	public MainWindow() 
	{
		initGUI();		
		this.addMouseListener(this); 
	}

	private void initGUI() 
	{
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("Menu"); 
		MenuItem item1 = new MenuItem("menu item 1");
		MenuItem item2 = new MenuItem("menu item 2");

		menuBar.add(menu);
		menu.add(item1);
		menu.add(item2);
		this.setMenuBar(menuBar);

		try {
			myImage = ImageIO.read(new File("Ariel1.png"));
			// JLabel label = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Ariel1.png"))));
			// setContentPane(label);//when u want to change background image just replace 'localweather' another image.
			//			 int h=myImage.getHeight();
			//			 int w=myImage.getWidth();
			//			 scaleImg(myImage,h,w);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}


	public void paint(Graphics g)
	{
		Color c=new Color(251,0,0);//red apple
		g.setColor(c);
		
		g.drawImage(myImage, 0, 0, this);

		int sizeFruit=fruits.size();//get the number of fruits we draw
		int i=0;//index of the arrayList
		while(sizeFruit>0) {//there are still fruits to show

			int r = 10;
			x = (int)fruits.get(i).getX() - (r / 2);//instead of casting should be pixels cordinate!!!!!!!!!!!
			y = (int)fruits.get(i).getY() - (r / 2);//instead of casting should be pixels cordinate!!!!!!!!!!!
			g.fillOval(x, y, r, r);

			sizeFruit--;
			i++;
		}

		c=new Color(0,0,204);//blue packman
		g.setColor(c);
		int sizePackmans=packmans.size();
		int j=0;
		while(sizePackmans>0) {//there are still packmans to show

			int r = 15;
			x = (int)packmans.get(j).getX() - (r / 2);//instead of casting should be pixels cordinate!!!!!!!!!!!
			y = (int)packmans.get(j).getY() - (r / 2);//instead of casting should be pixels cordinate!!!!!!!!!!!
			g.fillOval(x, y, r, r);

			sizePackmans--;
			j++;
		}

	}

	@Override
	public void mouseClicked(MouseEvent arg) {
		System.out.println("mouse Clicked");
		System.out.println("("+ arg.getX() + "," + arg.getY() +")");
		x = arg.getX();
		y = arg.getY();
		String i="draw fruit";
		i+=1;
		fruit f=new fruit(i, x, y, 0, 1);//add the pixels !!
		fruits.add(f);
		repaint();
		System.out.println("number of fruits: "+fruits.size());
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		//System.out.println("mouse entered");

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

		pressedTime = System.currentTimeMillis();
		x = arg0.getX();
		y = arg0.getY();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

		//by pressing more then 2 seconds create a packman 
		// by pressing less then 2 seconds create an apple
		System.out.println("mouse Clicked");
		timeClicked = System.currentTimeMillis() - pressedTime;
		System.out.println("("+ arg0.getX() + "," + arg0.getY() +")");
		
		if (timeClicked >= 2000) {

			String i="draw pack";
			i+=1;
			packman pack=new packman(i,x,y,0,1,1);//add the pixels !!
			packmans.add(pack);
			repaint();
			System.out.println("number of packmans: "+packmans.size());
		}else {

			String j="draw fruit";
			j+=1;
			fruit f=new fruit(j, x, y, 0, 1);//add the pixels !!
			fruits.add(f);
			repaint();
			System.out.println("number of fruits: "+fruits.size());
		}

	}
	

	// take all the pixels that draw and and saved in arrayLists
	//convert each of them to ArrayList of packman and fruit in coordinates 
	//after that call the function calcAll to use the algoritem 
	public void play(ArrayList<fruit> fruits,ArrayList<packman>packmans) throws IOException {

		map m=new map();
		int fruitSize=fruits.size();
		int fIndex=0;
		int packSize=packmans.size();
		int pIndex=0;
		// create fruit collection with real coords
		ArrayList<fruit> fruitCoords=new ArrayList<fruit>();
		while(fIndex<fruitSize) {

			Point3D temp =m.pixel2Coords(fruits.get(fIndex).getX(),fruits.get(fIndex).getY());
			String id=Integer.toString(fIndex);
			fruit tempF=new fruit(id, temp.x(), temp.y(), temp.z(), 1);
			fruitCoords.add(tempF);
			fIndex++;
			System.out.println(tempF);
		}
		
		// create packman collection with real coords
		ArrayList<packman> packCoords=new ArrayList<packman>();
		while(pIndex<packSize) {

			Point3D temp =m.pixel2Coords(packmans.get(pIndex).getX(),packmans.get(pIndex).getY());
			String id=Integer.toString(pIndex);
			packman tempPack=new packman(id, temp.x(), temp.y(), temp.z(), 1,1);
			packCoords.add(tempPack);
			pIndex++;
		}

		
		game g=new game(fruitCoords,packCoords);

		solution answer2=g.calcAll(fruitCoords, packCoords);
		for(int i=0;i<answer2.getPathCollection().size();i++) {
			System.out.println(answer2.getPathCollection().get(i));
		}
	}


}
*/