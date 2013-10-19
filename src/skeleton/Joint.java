package skeleton;



import math.geom3d.Point3D;

import com.primesense.nite.SkeletonJoint;




public class Joint {

	public Point3D position;
	
	public Joint(SkeletonJoint joint){
		position = new Point3D(
						joint.getPosition().getX(),
						joint.getPosition().getY(),
						joint.getPosition().getZ());
		//System.out.println(position.toString());
	}
	
	public Joint(float x, float y, float z){
		position = new Point3D(x, y, z);
		//System.out.println(position.toString());
	}
	
	
	
}
