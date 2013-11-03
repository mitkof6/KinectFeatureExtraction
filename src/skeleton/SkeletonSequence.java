package skeleton;

import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.media.opengl.GL2;

import main.Constant;

import com.jogamp.opengl.util.awt.TextRenderer;
import com.primesense.nite.JointType;
import com.primesense.nite.Skeleton;
import com.primesense.nite.SkeletonJoint;

/**
 * Sequence of skeletons
 *  
 * @author Jim Stanev
 *
 */
public class SkeletonSequence {
	
	private Vector<Pose> sequence;
	
	public SkeletonSequence() {
		sequence = new Vector<>();

	}
	
	public void add(Skeleton skeleton, long timeStamp){
		Pose newPose = new Pose(timeStamp);
		
		for(SkeletonJoint joint: skeleton.getJoints()){
			newPose.add(joint.getJointType(), joint);
		}
		sequence.add(newPose);
	}
	
	public Vector<Pose> getSequence(){
		return sequence;
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
		
		//text
		TextRenderer renderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 12));
		renderer.beginRendering(Constant.ANIMATOR_WIDTH, Constant.ANIMATOR_HEIGHT);
	    renderer.setColor(1.0f, 0f, 0f, 0.8f);
	    renderer.draw(String.format("SKEL: %d", sequence.lastElement().getTimeStamp()),
	    		10, Constant.ANIMATOR_HEIGHT-30) ;
	    renderer.endRendering();
		
		gl.glColor3f(1f, 0f, 0f);
		for(JointType[] type : Constant.JOINT_PAIRS){
			if(drawn.get(type[0])==null||
						drawn.get(type[1])==null) continue;
			gl.glBegin(GL2.GL_LINE_LOOP);
			
				gl.glVertex3d(
					drawn.get(type[0]).getPosition().getX()/Constant.POSITION_SCALING,
					drawn.get(type[0]).getPosition().getY()/Constant.POSITION_SCALING,
					drawn.get(type[0]).getPosition().getZ()/Constant.POSITION_SCALING);
			
				gl.glVertex3d(
					drawn.get(type[1]).getPosition().getX()/Constant.POSITION_SCALING,
					drawn.get(type[1]).getPosition().getY()/Constant.POSITION_SCALING,
					drawn.get(type[1]).getPosition().getZ()/Constant.POSITION_SCALING);
			gl.glEnd();	
		}
		
		gl.glPopMatrix();
	}
	
	/**
	 * Exports the sequence of poses to file
	 * 
	 * @param file path
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void export(String file) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter(file, "UTF-8");
		DecimalFormat df = new DecimalFormat("#.##");
		
		writer.println("FPS "+Constant.DEPTH_FPS);
		writer.println("SPF "+Constant.SAMPLES_PER_FRAME);
		
		//TODO hierarchy
		for(JointType type : Constant.JOINT_TYPES){
			writer.println("j "+type.toString());
		}
		
		for(int i = 0;i<sequence.size();i++){
			Pose pose = sequence.get(i);
			writer.println("t "+pose.getTimeStamp());
			for(JointType type : Constant.JOINT_TYPES){
				SkeletonJoint joint = pose.get(type);
				writer.println(
						type.toString()+" "+
						df.format(joint.getPosition().getX())+" "+
						df.format(joint.getPosition().getY())+" "+
						df.format(joint.getPosition().getZ())+" "+
						df.format(joint.getPositionConfidence())+" "+
						df.format(joint.getOrientation().getX())+" "+
						df.format(joint.getOrientation().getY())+" "+
						df.format(joint.getOrientation().getZ())+" "+
						df.format(joint.getOrientation().getW())+" "+
						df.format(joint.getOrientationConfidence()));
			}
		}
		
		writer.close();
	}
	
	/**
	 * Generates JOINT_TYPE.dat with (# X Y Z) mat for matlab to import
	 * 
	 * @param type the type of the joint to be generated
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void matlabExporter(JointType type) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter(type.toString()+".dat", "UTF-8");
		DecimalFormat df = new DecimalFormat("#.#");
		
		for(int i = 0;i<sequence.size();i++){
			Pose pose = sequence.get(i);

			SkeletonJoint joint = pose.get(type);
			writer.println(i+" "+
					df.format(joint.getPosition().getX())+"	"+
					df.format(joint.getPosition().getY())+" "+
					df.format(joint.getPosition().getZ()));
			
		}
		
		writer.close();
	}
	
}
