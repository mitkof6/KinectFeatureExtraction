package kinect;

import java.nio.ByteBuffer;

import main.Constant;
import skeleton.SkeletonSequence;

import com.primesense.nite.SkeletonState;
import com.primesense.nite.UserData;
import com.primesense.nite.UserMap;
import com.primesense.nite.UserTracker;
import com.primesense.nite.UserTracker.NewFrameListener;
import com.primesense.nite.UserTrackerFrameRef;

/**
 * Handles user stream from kinect
 * 
 * @author Jim Stanev
 *
 */
public class UserStream implements  NewFrameListener{
	
	private UserTracker tracker;
    private UserTrackerFrameRef lastFrame;
    
    private int startSampling;
    private boolean detectFlor = false;
    
    /**
     * Skeleton sequence
     */
    public SkeletonSequence sequence;

    /**
     * Segmentation for user pixels only
     */
    public int[][] userPixel;
    
	public UserStream(UserTracker tracker){
		this.tracker = tracker;
		this.tracker.addNewFrameListener(this);
		
		sequence = new SkeletonSequence();
		
		userPixel = new int[Constant.DEPTH_HEIGHT][Constant.DEPTH_WIDTH];
		for(int i = 0;i<userPixel.length;i++){
			for(int j = 0;j<userPixel[0].length;j++){
				userPixel[i][j] = 0;
			}
		}
		
		startSampling = Constant.DEPTH_FPS/Constant.SAMPLES_PER_FRAME;
	}

	@Override
	public void onNewFrame(UserTracker arg0) {
		lastFrame = tracker.readFrame();
		
		if(!detectFlor&&lastFrame.getFloorConfidence()>Constant.FLOOR_CONFIDENCE){
			Constant.FLOOR_Y = 
				-lastFrame.getPlane().getPoint().getY().intValue()/Constant.JOINT_POSITION_SCALING;
			detectFlor = !detectFlor;
		}
		
        // check if any new user detected
        for (UserData user : lastFrame.getUsers()) {
        	if (user.isNew()) {
        		// start skeleton tracking
        		tracker.startSkeletonTracking(user.getId());
        		Constant.SKELETON_VISIBILITY = true;
        	}
        	if(user.isLost()){
        		tracker.stopSkeletonTracking(user.getId());
        		Constant.SKELETON_VISIBILITY = false;
        	}
        	if(!user.isVisible()){
        		Constant.SKELETON_VISIBILITY = false;
        	}
        	if(user.isVisible()){
        		Constant.SKELETON_VISIBILITY = true;
        		if (user.getSkeleton().getState() == SkeletonState.TRACKED) {//if tracked
        			startSampling--;
        			if(startSampling==0){
        				sequence.add(user.getSkeleton(), lastFrame.getTimestamp());
        				startSampling = Constant.DEPTH_FPS/Constant.SAMPLES_PER_FRAME;
        			}
            		getUserMap(user.getId());
            	}
        	}

        }
	}
	
	/**
	 * Get user pixels
	 * 
	 * @param id of user
	 */
	private void getUserMap(short id){
		UserMap userMap =  lastFrame.getUserMap();

		ByteBuffer frameData = userMap.getPixels();

		frameData.rewind();
		
		int width = 0,height = 0;
		while(frameData.remaining()>0){
			short depth = frameData.getShort();
			
			userPixel[height][width] = depth;
			
			filter(height, width);
			
			//System.out.println(depth);
			width++;
            if(width == Constant.DEPTH_WIDTH){
            	
            	width = 0;
            	height++;
            	if(height==Constant.DEPTH_HEIGHT) break;
            	
            }
		}
	}
	
	private void filter(int height, int width){
		if(height-Constant.POINT_CLOUD_SAMPLING>0&&
				width-Constant.POINT_CLOUD_SAMPLING>0){
			for(int i = Constant.POINT_CLOUD_SAMPLING;i>0;i--){
				if(userPixel[height-i][width]!=0||userPixel[height][width-i]!=0){
					userPixel[height][width] = 0;
				}
			}
		}
	}
}
