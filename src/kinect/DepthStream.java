package kinect;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import main.Constant;
import main.Main;
import opencv.MatViewer;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.openni.CoordinateConverter;
import org.openni.VideoFrameRef;
import org.openni.VideoMode;
import org.openni.VideoStream;
import org.openni.VideoStream.NewFrameListener;
import org.openni.Point3D;

import skeleton.PointCloud;
import skeleton.PointCloudSequence;

/**
 * Handles depth data from kinect
 * 
 * @author Jim Stanev
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
	
	private int startSampling;
	
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
		
		startSampling = Constant.DEPTH_FPS/Constant.SAMPLES_PER_FRAME;
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
		startSampling--;//reset sampling
		
		lastFrame = videoStream.readFrame();
		
		PointCloud pointCloud = new PointCloud(lastFrame.getTimestamp());
		
        ByteBuffer frameData = lastFrame.getData().order(ByteOrder.LITTLE_ENDIAN);
        
        frameData.rewind();
        
        int width = 0,height = 0;
        while(frameData.remaining() > 0) {
            int depth = (int)frameData.getShort();
            
            depthMat.put(height, width, depth);
            
            //add point to point cloud
            if(Main.kinect.userStream!=null&&
            		startSampling==0&&
            		Main.kinect.userStream.userPixel[height][width]!=0){

            	pointCloud.add(CoordinateConverter.convertDepthToWorld(videoStream, width, height, depth));
            	//pointCloud.add(getWorldCordinate(width, height, depth));
            	
            }//TODO Chose method

            width++;
            if(width == Constant.DEPTH_WIDTH){
            	width = 0;
            	height++;
            }
        }
        
        //add point cloud to point cloud sequence
        if(pointCloud.getPointCloud().size()!=0){
        	 sequence.add(pointCloud); 
        	 //System.out.println(pointCloud.getPointCloud().size());
        }
        
        //reset sampling
        if(startSampling==0){
        	startSampling = Constant.DEPTH_FPS/Constant.SAMPLES_PER_FRAME;
        }

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
	@SuppressWarnings("unused")
	private Point3D<Float> getWorldCordinate(int imageX, int imageY, int depth){
		float x, y;
		/*
		x = depth/Constant.DEPTH_WIDTH-.5f;
		y = depth/Constant.DEPTH_HEIGHT+.5f;
		
		return new Point3D(x*depth*Math.tan(hFOV/2)*2, y*depth*Math.tan(vFOV/2)*2, depth);
		*/
		x = (float) ((imageX-320)*depth/5.94e+02+35);
		y = (float) ((imageY-240)*depth/5.92e+02);
		//System.out.println(x+" "+y+" "+depth);
		return new Point3D<Float>(x, -y, (float)depth);
	}
	

}
