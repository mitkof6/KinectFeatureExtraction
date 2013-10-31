package opengl;

import javax.media.opengl.GL2;

import main.Constant;

/**
 * Floor
 * 
 * @author Jim Stanev
 *
 */
public class Floor {
	
	private int gridSize;
	private int gridLineWidth;

	private float[] normal = {0f, 1f, 0f};
	private float[] white = {1f , 1f, 1f};
	private float[] black = {0f, 0f, 0f};
	
	public Floor(int gridSize, int gridLineWidth){
		this.gridSize = gridSize;
		this.gridLineWidth = gridLineWidth;
	}

	/**
	 * Draws grid
	 * @param gl the gl object
	 */
	public void draw(GL2 gl) {
		gl.glPushMatrix();
		
		gl.glLineWidth(gridLineWidth);
		
		boolean flag = true;
		
		for (int i = -gridSize; i <= gridSize; i++) {
            for (int j = -gridSize; j <= gridSize; j++) {
                if (flag) {
                    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, white, 0);
                } else {
                    gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, black, 0);
                }
                flag = !flag;
                gl.glBegin(GL2.GL_QUADS);

                	gl.glNormal3fv(normal, 0);
                	gl.glVertex3d(i, -Constant.FLOOR_Y, j);
                	gl.glNormal3fv(normal, 0);
                	gl.glVertex3d(i, -Constant.FLOOR_Y, j - 1);
                	gl.glNormal3fv(normal, 0);
                	gl.glVertex3d(i - 1,-Constant.FLOOR_Y, j - 1);
                	gl.glNormal3fv(normal, 0);
                	gl.glVertex3d(i - 1, -Constant.FLOOR_Y, j);
                gl.glEnd();
            }
        }
		
		gl.glPopMatrix();

	}
}
