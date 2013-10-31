package opengl;

import java.awt.Frame;
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
import math.geom3d.Vector3D;

import com.jogamp.opengl.util.FPSAnimator;

/**
 * OpenGL model viewer
 *
 * @author Jim Stanev
 */
public class Animator extends Frame implements GLEventListener {

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
	
	private Camera camera;
	
	private Floor floor;
	
	private Axis axis;

	/**
	 * Constructor
	 */
	public Animator(){

		super("Animator");
		//this.setBounds(200, 20, Constant.WINDOW_WIDTH, Constant.WINDOW_HEIGHT);
		this.setSize(Constant.ANIMATOR_WIDTH, Constant.ANIMATOR_HEIGHT);
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
		
		//camera
		camera = new Camera(
						new Vector3D(Constant.CAMERA_POS_X, Constant.CAMERA_POS_Y, Constant.CAMERA_POS_Z),
						new Vector3D(Constant.CAMERA_VIEW_X, Constant.CAMERA_VIEW_Y, Constant.CAMERA_VIEW_Z),
						new Vector3D(Constant.CAMERA_UP_X, Constant.CAMERA_UP_Y, Constant.CAMERA_UP_Z));
		
		//floor
		floor = new Floor(Constant.GRID_SIZE, Constant.GRID_LINE_WIDTH);
		
		//axis
		axis = new Axis(Constant.AXIS_LENGTH, Constant.AXIS_WIDTH);

		//canvas
		canvas = new GLCanvas(caps);
		canvas.addGLEventListener(this);
		canvas.addMouseMotionListener(camera);
		canvas.addKeyListener(camera);
		canvas.setFocusable(true);
		
		//frame
		this.add(canvas);

		//animator
		animator = new FPSAnimator(canvas, Constant.ANIMATOR_FPS);
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
		gl.glClearColor(0f, 0.4f, 0.9f, 0f);

		//nice perspective.
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
		
		//set prespective
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();

		// Perspective.
		float widthHeightRatio = (float) getWidth() / (float) getHeight();
		glu.gluPerspective(45, widthHeightRatio, 1, 1000);

		// Change back to model view matrix.
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		//light
		//float[] diffuse = {0.5f, 0.5f, 0.5f};
		//gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, Constant.LIGHT_POSITION, 0);
		gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {}

	@Override
	public void display(GLAutoDrawable drawable) {
		gl = drawable.getGL().getGL2();

		// clear screen.
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		
		gl.glLoadIdentity();

		//set camera
		camera.update(glu);
		
		//draw floor
		floor.draw(gl);
				
		//draw axis
		axis.draw(gl);
				
		//draw skeleton
		if(Constant.SKELETON_VISIBILITY){
			Main.kinect.userStream.sequence.draw(gl);
			Main.kinect.depthStream.sequence.draw(gl);
		}
		
		gl.glFlush();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
					int height) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glViewport(0, 0, width, height);
	}
	
}
