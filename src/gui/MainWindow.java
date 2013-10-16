package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import main.Constant;
import main.Main;

public class MainWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MainWindow(){
		super("Viewer");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		
		
		Box horizontBox = Box.createHorizontalBox();
		if(Main.kinect.rgbStream!=null){
			horizontBox.add(Main.kinect.rgbStream);
		}
		if(Main.kinect.depthStream!=null){
			horizontBox.add(Main.kinect.depthStream);
		}
		this.add(horizontBox, BorderLayout.CENTER);
		
		JToolBar toolBar = new JToolBar();
		JButton rgbButton = new JButton("RGB");
		rgbButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main.kinect.rgbStream.stop();
				
			}
		});
		toolBar.add(rgbButton);
		this.add(toolBar, BorderLayout.NORTH);
		
		this.setSize(Constant.MAIN_WINDOW_WIDTH, Constant.MAIN_WINDOW_HEIGHT);
	}
}
