package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

import opengl.Animator;
import opengl.Axis;
import opengl.Grid;

import org.opencv.core.Core;

import gui.MainWindow;
import kinect.Kinect;

/**
 * Starts the application
 * 
 * @author Jim Stanev
 *
 */
public class Main {

	/**
	 * Kinect device
	 */
	public static Kinect kinect;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//properties
		initializeProperties();
		
		//library
		initializeLibrary();
		
		//kinect
		kinect = new Kinect();
		if(Constant.START_COLOR_STREAM) kinect.openRGB();
		if(Constant.START_DEPTH_STREAM) kinect.openDepth();
		if(Constant.START_USER_STREAM) kinect.openUserTracker();
		
		
		//gui
		MainWindow mainWindow = new MainWindow();
		mainWindow.setVisible(true);
		
		//animator
		if(Constant.START_ANIMATOR){
			Animator animator = new Animator();
			animator.addToDraw(new Grid(Constant.GRID_SIZE, Constant.GRID_LINE_WIDTH));
			animator.addToDraw(new Axis(Constant.AXIS_LENGTH, Constant.AXIS_WIDTH));
			animator.addToDraw(kinect.userStream.sequence);
			animator.addToDraw(kinect.depthStream.sequence);
			animator.start();
			animator.setVisible(true);
		}
		

	}
	
	private static void initializeProperties(){
		
		Properties prop = new Properties();

		try {
			prop.load(new FileInputStream("config.properties"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, 
					"Can't find config.properties file", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		Constant.OPENNI2_DLL = prop.getProperty("OPENNI2_DLL");
		Constant.NITE2_DLL = prop.getProperty("NITE2_DLL");
		
		Constant.POINT_CLOUD_FILE_NAME = prop.getProperty("POINT_CLOUD_FILE_NAME");
		Constant.SKELETON_FILE_NAME = prop.getProperty("SKELETON_FILE_NAME");
		
		Constant.SAMPLES_PER_FRAME = Integer.parseInt(prop.getProperty("SAMPLES_PER_FRAME"));
		Constant.POINT_CLOUD_DENSITY = Integer.parseInt(prop.getProperty("POINT_CLOUD_DENSITY"));
		
		Constant.START_COLOR_STREAM = Boolean.parseBoolean(prop.getProperty("START_COLOR_STREAM"));
		Constant.START_DEPTH_STREAM = Boolean.parseBoolean(prop.getProperty("START_DEPTH_STREAM"));
		Constant.START_USER_STREAM = Boolean.parseBoolean(prop.getProperty("START_USER_STREAM"));
		
		Constant.START_ANIMATOR = Boolean.parseBoolean(prop.getProperty("START_ANIMATOR"));
		
		Constant.MATLAB_EXPORT = Boolean.parseBoolean(prop.getProperty("MATLAB_EXPORT"));
		
		Constant.POSITION_SCALING = Integer.parseInt(prop.getProperty("POSITION_SCALING"));
		
		Constant.GRID_SIZE = Integer.parseInt(prop.getProperty("GRID_SIZE"));
		
		Constant.AXIS_LENGTH = Integer.parseInt(prop.getProperty("AXIS_LENGTH"));
		
		Constant.CAMERA_POS_X = Double.parseDouble(prop.getProperty("CAMERA_POS_X"));
		Constant.CAMERA_POS_Y = Double.parseDouble(prop.getProperty("CAMERA_POS_Y"));
		Constant.CAMERA_POS_Z = Double.parseDouble(prop.getProperty("CAMERA_POS_Z"));
		
		Constant.CAMERA_VIEW_X = Double.parseDouble(prop.getProperty("CAMERA_VIEW_X"));
		Constant.CAMERA_VIEW_Y = Double.parseDouble(prop.getProperty("CAMERA_VIEW_Y"));
		Constant.CAMERA_VIEW_Z = Double.parseDouble(prop.getProperty("CAMERA_VIEW_Z"));
		
		Constant.CAMERA_UP_X = Double.parseDouble(prop.getProperty("CAMERA_UP_X"));
		Constant.CAMERA_UP_Y = Double.parseDouble(prop.getProperty("CAMERA_UP_Y"));
		Constant.CAMERA_UP_Z = Double.parseDouble(prop.getProperty("CAMERA_UP_Z"));
		
		Constant.CAMERA_MOVE_SPEED = Integer.parseInt(prop.getProperty("CAMERA_MOVE_SPEED"));
		Constant.CAMERA_ROTATE_SPEED = Double.parseDouble(prop.getProperty("CAMERA_ROTATE_SPEED"));
	}
	
	private static void initializeLibrary(){
		try {
            System.load(Constant.OPENNI2_DLL);
            System.load(Constant.NITE2_DLL);
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        }
        catch (Exception e){
            e.printStackTrace();
        }
	}
	
}
