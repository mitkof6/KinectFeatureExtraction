package gui;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import main.Constant;
import main.Main;


import com.jogamp.opengl.util.FPSAnimator;

/**
 * The class for viewing the animation and interaction between
 * bone system and skin
 *
 * @author Jim Stanev
 */
public class Animator extends Frame implements GLEventListener, KeyListener,
MouseListener, MouseMotionListener, MouseWheelListener{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private GLProfile glp;
	private GLCapabilities caps;
	private GLCanvas canvas;
	private FPSAnimator animator;
	private GLU glu = new GLU();
	private GL2 gl;

	/**
	 * animation parameters
	 * kayFrameIndex: indicates the current animation frame used for recording a key frame
	 * FPS: frames per second
	 * time: used to indicate the animation frame during animation
	 */


	/**
	 * camera movement parameters
	 */
	private float CAMERA_ZOOM = -50;
	private float CAMERA_X = 50;
	private float CAMERA_Y = 50;
	private int lastX = 0;
	private int lastY = 0;


	/**
	 * Constructor
	 */
	public Animator(){

		super("Animator");
		//this.setBounds(200, 20, Constant.WINDOW_WIDTH, Constant.WINDOW_HEIGHT);
		this.setSize(Constant.COLOR_WIDTH, Constant.COLOR_HEIGHT);
		//dispose
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				canvas.destroy();
				animator.stop();
				setVisible(false);
			}
		});

	


		//profile init
		glp = GLProfile.getDefault();
		GLProfile.initSingleton();
		caps = new GLCapabilities(glp);

		//canvas
		canvas = new GLCanvas(caps);
		canvas.addGLEventListener(this);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addMouseWheelListener(this);
		canvas.addKeyListener(this);
		canvas.setFocusable(true);

		//frame
		this.add(canvas);

		//animator
		animator = new FPSAnimator(canvas, Constant.COLOR_FPS);
		animator.add(canvas);
		animator.start();
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		gl = drawable.getGL().getGL2();

		//enable z- (depth) buffer for hidden surface removal.
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2.GL_LEQUAL);

		//enable smooth shading.
		gl.glShadeModel(GL2.GL_SMOOTH);

		//define "clear" color
		gl.glClearColor(0f, 0f, 0f, 0f);

		//nice perspective.
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {}

	@Override
	public void display(GLAutoDrawable drawable) {
		render(drawable);
		
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
					int height) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glViewport(0, 0, width, height);

	}

	/**
	 * Render method
	 *
	 * @param drawable the drawable object
	 */
	private void render(GLAutoDrawable drawable) {

		gl = drawable.getGL().getGL2();

		// clear screen.
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		//set camera
		setCamera(gl, CAMERA_X, CAMERA_Y, CAMERA_ZOOM);

		//draw skeleton
		if(Constant.SKELETON_VISIBILITY){
			Main.kinect.userStream.sequence.draw(gl);
		}
		
		drawAxis(gl);
		drawGrid(gl);

		gl.glFlush();
	}
	
	/**
	 * Draws some coordinates axis
	 *
	 * @param gl the gl object
	 */
	private void drawAxis(GL2 gl) {

		gl.glLineWidth(4.f);
		gl.glBegin(GL2.GL_LINES);
		gl.glColor3f(1.f, 0.f, 0.f);
		gl.glVertex3f(0.f, 0.f, 0.f);
		gl.glVertex3f(10.f, 0.f, 0.f);

		gl.glColor3f(0.f, 1.f, 0.f);
		gl.glVertex3f(0.f, 0.f, 0.f);
		gl.glVertex3f(0.f, 10.f, 0.f);

		gl.glColor3f(0.f, 0.f, 1.f);
		gl.glVertex3f(0.f, 0.f, 0.f);
		gl.glVertex3f(0.f, 0.f, 10.f);
		gl.glEnd();

	}

	/**
	 * Draws grid
	 *
	 * @param size of the grid
	 * @param gl the gl object
	 */
	private void drawGrid(GL2 gl) {

		gl.glColor3f(0.5f, 0.5f, 0.5f);
		gl.glLineWidth(1.f);
		gl.glBegin(GL2.GL_LINES);
		for (int i = -Constant.GRID_SIZE; i < Constant.GRID_SIZE; i++) {
			gl.glVertex3f(i, -Constant.FLOOR_Y, -Constant.GRID_SIZE);
			gl.glVertex3f(i, -Constant.FLOOR_Y, Constant.GRID_SIZE);

			gl.glVertex3f(Constant.GRID_SIZE, -Constant.FLOOR_Y, i);
			gl.glVertex3f(-Constant.GRID_SIZE, -Constant.FLOOR_Y, i);
		}
		gl.glEnd();

	}

	/**
	 * Camera position setter
	 *
	 * @param gl the gl object
	 * @param cX x coordinate of the camera
	 * @param cY y coordinate of the camera
	 * @param cZ z coordinate of the camera
	 */
	private void setCamera(GL2 gl, double cX, double cY, double cZ) {
		// Change to projection matrix.
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();

		// Perspective.
		float widthHeightRatio = (float) getWidth() / (float) getHeight();
		glu.gluPerspective(45, widthHeightRatio, 1, 1000);
		glu.gluLookAt(cX, cY, cZ, 0, 1, 0, 0, 1, 0);

		// Change back to model view matrix.
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_N){
			
		}else if(e.getKeyCode()==KeyEvent.VK_UP){
			
		}else if(e.getKeyCode()==KeyEvent.VK_DOWN){
			
		}else if(e.getKeyCode()==KeyEvent.VK_R){
			
		}else if(e.getKeyCode()==KeyEvent.VK_A){
			
		}

	}

	@Override
	public void mouseDragged(MouseEvent e) {

		int diffx = e.getX() - lastX;
		int diffy = e.getY() - lastY;

		lastX = e.getX();
		lastY = e.getY();

		if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) == MouseEvent.BUTTON1_MASK) {
			CAMERA_X += 0.05f * diffx;
			CAMERA_Y -= 0.05f * diffy;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		lastX = e.getX();
		lastY = e.getY();
	}


	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getWheelRotation()>0){
			CAMERA_ZOOM += 10;
		}else if(e.getWheelRotation()<0){
			CAMERA_ZOOM -= 10;
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

}



