package skeleton;

import java.util.Vector;

import javax.media.opengl.GL2;

import main.Constant;

import com.primesense.nite.JointType;
import com.primesense.nite.Skeleton;
import com.primesense.nite.SkeletonJoint;

public class SkeletonSequence {
	
	private Vector<Pose> sequence;
	private SkeletonSmooth smooth;
	
	public SkeletonSequence() {
		sequence = new Vector<>();
		smooth = new SkeletonSmooth();

	}
	
	public void add(Skeleton skeleton){
		Pose newPose = new Pose();
		
		for(SkeletonJoint joint: skeleton.getJoints()){
			newPose.add(joint.getJointType(), smooth(joint));
		}
		sequence.add(newPose);
	}
	
	public void draw(GL2 gl){
		if(sequence.isEmpty()) return;
		Pose drawn = sequence.lastElement();

		gl.glPushMatrix();
		
		gl.glColor3f(1f, 0f, 0f);
		for(JointType[] type : Constant.JOINT_PAIRS){
			if(drawn.get(type[0])==null||
						drawn.get(type[1])==null) continue;
			gl.glBegin(GL2.GL_LINE_LOOP);
			
			gl.glVertex3d(
							drawn.get(type[0]).position.getX()/Constant.JOINT_POSITION_SCALING,
							drawn.get(type[0]).position.getY()/Constant.JOINT_POSITION_SCALING,
							drawn.get(type[0]).position.getZ()/Constant.JOINT_POSITION_SCALING);
			
			gl.glVertex3d(
							drawn.get(type[1]).position.getX()/Constant.JOINT_POSITION_SCALING,
							drawn.get(type[1]).position.getY()/Constant.JOINT_POSITION_SCALING,
							drawn.get(type[1]).position.getZ()/Constant.JOINT_POSITION_SCALING);
			gl.glEnd();	
		}
		
		gl.glPopMatrix();
	}
	
	private Joint smooth(SkeletonJoint joint){
		smooth.updateMemory(joint);
		return smooth.getPosition(joint.getJointType());
	}
}
