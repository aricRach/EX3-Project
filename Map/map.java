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
}
