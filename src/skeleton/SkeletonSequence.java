package skeleton;

import java.util.Vector;

import javax.media.opengl.GL2;

import main.Constant;

import com.primesense.nite.JointType;
import com.primesense.nite.Skeleton;
import com.primesense.nite.SkeletonJoint;

/**
 * Sequence of skeletons
 *  
 * @author Jim Staneb
 *
 */
public class SkeletonSequence {
	
	private Vector<Pose> sequence;
	
	public SkeletonSequence() {
		sequence = new Vector<>();

	}
	
	public void add(Skeleton skeleton){
		Pose newPose = new Pose();
		
		for(SkeletonJoint joint: skeleton.getJoints()){
			newPose.add(joint.getJointType(), joint);
		}
		sequence.add(newPose);
	}
	
	/**
	 * OpenGL draw function for the last pose
	 * 
	 * @param gl
	 */
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
							drawn.get(type[0]).getPosition().getX()/Constant.JOINT_POSITION_SCALING,
							drawn.get(type[0]).getPosition().getY()/Constant.JOINT_POSITION_SCALING,
							drawn.get(type[0]).getPosition().getZ()/Constant.JOINT_POSITION_SCALING);
			
				gl.glVertex3d(
							drawn.get(type[1]).getPosition().getX()/Constant.JOINT_POSITION_SCALING,
							drawn.get(type[1]).getPosition().getY()/Constant.JOINT_POSITION_SCALING,
							drawn.get(type[1]).getPosition().getZ()/Constant.JOINT_POSITION_SCALING);
			gl.glEnd();	
		}
		
		gl.glPopMatrix();
	}
	
}
