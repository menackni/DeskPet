package base.graphics.renderers;

import base.graphics.models.EntityModel;
import base.graphics.models.RawModel;
import base.graphics.shaders.Shader;
import base.tools.math;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Render2d extends Render{
    private Matrix4f projectionMatrix;
    private int mode;

    private Vector3f position=new Vector3f(0);
    private Vector3f rotation=new Vector3f(0);
    private Vector3f zoom=new Vector3f(0);

    public void setPosition(Vector3f position){ this.position=position; }
    public void setRotation(Vector3f rotation){ this.rotation=rotation; }
    public void setZoom(Vector3f zoom){ this.zoom=zoom; }

    public Vector3f getPosition(){ return position; }
    public Vector3f getRotation(){ return rotation; }
    public Vector3f getZoom(){ return zoom; }

    public void setPosition(Vector2f position){ this.position=new Vector3f(position,0); }
    public void setRotation(Vector2f rotation){ this.rotation=new Vector3f(rotation,0); }
    public void setZoom(Vector2f zoom){ this.zoom=new Vector3f(zoom,1); }

    public void cleanupPosRotZoom(){
        setPosition(new Vector3f(0));
        setRotation(new Vector3f(0));
        setZoom(new Vector3f(1));
    }

    public void loadProjectionMatrixToShader(Shader shader){ shader.loadProjectionMatrix(projectionMatrix); }
    public void loadTransformationMatrixToShader(Shader shader){
        Matrix4f transformationMatrix=math.createTransformationMatrix(getPosition(),getRotation(),getZoom());
        shader.loadTransformationMatrix(transformationMatrix);
    }

    public void updProjectionMatrix(int w, int h){ projectionMatrix=new Matrix4f().ortho(0, w, h, 0, -2048, 2048); }
    public void updProjectionMatrix(float l, float r, float b, float t){ projectionMatrix=new Matrix4f().ortho(l, r, b, t,-2048, 2048); }
    public void updProjectionMatrix(int w, int h, Camera cam){
        projectionMatrix=new Matrix4f().ortho(cam.getCamPos().x-((w/2.0f)/cam.getZoom()), cam.getCamPos().x+((w/2.0f)/cam.getZoom()), cam.getCamPos().y+((h/2.0f)/cam.getZoom()), cam.getCamPos().y-((h/2.0f)/cam.getZoom()), -1, 1);
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void drawEntityModel(EntityModel entity){
        RawModel model=entity.getRaw();

        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        GL20.glBindTexture(GL20.GL_TEXTURE_2D, entity.getTexture().getId());
        GL20.glDrawElements(GL20.GL_QUADS, model.getVertexCount(),GL20.GL_UNSIGNED_INT, 0);

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }

    public void drawRawModel(RawModel model, int texture){

        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        GL20.glBindTexture(GL20.GL_TEXTURE_2D, texture);
        GL20.glDrawElements(GL20.GL_QUADS, model.getVertexCount(),GL20.GL_UNSIGNED_INT, 0);

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }

    public void render(EntityModel entity){
        RawModel model=entity.getRaw();

        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        GL20.glBindTexture(GL20.GL_TEXTURE_2D, entity.getTexture().getId());
        GL20.glDrawElements(GL20.GL_TRIANGLES, model.getVertexCount(),GL20.GL_UNSIGNED_INT, 0);

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }

    public void render(EntityModel entity, Shader shader,Vector3f pos,Vector3f rot,Vector3f scl){
        RawModel model=entity.getRaw();

        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        Vector3f position=new Vector3f(entity.getPosition());
        Vector3f rotation=new Vector3f(entity.getRotarion());
        Vector3f scale=new Vector3f(entity.getScale());

        if(pos!=null){
            position.add(pos);
        }
        if(rot!=null){
            rotation.add(rot);
        }
        if(scl!=null){
            scale.mul(scl);
        }

        shader.loadProjectionMatrix(projectionMatrix);
        Matrix4f transformationMatrix=math.createTransformationMatrix(position, rotation, scale);
        shader.loadTransformationMatrix(transformationMatrix);

        GL20.glBindTexture(GL20.GL_TEXTURE_2D, entity.getTexture().getId());

        GL20.glDrawElements(mode, model.getVertexCount(),GL20.GL_UNSIGNED_INT, 0);

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }

    public Render2d(int w, int h){
        projectionMatrix=new Matrix4f().ortho(0, w, h, 0, -2048, 2048);
    }

}
