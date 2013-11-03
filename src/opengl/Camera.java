package opengl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.glu.GLU;

import main.Constant;
import math.geom3d.Vector3D;
import math.geom3d.transform.AffineTransform3D;

/**
 * Camera
 * 
 * @author Jim Stanev
 *
 */
public class Camera implements KeyListener, MouseMotionListener{
	
	private Vector3D position;
	private Vector3D view;
	private Vector3D up;
	
	private int lastX = 0, lastY = 0;
	
	public Camera(Vector3D position, Vector3D view, Vector3D up){
		this.position = position;
		this.view = view;
		this.up = up;
	}
	
	public void update(GLU glu){
		glu.gluLookAt(
						position.getX(), position.getY(), position.getZ(),
						view.getX(), view.getY(), view.getZ(),
						up.getX(), up.getY(), up.getZ());
	}
	
	private void move(double speed){
		
		Vector3D direction = view.minus(position);
		direction = direction.normalize();
		
		position = position.plus(direction.times(speed));
	}
	
	private void rotateY(double speed){
		
		Vector3D direction = view.minus(position);
		
		view = new Vector3D(
						Math.cos(speed)*direction.getX()+Math.sin(speed)*direction.getZ(),
						view.getY(),
						-Math.sin(speed)*direction.getX()+Math.cos(speed)*direction.getZ());
	}
	
	private void rotateX(double speed){
		view = view.transform(AffineTransform3D.createRotationOx(speed));
	}
	
	private void strafe(double speed){
		
		Vector3D direction = view.minus(position);
		Vector3D ortho = new Vector3D(-direction.getZ()*speed, 0, direction.getX()*speed);
		ortho = ortho.normalize();
		
		position = position.plus(ortho);
		view = view.plus(ortho);
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if(event.getKeyCode()==KeyEvent.VK_W){//up
			move(Constant.CAMERA_MOVE_SPEED);
			//strafe(Constant.CAMERA_MOVE_SPEED);
		}else if(event.getKeyCode()==KeyEvent.VK_S){//down
			move(-Constant.CAMERA_MOVE_SPEED);
			//strafe(-Constant.CAMERA_MOVE_SPEED);
		}else if(event.getKeyCode()==KeyEvent.VK_A){//left
			strafe(-Constant.CAMERA_MOVE_SPEED);
			//move(-Constant.CAMERA_MOVE_SPEED);
		}else if(event.getKeyCode()==KeyEvent.VK_D){//right
			strafe(Constant.CAMERA_MOVE_SPEED);
			//move(Constant.CAMERA_MOVE_SPEED);	
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}


	@Override
	public void mouseDragged(MouseEvent event) {
		int diffX = event.getX() - lastX;
		int diffY = event.getY() - lastY;
		
		lastX = event.getX();
		lastY = event.getY();

		if(Math.abs(diffX)<Math.abs(diffY)){
			rotateX(-diffY*Constant.CAMERA_ROTATE_SPEED);
		}else{
			rotateY(-diffX*Constant.CAMERA_ROTATE_SPEED);
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {}

}
