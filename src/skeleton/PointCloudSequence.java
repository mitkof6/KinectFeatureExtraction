package skeleton;

import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.awt.TextRenderer;

import main.Constant;
import opengl.Drawable;

import org.openni.Point3D;

/**
 * Sequence of point clouds
 * 
 * @author Jim Stanev
 */
public class PointCloudSequence extends Drawable{

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
		
		//text
		TextRenderer renderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 12));
		renderer.beginRendering(Constant.ANIMATOR_WIDTH, Constant.ANIMATOR_HEIGHT);
	    renderer.setColor(0f, 0f, 0f, 0.8f);
	    renderer.draw(String.format("PC: %d", sequence.lastElement().getTimeStamp()), 10, Constant.ANIMATOR_HEIGHT-15) ;
	    renderer.endRendering();
		
		for(Point3D<Float> p : sequence.lastElement().getPointCloud()){
			gl.glBegin(GL2.GL_POINTS);
				gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, wellow, 0);
				gl.glVertex3d(
					p.getX()/Constant.POSITION_SCALING,
					p.getY()/Constant.POSITION_SCALING,
					p.getZ()/Constant.POSITION_SCALING);
			gl.glEnd();
			
		}
		
		gl.glPopMatrix();
	}
	
	/**
	 * Exports the sequence of point clouds to file
	 * 
	 * @param file path
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void export(String file) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter(file, "UTF-8");
		DecimalFormat df = new DecimalFormat("#.#");
		
		writer.println("FPS "+Constant.DEPTH_FPS);
		writer.println("SPF "+Constant.SAMPLES_PER_FRAME);
		
		for(int i = 0;i<sequence.size();i++){
			PointCloud pointCloud = sequence.get(i);
			writer.println("t "+pointCloud.getTimeStamp());
			
			for(Point3D<Float> p : pointCloud.getPointCloud()){
				writer.println(
						"p "+
						df.format(p.getX())+" "+
						df.format(p.getY())+" "+
						df.format(p.getZ()));
			}
		}
		writer.close();
	}
}
