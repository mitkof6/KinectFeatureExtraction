package opengl;

import javax.media.opengl.GL2;

/**
 * Coordinate axis
 * 
 * @author Jim Stanev
 *
 */
public class Axis extends Drawable{

	private int axisLength;
	private int axisWidth;
	
	private float[] red = {1f, 0, 0};
	private float[] green = {0, 1f, 0};
	private float[] blue = {0, 0, 1f};
	
	public Axis(int axisLength, int axisWidth){
		this.axisLength = axisLength;
		this.axisWidth = axisWidth;
	}
	
	
	/**
	 * Draws some coordinates axis
	 *
	 * @param gl the gl object
	 */
	public void draw(GL2 gl) {
		gl.glPushMatrix();
			
		gl.glLineWidth(axisWidth);
		gl.glBegin(GL2.GL_LINES);
			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, red, 0);
			gl.glVertex3f(0.f, 0.f, 0.f);
			gl.glVertex3f(axisLength, 0.f, 0.f);

			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, green, 0);
			gl.glVertex3f(0.f, 0.f, 0.f);
			gl.glVertex3f(0.f, axisLength, 0.f);

			gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, blue, 0);
			gl.glVertex3f(0.f, 0.f, 0.f);
			gl.glVertex3f(0.f, 0.f, axisLength);
			gl.glEnd();

		gl.glPopMatrix();
	}
}
