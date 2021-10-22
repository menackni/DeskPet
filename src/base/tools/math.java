package base.tools;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class math {
	
	public static Vector3f rotate(Vector3f dir0,Vector3f dir1){
		float X1=(float) ((dir1.x)*Math.cos(Math.toRadians(dir0.z))+(dir1.y)*Math.sin(Math.toRadians(dir0.z)));
		float Y1=(float) ((dir1.y)*Math.cos(Math.toRadians(dir0.x))-(dir1.z)*Math.sin(Math.toRadians(dir0.x)));
		float Z1=(float) ((dir1.y)*Math.sin(Math.toRadians(dir0.x))+(dir1.z)*Math.cos(Math.toRadians(dir0.x)));
		
		float X2=(float) ( (X1)*Math.cos(Math.toRadians(dir0.y))+(Z1)*Math.sin(Math.toRadians(dir0.y)));
		float Y2=(float) (-(X1)*Math.sin(Math.toRadians(dir0.z))+(Y1)*Math.cos(Math.toRadians(dir0.z)));
		float Z2=(float) ( (X1)*Math.sin(Math.toRadians(dir0.y))-(Z1)*Math.cos(Math.toRadians(dir0.y)));
		
		return new Vector3f(X2,Y2,Z2);
	}

	public static Matrix4f createTransformationMatrix(Vector3f translation,Vector3f rotation,Vector3f scale){
		Matrix4f matrix=new Matrix4f();
		matrix.translate(translation, matrix);
		matrix.rotate((float)Math.toRadians(rotation.x), new Vector3f(0,1,0), matrix);
		matrix.rotate((float)Math.toRadians(rotation.y), new Vector3f(1,0,0), matrix);
		matrix.rotate((float)Math.toRadians(rotation.z), new Vector3f(0,0,1), matrix);
		matrix.scale(scale, matrix);
		return matrix;
	}

	public static Matrix4f createViewMatrix(Vector3f dir,Vector3f pos){
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.rotate((float)Math.toRadians(dir.x), new Vector3f(1,0,0), viewMatrix);
		viewMatrix.rotate((float)Math.toRadians(dir.y), new Vector3f(0,1,0), viewMatrix);
		Vector3f negativeCameraPos = new Vector3f(-pos.x,-pos.y,-pos.z);
		viewMatrix.translate(negativeCameraPos, viewMatrix);
		return viewMatrix;
	}
}
