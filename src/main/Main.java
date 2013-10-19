package main;

import opengl.Animator;

import org.opencv.core.Core;

import gui.MainWindow;
import kinect.Kinect;

public class Main {

	public static Kinect kinect;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//kinect
		kinect = new Kinect();
		kinect.openRGB();
		kinect.openDepth();
		kinect.openUserTracker();
		
		//gui
		MainWindow mainWindow = new MainWindow();
		mainWindow.setVisible(true);
		
		//animator
		Animator animator = new Animator();
		animator.setVisible(true);

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
