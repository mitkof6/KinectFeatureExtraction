package kinect;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import main.Constant;
import main.Main;
import math.geom3d.Point3D;
import opencv.MatViewer;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.openni.VideoFrameRef;
import org.openni.VideoMode;
import org.openni.VideoStream;
import org.openni.VideoStream.NewFrameListener;

import skeleton.PointCloud;
import skeleton.PointCloudSequence;

/**
 * Handles depth data from kinect
 * 
 * @author Jim Staneb
 *
 */
public class DepthStream implements NewFrameListener{
	
	private VideoStream videoStream;
	private VideoFrameRef lastFrame;
	private Mat depthMat;
	/**
	 * Component for opencv mat viewer
	 */
	public MatViewer viwer;
	/**
	 * Point cloud sequence registered
	 */
	public PointCloudSequence sequence;
	
    private boolean start = false;
    
    public DepthStream(VideoStream videoStream){
    	
		this.videoStream = videoStream;
		
		this.videoStream.setVideoMode(new VideoMode(
						Constant.DEPTH_WIDTH, 
						Constant.DEPTH_HEIGHT,
						Constant.DEPTH_FPS,
						Constant.DEPTH_PIXEL_FORMAT));
			
		this.videoStream.addNewFrameListener(this);
		
		depthMat = new Mat(Constant.DEPTH_HEIGHT, Constant.DEPTH_WIDTH, CvType.CV_16UC1);
		viwer = new MatViewer();
		
		sequence = new PointCloudSequence();
		
		
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
		PointCloud pointCloud = new PointCloud();
		lastFrame = videoStream.readFrame();
		
        ByteBuffer frameData = lastFrame.getData().order(ByteOrder.LITTLE_ENDIAN);
        
        frameData.rewind();
        
        int width = 0,height = 0;
        while(frameData.remaining() > 0) {
            int depth = (int)frameData.getShort();
            
            depthMat.put(height, width, depth);
            
            if(Main.kinect.userStream!=null&&Main.kinect.userStream.userPixel[height][width]!=0){
            	pointCloud.add(getWorldCordinate(width, height, depth));
            }//TODO

            width++;
            if(width == Constant.DEPTH_WIDTH){
            	width = 0;
            	height++;
            }
        }
        
        sequence.add(pointCloud);
        viwer.update(depthMat, ".png");
	}
	
	/**
	 * Converts from image system to world 3D coordinates
	 * 
	 * @param imageX image x pixel
	 * @param imageY image y pixel
	 * @param depth depth value mm
	 * @return
	 */
	private Point3D getWorldCordinate(int imageX, int imageY, int depth){
		float x, y;
		/*
		x = depth/Constant.DEPTH_WIDTH-.5f;
		y = depth/Constant.DEPTH_HEIGHT+.5f;
		
		return new Point3D(x*depth*Math.tan(hFOV/2)*2, y*depth*Math.tan(vFOV/2)*2, depth);
		*/
		x = (float) ((imageX-320)*depth/5.9425464969100040e+02+10);
		y = (float) ((imageY-240)*depth/5.9248479436384002e+02);
		//System.out.println(x+" "+y+" "+depth);
		return new Point3D(x, -y, depth);
	}
	

}
