package kinect;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import main.Constant;

import org.openni.VideoFrameRef;
import org.openni.VideoMode;
import org.openni.VideoStream;
import org.openni.VideoStream.NewFrameListener;

/**
 * Handles rgb data from kinect
 * 
 * @author Jim Stanev
 *
 */
public class RGBStream extends Component implements NewFrameListener{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int[] imagePixels;
	private VideoStream videoStream;
	private VideoFrameRef lastFrame;
	private BufferedImage bufferedImage;
	
	private boolean start = false;
    
	public RGBStream(VideoStream videoStream){
		this.videoStream = videoStream;
		
		this.videoStream.setVideoMode(new VideoMode(
						Constant.COLOR_WIDTH, 
						Constant.COLOR_HEIGHT,
						Constant.COLOR_FPS,
						Constant.COLOR_PIXEL_FORMAT));
						
		this.videoStream.addNewFrameListener(this);
	}
	
	/**
     * Starts and stops streaming
     */
	public void setStart(){
		if(!start){
			this.videoStream.start();
			start = true;
		}else{
			this.videoStream.stop();
			start = false;
		}
	}
	
	@Override
	public void onFrameReady(VideoStream arg0) {
		lastFrame = videoStream.readFrame();
        ByteBuffer frameData = lastFrame.getData().order(ByteOrder.LITTLE_ENDIAN);
        
        // make sure we have enough room
        if (imagePixels == null || 
        				imagePixels.length < lastFrame.getWidth()*lastFrame.getHeight()) {
            imagePixels = new int[lastFrame.getWidth()*lastFrame.getHeight()];
        }
        
        int pos = 0;
        while (frameData.remaining() > 0) {
            int red = (int)frameData.get() & 0xFF;
            int green = (int)frameData.get() & 0xFF;
            int blue = (int)frameData.get() & 0xFF;

            imagePixels[pos] = 0xFF000000 | (red << 16) | (green << 8) | blue;
            pos++;
        }
		
        repaint();
	}
	
	@Override
    public synchronized void paint(Graphics g) {
        if (lastFrame == null) {
            return;
        }
        
        int width = lastFrame.getWidth();
        int height = lastFrame.getHeight();

        // make sure we have enough room
        if (bufferedImage == null || bufferedImage.getWidth() != width || 
        				bufferedImage.getHeight() != height) {
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        }
        
        bufferedImage.setRGB(0, 0, width, height, imagePixels, 0, width);

        int framePosX = (getWidth() - width) / 2;
        int framePosY = (getHeight() - height) / 2;
        g.drawImage(bufferedImage, framePosX, framePosY, null);
    }
}
