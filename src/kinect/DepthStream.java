package kinect;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Vector;


import main.Constant;
import math.geom3d.Point3D;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.openni.CoordinateConverter;
import org.openni.VideoFrameRef;
import org.openni.VideoMode;
import org.openni.VideoStream;
import org.openni.VideoStream.NewFrameListener;

public class DepthStream implements NewFrameListener{
	
	private float histogram[];
	private VideoStream videoStream;
	private VideoFrameRef lastFrame;
	public MatViewer viwer;
	Mat depthSrc, depthDist;
	public Vector<Point3D> pointCloud = new Vector<>();
	
    boolean first = false;
    
    public DepthStream(VideoStream videoStream){
    	
		this.videoStream = videoStream;
		
		this.videoStream.setVideoMode(new VideoMode(
						Constant.DEPTH_WIDTH, 
						Constant.DEPTH_HEIGHT,
						Constant.DEPTH_FPS,
						Constant.DEPTH_PIXEL_FORMAT));
			
		this.videoStream.addNewFrameListener(this);
		//this.videoStream.setMirroringEnabled(true);
		
		depthSrc = new Mat(Constant.DEPTH_HEIGHT, Constant.DEPTH_WIDTH, CvType.CV_16UC1);
		depthDist = new Mat(Constant.DEPTH_HEIGHT, Constant.DEPTH_WIDTH, CvType.CV_8UC1);
		viwer = new MatViewer();
		
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
        
		calcHist(frameData);
        frameData.rewind();
       
        int width = 0,height = 0;
        while(frameData.remaining() > 0) {
            int depth = (int)frameData.getShort() & 0xFFFF;
            //System.out.println(depth);
            short pixel = (short)histogram[depth];
            
            depthSrc.put(height, width, pixel);
            if(first){
            		org.openni.Point3D<Float> point = CoordinateConverter.convertDepthToWorld(videoStream, width, height, pixel);
                    pointCloud.add(new Point3D(point.getX(), point.getY(), point.getZ()));
            }
            
            width++;
            if(width == Constant.DEPTH_WIDTH){
            	width = 0;
            	height++;
            }
        }
        
        first = false;
        Core.normalize(depthSrc, depthDist, 0, 255, Core.NORM_MINMAX, CvType.CV_8UC1);
        //Imgproc.blur(depthSrc, depthDist, new Size(9, 9));
        //Imgproc.Laplacian(depthDist, depthDist, -1);
        viwer.update(depthDist, ".png");
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
