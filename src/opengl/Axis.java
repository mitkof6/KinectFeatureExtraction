package opengl;

import javax.media.opengl.GL2;

public class Axis {

	private int axisLength;
	private int axisWidth;
	
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
		gl.glColor3f(1.f, 0.f, 0.f);
		gl.glVertex3f(0.f, 0.f, 0.f);
		gl.glVertex3f(axisLength, 0.f, 0.f);

		gl.glColor3f(0.f, 1.f, 0.f);
		gl.glVertex3f(0.f, 0.f, 0.f);
		gl.glVertex3f(0.f, axisLength, 0.f);

		gl.glColor3f(0.f, 0.f, 1.f);
		gl.glVertex3f(0.f, 0.f, 0.f);
		gl.glVertex3f(0.f, 0.f, axisLength);
		gl.glEnd();

		gl.glPopMatrix();
	}
}
