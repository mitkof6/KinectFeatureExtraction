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
	
	public Pose(){
		joints = new HashMap<JointType, SkeletonJoint>();
	}
	
	public void add(JointType type, SkeletonJoint joint){
		joints.put(type, joint);
	}
	
	public SkeletonJoint get(JointType type){
		return joints.get(type);
	}
}
