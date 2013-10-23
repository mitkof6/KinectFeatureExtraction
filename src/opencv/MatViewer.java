package opencv;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;


public class MatViewer extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage img;
	
	public MatViewer(){
		
	}
	
	public void update(Mat mat, String format){
		MatOfByte matOfByte = new MatOfByte();
	    Highgui.imencode(format, mat, matOfByte); 
	    byte[] byteArray = matOfByte.toArray();

	    try {
	        InputStream in = new ByteArrayInputStream(byteArray);
	        img = ImageIO.read(in);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return;
	    }
	    this.setSize(img.getWidth(), img.getHeight());
	    repaint();
	}
   
	 
    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
}
