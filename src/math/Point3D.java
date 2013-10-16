package math;

public class Point3D {
	
	private static final float TOLERANCE = 0.0001f;

	public float x;
	public float y;
	public float z;
	
	public Point3D(){
		
	}
	
	public Point3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}	
	
	public String toString(){
		return "["+x+", "+y+", "+z+"]";
	}
	
	public float distance(Point3D p){
		return (float) Math.sqrt((x-p.x)*(x-p.x)+(y-p.y)*(y-p.y)+(z-p.z)*(z-p.z));
	}
	
	public boolean equal(Point3D p){
		if(distance(p)>TOLERANCE){
			return false;
		}else{
			return true;
		}
	}
}
