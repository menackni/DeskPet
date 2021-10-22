package base.graphics.tiles;

import base.graphics.models.RawModel;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL15;

public class QuadTile{

    private RawModel parentModel;
    private Vector3f lt;
    private Vector3f rb;
    private Vector2f texturePosLt;
    private Vector2f texturePosRb;
    private final long index;

    public void setParentModel(RawModel parentModel){ this.parentModel=parentModel; }
    public RawModel getParentModel(){ return parentModel; }

    public void setLt(Vector3f lt){ this.lt=lt; }
    public Vector3f getLt(){ return lt; }

    public void setRb(Vector3f rb){ this.rb=rb; }
    public Vector3f getRb(){ return rb; }

    public void setTexturePosLt(Vector2f texturePosLt){ this.texturePosLt=texturePosLt; }
    public Vector2f getTexturePosLt(){ return texturePosLt; }

    public void setTexturePosRb(Vector2f texturePosRb){ this.texturePosRb=texturePosRb; }
    public Vector2f getTexturePosRb(){ return texturePosRb; }

    public long getIndex(){ return index; }

    public void editFull(){
        editPos();
        editTexture();
    }

    public void editTexture(){
        int bufferId=getParentModel().getVbos().get(2);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferId);
        GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER,index*32,new float[]{
                getTexturePosLt().x+0.00001f,getTexturePosLt().y+0.00001f,
                getTexturePosRb().x-0.00001f,getTexturePosLt().y+0.00001f,
                getTexturePosRb().x-0.00001f,getTexturePosRb().y-0.00001f,
                getTexturePosLt().x+0.00001f,getTexturePosRb().y-0.00001f});
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }
    public void editPos(){
        int bufferId=getParentModel().getVbos().get(1);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferId);
        GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER,index*48,new float[]{
                getLt().x,getLt().y,0,
                getRb().x,getLt().y,0,
                getRb().x,getRb().y,0,
                getLt().x,getRb().y,0});
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public QuadTile(long index){
        this.index=index;
        this.texturePosLt=new Vector2f(0);
        this.texturePosRb=new Vector2f(0);
        this.lt=new Vector3f(0);
        this.rb=new Vector3f(0);
    }
}
