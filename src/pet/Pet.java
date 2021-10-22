package pet;

import base.glfw.GLFW_Manager;
import base.graphics.assets.PackManager;
import base.graphics.models.EntityModel;
import base.graphics.renderers.RenderersList;
import base.graphics.shaders.Shader;
import base.graphics.shaders.ShadersList;
import base.graphics.tiles.TileModel;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;

public class Pet{
    private EntityModel model=null;
    private boolean show=true;
    private String icon="dog";

    private Vector2i position;
    private int size;

    public Vector2i getPosition(){ return position; }
    public void setPosition(Vector2i position){ this.position=position; }
    public void setPosition(int x,int y){ this.position=new Vector2i(x,y); }

    public int getSize(){ return size; }
    public void setSize(int size){ this.size=size; }

    public EntityModel getModel(){ return model; }
    public void setModel(EntityModel model){ this.model=model; }

    public boolean isShow(){ return show; }
    public void setShow(boolean show){ this.show=show; }

    public String getIcon(){ return icon; }
    public void setIcon(String icon){ this.icon=icon; }

    public Pet(Vector2i pos, int size){
        this.position=pos;
        this.size=size;
    }

    public void drawModel(int x, int y){
        Vector2i screenSize=GLFW_Manager.getDefaultWindow().getSize();

        Shader shd=ShadersList.get("quad");

        Vector2i move=new Vector2i(0);
        if(CaptureMouse.getPoseHistory().size()>0)move=new Vector2i(CaptureMouse.getPoseHistory().get(0));

        float distanceX=(((move.x-getPosition().x)/86.0f)/getSize())*64.0f;
        float distanceY=(((move.y-getPosition().y)/86.0f)/getSize())*64.0f;

        float distance=(float)(0-move.distance(getPosition()))+getSize();
        if(distance<0)distance=0;
        else distance=((distance*distance*0.025f)/getSize())*64.0f;

        float xADD=(((float)(getPosition().x-move.x)/getSize())*64.0f)*distance;
        float yADD=(((float)(0-(getPosition().y-move.y))/getSize())*64.0f)*distance;

        float a=getSize()/2.0f;

        RenderersList.get("r2d").setRotation(new Vector3f(xADD/a,yADD/a,0));
        RenderersList.get("r2d").setPosition(new Vector3f(x-(distanceX*distance),y-(distanceY*distance),0));
        RenderersList.get("r2d").setZoom(new Vector3f(getSize(),getSize(),1));
        RenderersList.get("r2d").updProjectionMatrix(screenSize.x,screenSize.y);

        shd.start();

        RenderersList.get("r2d").loadProjectionMatrixToShader(shd);
        RenderersList.get("r2d").loadTransformationMatrixToShader(shd);
        RenderersList.get("r2d").setMode(GL20.GL_QUADS);
        RenderersList.get("r2d").drawRawModel(getModel().getRaw(), PackManager.getTextureDynamic().getId());

        shd.stop();

        RenderersList.get("r2d").updProjectionMatrix(screenSize.x,screenSize.y);
    }

    public void checkForModelAndCreate(){
        if(getModel()!=null)getModel().getRaw().cleanUp();
        TileModel tm=new TileModel(1);
        tm.getTile(0).setAsset(PackManager.getAsset(icon));
        tm.getTile(0).setLt(new Vector3f(-0.5f,-0.5f,0));
        tm.getTile(0).setRb(new Vector3f(0.5f,0.5f,0));
        tm.getTile(0).setPos(new Vector3f(0,0,0));
        tm.getTile(0).updGeometry();
        tm.getTile(0).updTextureDontCheckUpd();

        setModel(new EntityModel(tm.getQuadModel().getModel(),PackManager.getTextureDynamic()));
    }

    public void draw(){ if(isShow())if(getModel()==null)checkForModelAndCreate();drawModel(getPosition().x, getPosition().y); }

}
