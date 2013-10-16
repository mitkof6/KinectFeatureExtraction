package main;

import com.primesense.nite.JointType;


public class Constant {

	public static final String OPENNI2_DLL = "C:\\Program Files\\OpenNI2\\Tools\\OpenNI2.dll";
	public static final String NITE2_DLL = "C:\\Program Files\\PrimeSense\\NiTE2\\Redist\\NiTE2.dll";
	
	public static int COLOR_WIDTH = 640; 
	public static int COLOR_HEIGHT = 480;
	public static int COLOR_FPS = 30;
	public static int COLOR_PIXEL_FORMAT = 5; 
	
	public static int MAIN_WINDOW_WIDTH = 2*640;
	public static int MAIN_WINDOW_HEIGHT = 480;
	
	public static float FLOOR_CONFIDENCE = .8f;
	
	public static int JOINT_SMOOTH_MEMORY = 30;
	public static float JOINT_CONFIDENCE = .6f;
	
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
	
	public static JointType[] JOINT_TYPES = {
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
	
	public static int JOINT_POSITION_SCALING = 100;
	
	public static int GRID_SIZE = 100;
	public static int FLOOR_Y = 0;
	
	public static boolean SKELETON_VISIBILITY = false;
						
	
}
