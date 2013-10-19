package skeleton;

import java.util.HashMap;
import java.util.Vector;

import main.Constant;
import math.geom3d.Point3D;

import com.primesense.nite.JointType;
import com.primesense.nite.SkeletonJoint;

public class SkeletonSmooth {
	
	private HashMap<JointType, Vector<Point3D>> memory;
	
	public SkeletonSmooth() {
		memory = new HashMap<>();
		for(JointType type : Constant.JOINT_TYPES){
			memory.put(type, new Vector<Point3D>());
		}
	}
	
	public void updateMemory(SkeletonJoint joint){
		if(joint.getPositionConfidence()>Constant.JOINT_CONFIDENCE){
			if(memory.get(joint.getJointType()).size()==Constant.JOINT_SMOOTH_MEMORY){
				memory.get(joint.getJointType()).remove(0);
			}
			memory.get(joint.getJointType()).add(new Point3D(
							joint.getPosition().getX(),
							joint.getPosition().getY(),
							joint.getPosition().getZ()));
		}else{
			if(!memory.get(joint.getJointType()).isEmpty()){
				memory.get(joint.getJointType()).remove(0);
			}
			
		}
	}
	
	public Joint getPosition(JointType type){
		if(memory.get(type).isEmpty()){
			return null;
		}else{
			float x = 0,  y = 0, z = 0;
			int count = 0;
			for(Point3D p : memory.get(type)){
				x += p.getX();
				y += p.getY();
				z += p.getZ();
				count++;
			}
			return new Joint(x/count, y/count, z/count);
		}
	}

}
