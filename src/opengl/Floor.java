package opengl;

import javax.media.opengl.GL2;

import main.Constant;

public class Floor {
	
	private int gridSize;
	private int gridWidth;
	
	public Floor(int gridSize, int gridWidth){
		this.gridSize = gridSize;
		this.gridWidth = gridWidth;
	}

	/**
	 * Draws grid
	 * @param gl the gl object
	 */
	public void draw(GL2 gl) {
		gl.glPushMatrix();
		
		gl.glColor3f(1f, 1f, 1f);
		gl.glLineWidth(gridWidth);
		gl.glBegin(GL2.GL_LINES);
		for (int i = -gridSize; i < gridSize; i++) {
			gl.glVertex3f(i, -Constant.FLOOR_Y, -Constant.GRID_SIZE);
			gl.glVertex3f(i, -Constant.FLOOR_Y, Constant.GRID_SIZE);

			gl.glVertex3f(gridSize, -Constant.FLOOR_Y, i);
			gl.glVertex3f(-gridSize, -Constant.FLOOR_Y, i);
		}
		gl.glEnd();
		
		gl.glPopMatrix();

	}
}
