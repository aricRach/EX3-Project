/*package GUI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class  kabasoGui extends JFrame implements MouseListener
{
    ImageIcon imageIcon;
    MyJLabel jLabel ;
    public kabasoGui ()
    {
        super("Aric");
    }
    public  void createAndShowGUI()
    {
        imageIcon = new ImageIcon("Ariel1.png");
        jLabel = new MyJLabel(imageIcon);
        getContentPane().add(jLabel);
        addWindowListener( new WindowAdapter()
        {
            public void windowResized(WindowEvent evt)
            {
                jLabel.repaint();
            }
        });
        setSize(850,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        jLabel.repaint();
    }
    
    public void mouseClicked(MouseEvent arg) {
		
    	System.out.println("mouse Clicked");
		System.out.println("("+ arg.getX() + "," + arg.getY() +")");
		
	}
    public static void main(String st[])
    {
 	
    	  kabasoGui demo = new kabasoGui();
          demo.createAndShowGUI();
    }
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
class MyJLabel extends JLabel
{
    ImageIcon imageIcon;
    public MyJLabel(ImageIcon icon)
    {
        super();
        this.imageIcon = icon;
    }
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(imageIcon.getImage(),0,0,getWidth(),getHeight(),this);
    }
}*/