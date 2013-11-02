package main;

import org.openni.PixelFormat;

import com.primesense.nite.JointType;

/**
 * Contains constants
 * 
 * @author Jim Stanev
 *
 */
public class Constant {
	/**
	private static Properties prop;
	
	static{
		prop = new Properties();
		try {
			prop.load(new FileInputStream("config.properties"));
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
	}

	public static final String a = prop.getProperty("name");
	*/
	//DLL
	public static final String OPENNI2_DLL = "C:\\Program Files\\OpenNI2\\Tools\\OpenNI2.dll";
	public static final String NITE2_DLL = "C:\\Program Files\\PrimeSense\\NiTE2\\Redist\\NiTE2.dll";
	
	//Store
	public static final String POINT_CLOUD_FILE_NAME = "point-cloud.txt";
	public static final String SKELETON_FILE_NAME = "skeleton.txt";
	
	//Sampling
	public static final int SAMPLES_PER_FRAME = 15;
	public static final int POINT_CLOUD_SAMPLING = 10;
	
	//Color stream
	public static boolean START_COLOR = true;
	public static final int COLOR_WIDTH = 640; 
	public static final int COLOR_HEIGHT = 480;
	public static final int COLOR_FPS = 30;
	public static final int COLOR_PIXEL_FORMAT = PixelFormat.RGB888.toNative(); 
	
	//Depth stream
	public static final boolean START_DEPTH = true;
	public static final int DEPTH_WIDTH = 640;
	public static final int DEPTH_HEIGHT = 480;
	public static final int DEPTH_FPS = 30;
	public static final int DEPTH_PIXEL_FORMAT = PixelFormat.DEPTH_1_MM.toNative();
	
	//User stream
	public static final boolean START_USER = true;
	
	//Main window
	public static final int MAIN_WINDOW_WIDTH = 2*640;
	public static final int MAIN_WINDOW_HEIGHT = 480;
	
	//Animator window
	public static final boolean START_ANIMATOR = true;
	public static final int ANIMATOR_FPS =30;
	public static final int ANIMATOR_WIDTH = 640;
	public static final int ANIMATOR_HEIGHT = 480;
	
	//Confidence
	public static final float FLOOR_CONFIDENCE = .8f;
	public static final float JOINT_CONFIDENCE = .6f;
	
	//Joints
	public static final JointType[][] JOINT_PAIRS = {
		{JointType.HEAD, JointType.NECK},
		
		{JointType.LEFT_SHOULDER, JointType.LEFT_ELBOW},
		{JointType.LEFT_ELBOW, JointType.LEFT_HAND},
		
		{JointType.RIGHT_SHOULDER, JointType.RIGHT_ELBOW},
		{JointType.RIGHT_ELBOW, JointType.RIGHT_HAND},
		
		{JointType.LEFT_SHOULDER, JointType.RIGHT_SHOULDER},
		
		{JointType.LEFT_SHOULDER, JointType.TORSO},
		{JointType.RIGHT_SHOULDER, JointType.TORSO},
		
		{JointType.LEFT_HIP, JointType.TORSO},
		{JointType.RIGHT_HIP, JointType.TORSO},
		{JointType.LEFT_HIP, JointType.RIGHT_HIP},
		
		{JointType.LEFT_HIP, JointType.LEFT_KNEE},
		{JointType.LEFT_KNEE, JointType.LEFT_FOOT},
		
		{JointType.RIGHT_HIP, JointType.RIGHT_KNEE},
		{JointType.RIGHT_KNEE, JointType.RIGHT_FOOT}};
	
	public static final JointType[] JOINT_TYPES = {
		JointType.HEAD,
		JointType.NECK,
		JointType.LEFT_SHOULDER,
		JointType.LEFT_ELBOW,
		JointType.LEFT_HAND,
		JointType.RIGHT_SHOULDER,
		JointType.RIGHT_ELBOW,
		JointType.RIGHT_HAND,
		JointType.TORSO,
		JointType.LEFT_HIP, 
		JointType.LEFT_KNEE,
		JointType.LEFT_FOOT,
		JointType.RIGHT_HIP, 
		JointType.RIGHT_KNEE,
		JointType.RIGHT_FOOT
	};
	
	//Animation parameters
	public static final int JOINT_POSITION_SCALING = 100;
	
	public static final int GRID_SIZE = 100;
	public static final int GRID_LINE_WIDTH = 2;
	
	
	public static final int AXIS_LENGTH = 10;
	public static final int AXIS_WIDTH = 3;
	public static int FLOOR_Y = 0;
	
	public static boolean SKELETON_VISIBILITY = false;
	
	public static final double CAMERA_POS_X = 0;
	public static final double CAMERA_POS_Y = 2;
	public static final double CAMERA_POS_Z = +2;
	
	public static final double CAMERA_VIEW_X = 0;
	public static final double CAMERA_VIEW_Y = 2;
	public static final double CAMERA_VIEW_Z = 10;
	
	public static final double CAMERA_UP_X = 0;
	public static final double CAMERA_UP_Y = 1;
	public static final double CAMERA_UP_Z = 0;
	
	public static final int CAMERA_MOVE_SPEED = 10;
	public static final double CAMERA_ROTATE_SPEED = 0.03;
	
	public static final float[] LIGHT_POSITION = {100f, 200f, 10f};
}
