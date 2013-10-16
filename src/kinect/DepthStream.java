package kinect;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.openni.VideoFrameRef;
import org.openni.VideoStream;
import org.openni.VideoStream.NewFrameListener;

public class DepthStream extends Component implements NewFrameListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	float histogram[];
    int[] imagePixels;
    VideoStream videoStream;
    VideoFrameRef lastFrame;
    BufferedImage bufferedImage;
    
    public DepthStream(VideoStream videoStream){
		this.videoStream = videoStream;
		/*
		this.videoStream.setVideoMode(new VideoMode(
						Constant.COLOR_WIDTH, 
						Constant.COLOR_HEIGHT,
						Constant.COLOR_FPS,
						Constant.COLOR_PIXEL_FORMAT));
						*/
		this.videoStream.addNewFrameListener(this);
		
	}
	
	public void start(){
		if(videoStream!=null){
			this.videoStream.start();
		}
	}
	
	public void stop(){
		if(videoStream!=null){
			this.videoStream.stop();
		}
	}

	@Override
	public void onFrameReady(VideoStream arg0) {	
		lastFrame = videoStream.readFrame();
        ByteBuffer frameData = lastFrame.getData().order(ByteOrder.LITTLE_ENDIAN);
        //CoordinateConverter.convertDepthToWorld(videoStream, 0, 0, 0);
        // make sure we have enough room
        if (imagePixels == null || imagePixels.length < lastFrame.getWidth() * lastFrame.getHeight()) {
            imagePixels = new int[lastFrame.getWidth() * lastFrame.getHeight()];
        }
		calcHist(frameData);
        frameData.rewind();
        int pos = 0;

        while(frameData.remaining() > 0) {
            int depth = (int)frameData.getShort() & 0xFFFF;
            
            short pixel = (short)histogram[depth];
            imagePixels[pos] = 0xFF000000 | (pixel << 16) | (pixel << 8);
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
	
	private void calcHist(ByteBuffer depthBuffer) {
        // make sure we have enough room
        if (histogram == null || histogram.length < videoStream.getMaxPixelValue()) {
        	histogram = new float[videoStream.getMaxPixelValue()];
        }
        
        // reset
        for (int i = 0; i < histogram.length; ++i)
        	histogram[i] = 0;

        int points = 0;
        while (depthBuffer.remaining() > 0) {
            int depth = depthBuffer.getShort() & 0xFFFF;
            if (depth != 0) {
            	histogram[depth]++;
                points++;
            }
        }

        for (int i = 1; i < histogram.length; i++) {
        	histogram[i] += histogram[i - 1];
        }

        if (points > 0) {
            for (int i = 1; i < histogram.length; i++) {
            	histogram[i] = (int) (256 * (1.0f - (histogram[i] / (float) points)));
            }
        }
    }

}
