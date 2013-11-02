package main;

import opengl.Animator;

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
		//kinect
		kinect = new Kinect();
		//if(Constant.START_COLOR) kinect.openRGB();
		if(Constant.START_DEPTH) kinect.openDepth();
		if(Constant.START_USER) kinect.openUserTracker();
		
		
		//gui
		MainWindow mainWindow = new MainWindow();
		mainWindow.setVisible(true);
		
		//animator
		if(Constant.START_ANIMATOR){
			Animator animator = new Animator();
			animator.setVisible(true);
		}
		

	}
	
	static {
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
