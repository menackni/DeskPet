package base.graphics.assets;

import base.glfw.GLFW_Manager;
import base.graphics.frameBuffers.FrameBuffer;
import base.graphics.textures.Texture;
import base.graphics.textures.TextureCombine;
import base.graphics.textures.TextureManager;
import base.tools.Mapper;
import base.tools.ToolBox;
import org.joml.Vector2i;
import org.lwjgl.opengl.GL11;

public class PackManager{
    private static FrameBuffer frameBuffer;
    private static TextureCombine textureCombine=new TextureCombine();
    private static Texture textureDynamic;

    private static Mapper packs=new Mapper();
    private static Mapper assets=new Mapper();
    private static Mapper staticAnimations=new Mapper();

    public static FrameBuffer getFrameBuffer(){ return frameBuffer; }

    public static TextureCombine getTextureCombine() { return textureCombine; }
    public static Texture getTextureDynamic() { return textureDynamic; }

    public static Mapper getPacks() { return packs; }
    public static Mapper getAssets() { return assets; }
    public static Mapper getStaticAnimations(){ return staticAnimations; }

    public static Asset getStaticAnimation(String name){ return (Asset)getStaticAnimations().get(name); }
    public static void addStaticAnimation(Asset asset,String name){ getStaticAnimations().add(asset,name); }
    public static void addStaticAnimation(Asset asset){getStaticAnimations().add(asset,asset.getName());}

    public static AssetPack getPack(String name){ return (AssetPack)packs.get(name); }
    public static AssetPack getPack(int id){ return (AssetPack)packs.get(id); }

    public static void addPack(AssetPack asset){getPacks().add(asset,asset.getName());}

    public static Asset getAsset(String name){return (Asset)getAssets().get(name);}

    public static void addAsset(Asset asset){getAssets().add(asset,asset.getName());}
    public static void addAsset(Asset asset,String name){getAssets().add(asset,name);}

    public static void beforeTextureInit(){
        TextureManager.getCombines().add(getTextureCombine(),"PackManager");
        LoadAsset.init();
    }

    public static void afterTextureInit(){
        initFrameBuffer();
    }

    public static void updAfterFrame(){
        updStaticAnimations();
    }

    private static void updStaticAnimations(){
        FrameBuffer fb=getFrameBuffer();
        Vector2i winSize = GLFW_Manager.getDefaultWindow().getSize();
        ToolBox.setOrtho(0, winSize.x, 0, winSize.y);
        fb.bindFrameBuffer();
        for(int i=0;i<getStaticAnimations().size();i++){
            ((AnimationStatic)getStaticAnimations().get(i)).updStatic(getTextureCombine().getId());
            ((AnimationStatic)getStaticAnimations().get(i)).upd();
        }
        fb.unbindCurrentFrameBuffer();
        ToolBox.resetOrtho();
    }

    private static void initFrameBuffer(){
        frameBuffer=new FrameBuffer(getTextureCombine().getW(),getTextureCombine().getH());
        textureDynamic=new Texture(getFrameBuffer().getTexture(), getFrameBuffer().WIDTH, getFrameBuffer().HEIGHT);
        Vector2i winSize = GLFW_Manager.getDefaultWindow().getSize();
        ToolBox.setOrtho(0, winSize.x, 0, winSize.y);
        getFrameBuffer().bindFrameBuffer();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, getTextureCombine().getId());
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0,0);
        GL11.glVertex2f(0,0);
        GL11.glTexCoord2f(1,0);
        GL11.glVertex2f(getTextureCombine().getW(),0);
        GL11.glTexCoord2f(1,1);
        GL11.glVertex2f(getTextureCombine().getW(),getTextureCombine().getH());
        GL11.glTexCoord2f(0,1);
        GL11.glVertex2f(0,getTextureCombine().getH());
        GL11.glEnd();
        getFrameBuffer().unbindCurrentFrameBuffer();
        ToolBox.resetOrtho();
    }

    public static void drawAllAssetsFullScreen(){
        ToolBox.resetOrtho();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,PackManager.getTextureDynamic().getId());
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(0,0);
        GL11.glVertex2f(0,0);

        GL11.glTexCoord2f(1,0);
        GL11.glVertex2f(GLFW_Manager.getDefaultWindow().getSize().x,0);

        GL11.glTexCoord2f(1,1);
        GL11.glVertex2f(GLFW_Manager.getDefaultWindow().getSize().x, GLFW_Manager.getDefaultWindow().getSize().y);

        GL11.glTexCoord2f(0,1);
        GL11.glVertex2f(0, GLFW_Manager.getDefaultWindow().getSize().y);
        GL11.glEnd();
    }
}
