package main;

import org.openni.PixelFormat;

import com.primesense.nite.JointType;


public class Constant {

	public static final String OPENNI2_DLL = "C:\\Program Files\\OpenNI2\\Tools\\OpenNI2.dll";
	public static final String NITE2_DLL = "C:\\Program Files\\PrimeSense\\NiTE2\\Redist\\NiTE2.dll";
	
	public static int COLOR_WIDTH = 640; 
	public static int COLOR_HEIGHT = 480;
	public static int COLOR_FPS = 30;
	public static int COLOR_PIXEL_FORMAT = PixelFormat.RGB888.toNative(); 
	
	public static int DEPTH_WIDTH = 640;
	public static int DEPTH_HEIGHT = 480;
	public static int DEPTH_FPS = 30;
	public static int DEPTH_PIXEL_FORMAT = PixelFormat.DEPTH_1_MM.toNative();
	
	public static int MAIN_WINDOW_WIDTH = 2*640;
	public static int MAIN_WINDOW_HEIGHT = 480;
	
	public static int ANIMATOR_FPS =30;
	public static int ANIMATOR_WIDTH = 640;
	public static int ANIMATOR_HEIGHT = 480;
	
	public static float FLOOR_CONFIDENCE = .8f;
	
	public static int JOINT_SMOOTH_MEMORY = 10;
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
	public static int GRID_LINE_WIDTH = 2;
	
	
	public static int AXIS_LENGTH = 10;
	public static int AXIS_WIDTH = 3;
	public static int FLOOR_Y = 0;
	
	public static boolean SKELETON_VISIBILITY = false;
	
	public static double CAMERA_POS_X = 0;
	public static double CAMERA_POS_Y = 0;
	public static double CAMERA_POS_Z = -20;
	
	public static double CAMERA_VIEW_X = 0;
	public static double CAMERA_VIEW_Y = 0;
	public static double CAMERA_VIEW_Z = 0;
	
	public static double CAMERA_UP_X = 0;
	public static double CAMERA_UP_Y = 1;
	public static double CAMERA_UP_Z = 0;
	
	public static int CAMERA_MOVE_SPEED = 10;
	public static double CAMERA_ROTATE_SPEED = 0.06;
	
	public static float[] LIGHT_POSITION = {100f, 200f, 10f};
	
						
	
}
