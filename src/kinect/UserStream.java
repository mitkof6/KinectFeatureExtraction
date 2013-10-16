package kinect;

import main.Constant;
import skeleton.SkeletonSequence;

import com.primesense.nite.SkeletonState;
import com.primesense.nite.UserData;
import com.primesense.nite.UserTracker;
import com.primesense.nite.UserTracker.NewFrameListener;
import com.primesense.nite.UserTrackerFrameRef;

public class UserStream implements  NewFrameListener{
	
	private UserTracker tracker;
    private UserTrackerFrameRef lastFrame;
    
    private boolean detectFlor = false;
    
    public SkeletonSequence sequence;

	public UserStream(UserTracker tracker){
		this.tracker = tracker;
		this.tracker.addNewFrameListener(this);
		//this.tracker.setSkeletonSmoothingFactor(0.5f);
		
		sequence = new SkeletonSequence();
		
	}

	@Override
	public void onNewFrame(UserTracker arg0) {
		lastFrame = tracker.readFrame();
		if(!detectFlor&&lastFrame.getFloorConfidence()>Constant.FLOOR_CONFIDENCE){
			Constant.FLOOR_Y = -lastFrame.getPlane().getPoint().getY().intValue()/Constant.JOINT_POSITION_SCALING;
			detectFlor = !detectFlor;
		}
        // check if any new user detected
        for (UserData user : lastFrame.getUsers()) {
        	if (user.isNew()) {
        		// start skeleton tracking
        		tracker.startSkeletonTracking(user.getId());
        		Constant.SKELETON_VISIBILITY = true;
        		//System.out.println("New User");
        	}
        	if(user.isLost()){
        		tracker.stopSkeletonTracking(user.getId());
        		Constant.SKELETON_VISIBILITY = false;
        		//System.out.println("User Lost");
        	}
        	if(!user.isVisible()){
        		Constant.SKELETON_VISIBILITY = false;
        		//System.out.println("User Not Visible");
        	}
        	if(user.isVisible()){
        		Constant.SKELETON_VISIBILITY = true;
        		//System.out.println("User Visible");
        		if (user.getSkeleton().getState() == SkeletonState.TRACKED) {
            		sequence.add(user.getSkeleton());
            		//System.out.println("Tracked");
            	}
        	}
        	
        	
        }
	}

}
