package GUI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import File_format.createKmlKabaso;
import File_format.writeCsv;
import GIS.fruit;
import GIS.game;
import GIS.metaDataFruit;
import GIS.metaDataPack;
import GIS.packman;
import GIS.solution;
import Geom.Point3D;
import Map.converts;
import Map.map;
import Threads.MyThread;
import algorithm.algo;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class guiGame extends JFrame 
{
	static JMenuBar wholeMenuBar;
	JMenu fileMenu;
	JMenuItem openItem, saveItem,clearItem,runItem;
	static JMenuItem createKml;
	static solution s;
	static converts c;

	 static ArrayList<fruit> fruits;
	 static ArrayList<packman> packmans;

	static int x = -1; 
	static int y = -1;
	static long pressedTime;// pressed 
	static long timeClicked;// leave
	static boolean hide=true;



	static ImageIcon imageIcon;
	static MyJLabel jLabel ;

	public guiGame ()
	{
		super("Packman Game");
		fruits=new ArrayList<fruit>();
		packmans=new ArrayList<packman>();
		s=new solution();		
		try {
			c=new converts();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void callToPaintT(int x,int y,Color c,int index) {
		
		
		jLabel.Thredpaint(x,y,c,index);
		
		
	}
	public void init() {


		wholeMenuBar = new JMenuBar();
		setJMenuBar(wholeMenuBar);
		wholeMenuBar.setVisible(true);
		//set file menu	with open, save
		fileMenu = new JMenu("File");
		openItem = new JMenuItem("Open");
		//CTRL_MASK: The control modifier. An indicator that the control key was held down during the event.
		//openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,  ActionEvent.CTRL_MASK));
		fileMenu.add(openItem);
		saveItem = new JMenuItem("Save");
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		fileMenu.add(saveItem);
		//run
		runItem=new JMenuItem("run");
		fileMenu.add(runItem);
		runItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));

		//clear
		clearItem=new JMenuItem("clear");
		fileMenu.add(clearItem);
		clearItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		
		//createKml
		createKml=new JMenuItem("save as kml");
		fileMenu.add(createKml);
		createKml.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK));
		createKml.setEnabled(false); // not allowing to create kml file when the game is empty


		wholeMenuBar.add(fileMenu);

		imageIcon = new ImageIcon("Ariel1.png");
		jLabel = new MyJLabel(imageIcon);
		getContentPane().add(jLabel);


	}

	public  void createAndShowGUI()
	{
		init();

		openItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					fruits.clear();
					packmans.clear();
					readFileDialog();
					createKml.setEnabled(true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		saveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeFileDialog();
			}
		});

		runItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createKml.setEnabled(true);
				//setResizable(false);
//				converts c = null;
//				try {
//					c = new converts();
//				} catch (IOException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				}
				System.out.println("the game start");
				//we can call to function that paint the path of the packmans
				try {
					
					if (packmans.size()==0) {
						
						throw new RuntimeException("can't start , there is no packman");
					}
					
					ArrayList<fruit>fCoords=converts.pixels2CoordsFruit(fruits);
					ArrayList<packman>pCoords=converts.pixels2CoordsPack(packmans);

					//	game g=new game(fCoords,pCoords);  
				//	algo al=new algo();

					s=algo.calcAll(fCoords, pCoords);
					solution solutionPixel=converts.solutionToPixel(s);

					jLabel.paintEat(solutionPixel);


				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		

		clearItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.out.println("clean pressed");
				fruits.clear();
				packmans.clear();
				createKml.setEnabled(false);
				repaint();
			}
		});
		
		createKml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				writeFileDialogKml();
			}
		});


		addWindowListener( new WindowAdapter()
		{
			public void windowResized(WindowEvent evt)
			{
				jLabel.repaint();
				System.out.println(jLabel.getWidth());
				System.out.println(jLabel.getHeight());
				System.out.println("hi");
			}
		});

		setSize(850,500);
		System.out.println(JLabel.HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		jLabel.repaint();
	}

	public void readFileDialog() throws IOException {

		this.setExtendedState(this.MAXIMIZED_BOTH); // full screen
		//this.setExtendedState(this.MAXIMIZED_BOTH); //set full screen in order to show all the elements in the frame

		//		try read from the file
		FileDialog fd = new FileDialog(this, "Open text file", FileDialog.LOAD);
		fd.setFile("*.csv");
		fd.setDirectory("C:\\Users\\Elizabeth\\Desktop\\Temp");
		fd.setFilenameFilter(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".csv");
			}
		});

		fd.setVisible(true);
		String folder = fd.getDirectory();
		String fileName = fd.getFile();       
		this.setTitle(fileName);// give the game the name of the file


		//game
		game g=new game(fruits,packmans);
		game.createGameCollection(folder+"\\"+fileName);
		g.paintGame();

		//call function to show the packman moving

		try {
			FileReader fr = new FileReader(folder + fileName);
			BufferedReader br = new BufferedReader(fr);
			String str = new String();
			for (int i = 0; str != null; i = i + 1) {
				str = br.readLine();
				if (str != null) {
					System.out.println(str);
				}
			}
			br.close();
			fr.close();
		} catch (IOException ex) {
			System.out.print("Error reading file " + ex);
			System.exit(2);
		}
	}

	public void writeFileDialog() {
		//		 try write to the file
		FileDialog fd = new FileDialog(this, "Save the text file", FileDialog.SAVE);
		fd.setFile("*.csv");
		fd.setFilenameFilter(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		});
		fd.setVisible(true);
		String folder = fd.getDirectory();
		String fileName = fd.getFile();
		try {
			FileWriter fw = new FileWriter(folder + fileName);
			PrintWriter outs = new PrintWriter(fw);
			String csvString=writeCsv.Write(fruits, packmans);
			outs.println(csvString);
			outs.close();
			fw.close();
		} catch (IOException ex) {
			System.out.print("Error writing file  " + ex);
		}

	}
	
	public void writeFileDialogKml() {
		//		 try write to the file
		FileDialog fd = new FileDialog(this, "create and save the kml file", FileDialog.SAVE);
		fd.setFile("*.kml");
		fd.setFilenameFilter(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".kml");
			}
		});
		fd.setVisible(true);
		String folder = fd.getDirectory();
		String fileName = fd.getFile();
		try {
			FileWriter fw = new FileWriter(folder + fileName);
			PrintWriter outs = new PrintWriter(fw);
			ArrayList<packman> ppCoords=converts.pixels2CoordsPack(packmans);
			ArrayList<fruit> ffCoords = converts.pixels2CoordsFruit(fruits);
			String save=folder+"\\"+fileName;
			//createKmlKabaso.run(ffCoords, ppCoords,s,save);
			outs.close();
			fw.close();
		} catch (IOException ex) {
			System.out.print("Error writing file  " + ex);
		}

	}

	//G
	//get arrayList of fruits and packmans from game into the gui and repaint with the new arraysList
	public void createAndShowGUI2(ArrayList<fruit>ff,ArrayList<packman>pp)
	{	
		//push the ff and pp into fruits and packmans
		int i=0;
		while(i<pp.size()) {

			packmans.add(pp.get(i));
			i++;
		}
		i=0;
		while(i<ff.size()) {

			fruits.add(ff.get(i));
			i++;
		}
		addWindowListener( new WindowAdapter()
		{
			public void windowResized(WindowEvent evt)
			{
				jLabel.repaint();
			}
		});

//		init();
//
//		setSize(850,500);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setVisible(true);
		jLabel.repaint();

	}

	// end G

	public static void main(String st[])
	{
		SwingUtilities.invokeLater( new Runnable()
		{
			@Override
			public void run()
			{
				guiGame demo = new guiGame();
				demo.createAndShowGUI();

			}
		});
	}

	public static int fruitSize() {

		return fruits.size();
	}
	
	public static int packSize() {
		return packmans.size();
	}

	public static ArrayList<fruit> getFruitArr() {

		return fruits;
	}
	public static ArrayList<packman> getPackArr() {

		return packmans;
	}

}
class MyJLabel extends JLabel implements MouseListener
{

	ImageIcon imageIcon;
	private int oldWidth;// for strach the screen
	private int oldHeight;// // for strach the screen  

	public 	MyJLabel(ImageIcon icon)
	{
		super();
		this.imageIcon = icon;
		addMouseListener(this);
		oldHeight=getHeight();
		oldWidth=getWidth();

	}

	public void reSizeFruit(){

		int size=guiGame.fruits.size();
		int i=0;
		while(i<size) {

			double xOld=guiGame.fruits.get(i).getX();
			double yOld=guiGame.fruits.get(i).getY();

			double newWidth = xOld*getWidth()/oldWidth;
			double newHeight = yOld*getHeight()/oldHeight;

			guiGame.fruits.get(i).setX(newWidth);

			guiGame.fruits.get(i).setY(newHeight);

			i++;
		}
	}

	public void reSizePackman(){

		int size=guiGame.packmans.size();
		int i=0;
		while(i<size) {

			double xOld=guiGame.packmans.get(i).getX();
			double yOld=guiGame.packmans.get(i).getY();

			double newWidth = xOld*getWidth()/oldWidth;
			double newHeight = yOld*getHeight()/oldHeight;

			guiGame.packmans.get(i).setX(newWidth);
			guiGame.packmans.get(i).setY(newHeight);

			i++;
		}
	}

	@Override
	public void paintComponent(Graphics g)
	{

		super.paintComponent(g);
		g.drawImage(imageIcon.getImage(),0,0,getWidth(),getHeight(),this);

		// update the pixels after strech
		reSizeFruit();
		reSizePackman();
		oldHeight=getHeight();
		oldWidth=getWidth();

		Color c=new Color(251,0,0);//red apple
		g.setColor(c);

		int sizeFruit=guiGame.fruitSize();//get the number of fruits we draw
		int i=0;//index of the arrayList
		while(i<sizeFruit) {//there are still fruits to show

			int r = 10;
			guiGame.x = (int)guiGame.getFruitArr().get(i).getX() - (r / 2);//instead of casting should be pixels cordinate!!!!!!!!!!!
			guiGame.y = (int)guiGame.getFruitArr().get(i).getY() - (r / 2);//instead of casting should be pixels cordinate!!!!!!!!!!!

			g.fillOval(guiGame.x, guiGame.y, r, r);

			i++;
		}

		c=new Color(0,0,204);//blue packman
		g.setColor(c);
		int sizePackmans=guiGame.packSize();
		int j=0;
		while(j<sizePackmans) {//there are still packmans to show

			int r = 15;
			guiGame.x = (int)guiGame.getPackArr().get(j).getX() - (r / 2);//instead of casting should be pixels cordinate!!!!!!!!!!!
			guiGame.y = (int)guiGame.getPackArr().get(j).getY() - (r / 2);//instead of casting should be pixels cordinate!!!!!!!!!!!
			g.fillOval(guiGame.x, guiGame.y, r, r);
			j++;
		}

	}

	public void paintEat(solution s) {

		Graphics g=this.getGraphics();
		super.paintComponent(g);
		g.drawImage(imageIcon.getImage(),0,0,getWidth(),getHeight(),this);

		//strech the window 
		reSizeFruit();
		reSizePackman();
		//now the old Height and width is the current 
		oldHeight=getHeight();
		oldWidth=getWidth();

		paintFruits();
		paintPackmans();
		paintPath(s);

	}

	public void paintFruits() {

		Graphics g=this.getGraphics();
		Color c=new Color(251,0,0);//red apple
		g.setColor(c);

		int sizeFruit=guiGame.fruitSize();//get the number of fruits we draw
		int i=0;//index of the arrayList
		while(i<sizeFruit) {//there are still fruits to show

			
			int r = 10;
			guiGame.x = (int)guiGame.getFruitArr().get(i).getX() - (r / 2);//instead of casting should be pixels cordinate!!!!!!!!!!!
			guiGame.y = (int)guiGame.getFruitArr().get(i).getY() - (r / 2);//instead of casting should be pixels cordinate!!!!!!!!!!!

			g.fillOval(guiGame.x, guiGame.y, r, r);

			i++;
		}
	}

	public void paintPackmans() {

		Graphics g=this.getGraphics();
		Color c=new Color(0,0,204);//blue packman
		g.setColor(c);
		int sizePackmans=guiGame.packSize();
		int j=0;
		while(j<sizePackmans) {//there are still packmans to show

			int r = 15;
			guiGame.x = (int)guiGame.getPackArr().get(j).getX() - (r / 2);//instead of casting should be pixels cordinate!!!!!!!!!!!
			guiGame.y = (int)guiGame.getPackArr().get(j).getY() - (r / 2);//instead of casting should be pixels cordinate!!!!!!!!!!!
			g.fillOval(guiGame.x, guiGame.y, r, r);
			j++;
		}
	}

	public void paintPath(solution s) {

		//paintFruits();//delete

		
		Graphics g=this.getGraphics();
		int solutionSize=s.getSize();
		int k=0;

		// pass all the path in the solution ,create thread for each path and use the start function
		while(solutionSize>k) {

			Color c=randomColor();
			g.setColor(c);
			
			MyThread t1 = new MyThread("",c, s.getPathCollection().get(k),k);// k is the index of the solution 
			//so its the index of the current packman path
			t1.start();
			k++;
		}
	}
	
	public void Thredpaint(int x,int y,Color c,int index) {
		
		Graphics g=this.getGraphics();
		g.setColor(c);
		
		try {
			
			double speed=guiGame.packmans.get(index).getSpeed();
			Thread.sleep((long) (15/speed));
			g.fillOval(x, y, 3, 3);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Color randomColor() {

		int r=(int) (Math.random()*255+1);
		int g=(int) (Math.random()*255+1);
		int b=(int) (Math.random()*255+1);

		return new Color(r,g,b);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

		guiGame.pressedTime = System.currentTimeMillis();
		guiGame.x = e.getX();
		guiGame.y = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

		guiGame.createKml.setEnabled(true);
		//by pressing more then 2 seconds create a packman 
		// by pressing less then 2 seconds create an apple
		System.out.println("mouse Clicked");
		guiGame.timeClicked = System.currentTimeMillis() - guiGame.pressedTime;
		System.out.println("("+ e.getX() + "," + e.getY() +")");

		if (guiGame.timeClicked >= 500) {

			String i="draw pack";
			i+=1;
			metaDataPack data=new metaDataPack(i,1,1);
			Point3D position =new Point3D(guiGame.x,guiGame.y,0);
			packman pack=new packman(data,position);//add the pixels !!
			guiGame.packmans.add(pack);
			repaint();
			System.out.println("number of packmans: "+guiGame.packmans.size());
			guiGame.createKml.setEnabled(false);
		}else {

			String j="draw fruit";
			j+=1;
			metaDataFruit data=new metaDataFruit(j,1);
			Point3D position =new Point3D(guiGame.x,guiGame.y,0);
			fruit f=new fruit(data,position);//add the pixels !!
			guiGame.fruits.add(f);
			repaint();
			System.out.println("number of fruits: "+guiGame.fruits.size());
		}
	}

}

