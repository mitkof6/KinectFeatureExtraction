package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import main.Constant;
import main.Main;

/**
 * Main Window
 * 
 * @author Jim Stanev
 *
 */
public class MainWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MainWindow(){
		super("Viewer");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		//rgb-depth
		Box horizontBox = Box.createHorizontalBox();
		if(Main.kinect.rgbStream!=null){
			horizontBox.add(Main.kinect.rgbStream);
		}
		if(Main.kinect.depthStream!=null){
			horizontBox.add(Main.kinect.depthStream.viwer);			
		}
        this.add(horizontBox, BorderLayout.CENTER);
		
        //buttons
		JToolBar toolBar = new JToolBar();
		JButton rgbButton = new JButton("RGB");
		rgbButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(Main.kinect.rgbStream!=null) Main.kinect.rgbStream.setStart();
				
			}
		});
		toolBar.add(rgbButton);
		
		JButton depthButton = new JButton("Depth");
		depthButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(Main.kinect.depthStream!=null) Main.kinect.depthStream.setStart();
				
			}
		});
		toolBar.add(depthButton);
		
		JButton saveSkeleton = new JButton("Save Skel");
		saveSkeleton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(Main.kinect.userStream!=null) {
					try {
						Main.kinect.userStream.sequence.export(Constant.SKELETON_FILE_NAME);
					} catch (FileNotFoundException e) {
						JOptionPane.showMessageDialog(null, 
								"Skeleton sequence can't be saved", "Error", JOptionPane.ERROR_MESSAGE);
					} catch (UnsupportedEncodingException e) {
						JOptionPane.showMessageDialog(null, 
								"Skeleton sequence can't be saved", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});
		toolBar.add(saveSkeleton);
		
		JButton saveMesh = new JButton("Save PCS");
		saveMesh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(Main.kinect.depthStream!=null) {
					try {
						Main.kinect.depthStream.sequence.export(Constant.POINT_CLOUD_FILE_NAME);
					} catch (FileNotFoundException e) {
						JOptionPane.showMessageDialog(null, 
								"Point cloud sequence can't be saved", "Error", JOptionPane.ERROR_MESSAGE);
					} catch (UnsupportedEncodingException e) {
						JOptionPane.showMessageDialog(null, 
								"Point cloud sequence can't be saved", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});
		toolBar.add(saveMesh);
		this.add(toolBar, BorderLayout.NORTH);
		
		this.setSize(Constant.MAIN_WINDOW_WIDTH, Constant.MAIN_WINDOW_HEIGHT);
		this.setResizable(false);
	}
}
