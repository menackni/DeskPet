package base.graphics.renderers;

import base.graphics.models.EntityModel;
import base.graphics.models.RawModel;
import base.graphics.shaders.Shader;
import org.joml.Vector3f;

public class Render{

    public void setPosition(Vector3f position){ }
    public void setRotation(Vector3f rotation){ }
    public void setZoom(Vector3f zoom){}

    public Vector3f getPosition(){ return null; }
    public Vector3f getRotation(){ return null; }
    public Vector3f getZoom(){ return null; }

    public void cleanupPosRotZoom(){
        setPosition(new Vector3f(0));
        setRotation(new Vector3f(0));
        setZoom(new Vector3f(1));
    }

    public void loadProjectionMatrixToShader(Shader shader){ }
    public void loadTransformationMatrixToShader(Shader shader){ }

    public void updProjectionMatrix(int w, int h){ }
    public void updProjectionMatrix(float l, float r, float b, float t){ }
    public void updProjectionMatrix(int w, int h, Camera cam){ }

    public void setMode(int mode) { }//FOR glBegin() (GL_LINES,POLYGONS,QUADS etc...)

    public void drawEntityModel(EntityModel entity){ }

    public void drawRawModel(RawModel model, int texture){ }

    public void render(EntityModel entity){}

    public void render(EntityModel entity, Shader shader,Vector3f pos,Vector3f rot,Vector3f scl){}

}
