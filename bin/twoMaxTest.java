
/*import javax.imageio.*; import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
public class ImgInsert extends JPanel{
	BufferedImage img = null;
	public ImgInsert(String path) {
		try{
			img= ImageIO.read(new File(path));
		} catch(IOException a) {}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 250, 0, 900, 300, null);
	}
}*/
