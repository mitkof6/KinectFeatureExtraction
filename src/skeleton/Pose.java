package skeleton;

import java.util.HashMap;

import com.primesense.nite.JointType;
import com.primesense.nite.SkeletonJoint;

/**
 * Skeleton pose data
 * 
 * @author Jim Stanev
 *
 */
public class Pose {

	private HashMap<JointType, SkeletonJoint> joints;
	private long timeStamp;
	
	public Pose(long timeStamp){
		joints = new HashMap<JointType, SkeletonJoint>();
		this.timeStamp = timeStamp;
	}
	
	public void add(JointType type, SkeletonJoint joint){
		joints.put(type, joint);
	}
	
	public SkeletonJoint get(JointType type){
		return joints.get(type);
	}
	
	public long getTimeStamp(){
		return timeStamp;
	}
}
