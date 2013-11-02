package skeleton;

import java.util.Vector;


//import math.geom3d.Point3D;
import org.openni.Point3D;

/**
 * Point cloud data
 * 
 * @author Jim Stanev
 */
public class PointCloud{

	private Vector<Point3D<Float>> pointCloud;
	private long timeStamp;
	
	public PointCloud(long timeStamp){
		pointCloud = new Vector<>();
		this.timeStamp = timeStamp;
	}
	
	public void add(Point3D<Float> point3d){
		pointCloud.add(point3d);
	}
	
	public Vector<Point3D<Float>> getPointCloud(){
		return pointCloud;
	}
	
	public long getTimeStamp(){
		return timeStamp;
	}

	
}
