package base.graphics.shaders;

import org.joml.Matrix4f;
import org.joml.Vector3f;

abstract public class Shader extends ShaderProgram{

    public Shader(String fragmentFile) {
        super(fragmentFile);
    }

    public Shader(String fragmentFile, String vertexFile) {
        super(fragmentFile, vertexFile);
    }

    //public Shader(String fragmentFile, String vertexFile, String geometryFile) {
    //    super(vertexFile, fragmentFile, geometryFile);
    //}

    public void loadTransformationMatrix(Matrix4f matrix){ }

    public void loadProjectionMatrix(Matrix4f projection){ }

    public void loadViewMatrix(Vector3f dir, Vector3f pos){ }
}
