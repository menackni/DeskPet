package base.graphics.tiles;

import base.graphics.shaders.Shader;
import base.graphics.shaders.ShadersList;
import base.tools.math;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class QuadShader extends Shader{
    private static final String VERTEX_FILE=ShadersList.shadersFolderPath+"QuadShader/vertexShader.txt";
    private static final String FRAGMENT_FILE=ShadersList.shadersFolderPath+"QuadShader/fragmentShader.txt";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;

    public QuadShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "pos");
        super.bindAttribute(1, "texPos");
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix=super.getUniformLocation("transformationMatrix");
        location_projectionMatrix=super.getUniformLocation("projectionMatrix");
        location_viewMatrix=super.getUniformLocation("viewMatrix");
    }
    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);

    }
    public void loadViewMatrix(Vector3f dir, Vector3f pos){
        Matrix4f viewMatrix= math.createViewMatrix(dir,pos);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

    public void loadProjectionMatrix(Matrix4f projection){
        super.loadMatrix(location_projectionMatrix, projection);
    }
}
