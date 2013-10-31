package skeleton;

import java.util.Vector;

import javax.media.opengl.GL2;

import main.Constant;
import math.geom3d.Point3D;

/**
 * Sequence of point clouds
 * 
 * @author Jim Stanev
 */
public class PointCloudSequence {

	private Vector<PointCloud> sequence;
	
	public PointCloudSequence(){
		sequence = new Vector<>();
	}
	
	public void add(PointCloud s){
		sequence.add(s);
	}
	
	public Vector<PointCloud> getSequence(){
		return sequence;
	}
	
	/**
	 * OpenGL draw function for the last point cloud
	 * 
	 * @param gl GL2
	 */
	public void draw(GL2 gl){
		
		if(sequence.isEmpty()) return;
		
		gl.glPushMatrix();
		
		float[] wellow = {1f, 1f, 0};
		
		for(Point3D p : sequence.lastElement().getPointCloud()){
			
			gl.glBegin(GL2.GL_POINTS);
				gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, wellow, 0);
				gl.glVertex3d(
					p.getX()/Constant.JOINT_POSITION_SCALING,
					p.getY()/Constant.JOINT_POSITION_SCALING,
					p.getZ()/Constant.JOINT_POSITION_SCALING);
			gl.glEnd();
			
		}
		
		gl.glPopMatrix();
	}
}
