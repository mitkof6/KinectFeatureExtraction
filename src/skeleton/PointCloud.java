package skeleton;

import java.util.Vector;

import math.geom3d.Point3D;

/**
 * Point cloud data
 * 
 * @author Jim Stanev
 */
public class PointCloud {

	private Vector<Point3D> pointCloud;
	
	public PointCloud(){
		pointCloud = new Vector<>();
	}
	
	public void add(Point3D p){
		pointCloud.add(p);
	}
	
	public Vector<Point3D> getPointCloud(){
		return pointCloud;
	}
	
}
