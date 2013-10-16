package skeleton;

import java.util.HashMap;

import com.primesense.nite.JointType;

public class Pose {

	private HashMap<JointType, Joint> joints;
	
	public Pose(){
		joints = new HashMap<JointType, Joint>();
	}
	
	public void add(JointType type, Joint joint){
		joints.put(type, joint);
	}
	
	public Joint get(JointType type){
		return joints.get(type);
	}
}
