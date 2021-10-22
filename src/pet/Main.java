package pet;

import base.glfw.GLFW_Manager;
import base.glfw.window.GLFW_Window;
import base.graphics.assets.PackManager;
import base.graphics.renderers.Render2d;
import base.graphics.renderers.RenderersList;
import base.graphics.shaders.ShadersList;
import base.graphics.textures.TextureManager;
import base.graphics.tiles.QuadShader;
import base.opengl.ManagerGL;
import org.joml.Vector2i;

import java.awt.*;

public class Main{

    public static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    public static TrayIcon tray;
    public static int petSize;
    public static int windowSize;
    public static Pet pet;

    public static void main(String[]args){

        petSize=64;
        windowSize=(int)(petSize*1.3f);
        if(windowSize<194)windowSize=194;

        tray=new TrayIcon();

        GLFW_Window window=new GLFW_Window(new Vector2i(windowSize),new Vector2i(dim.width/2,dim.height/2),0,"Pet");

        //=====================//GLFW_START===================================
        //=====================//GLFW_START===================================
        //=====================//GLFW_START===================================

        //CODE HERE RUNS BEFORE GLFW INITIALIZED
        GLFW_Manager.getBefore().add(()->{
            GLFW_Manager.getWindowManager().setStartWindow(window);

            pet=new Pet(new Vector2i(windowSize/2),petSize);
            pet.setIcon("dog");

            PackManager.beforeTextureInit();//PRELOAD ASSET-PACKS

            return true;},"BEFORE_GLFW_INIT");

        //CODE HERE RUNS AFTER GLFW INITIALIZED
        GLFW_Manager.getAfter().add(()->{

            GLFW_Manager.getDefaultWindow().loadIcon("Assets/textures/tray.gif");

            ManagerGL.init();//AT THIS MOMENT OPENGL INITIALIZED
            GLFW_Manager.getDefaultWindow().setDraw(ManagerGL.getDraw());ManagerGL.getDraw().setWindow(GLFW_Manager.getDefaultWindow());//SET DRAW METHOD FOR WINDOW

            PackManager.afterTextureInit();//ASSET-PACKS INITIALIZED, AT THIS MOMENT YOU CAN USE ASSETS

            tray.init();

            return true;},"AFTER_GLFW_INIT");

        //CODE HERE RUNS EVERY CYCLE IN WINDOW LOOP (HAS CAP IN MANAGER)
        GLFW_Manager.getRunning().add(()->{
            CaptureMouse.upd();
            return true;},"AFTER_GLFW_INIT");

        //CODE HERE RUNS WHEN WINDOW CLOSED
        GLFW_Manager.getExit().add(()->{
            STOP();
            return true;},"WHEN_GLFW_CLOSE");

        //=====================//GLFW_END===================================
        //=====================//GLFW_END===================================
        //=====================//GLFW_END===================================



        //=====================//GL_START===================================
        //=====================//GL_START===================================
        //=====================//GL_START===================================

        //CODE HERE RUNS BEFORE GL INITIALIZED    //INITIALIZATION//INITIALIZATION//INITIALIZATION
        ManagerGL.getBefore().add(()->{
            return true; },"BEFORE_GL_INIT");

        //CODE HERE RUNS AFTER GL INITIALIZED    //INITIALIZATION//INITIALIZATION//INITIALIZATION
        ManagerGL.getAfter().add(()->{
            RenderersList.getRenderers().add(new Render2d(GLFW_Manager.getDefaultWindow().getSize().x, GLFW_Manager.getDefaultWindow().getSize().y),"r2d");
            ShadersList.add(new QuadShader(),"quad");
            TextureManager.init();
            return true; },"AFTER_GL_INIT");



        //CODE HERE FOR INITIALIZE FRAME     //RENDER//RENDER//RENDER//RENDER//RENDER
        ManagerGL.getDraw().getOpen().add(()->{
            GLFW_Manager.getDefaultWindow().setContext();
            GLFW_Manager.getDefaultWindow().swapBuff();
            return true;},"DRAW_OPEN");

        //CODE HERE FOR DRAW IN FRAME      //RENDER//RENDER//RENDER//RENDER//RENDER
        ManagerGL.getDraw().getInside().add(()->{
            pet.draw();
            return true;},"DRAW");

        //CODE HERE FOR CLOSE FRAME      //RENDER//RENDER//RENDER//RENDER//RENDER
        ManagerGL.getDraw().getClose().add(()->{
            //PackManager.updAfterFrame();
            return true; },"DRAW_CLOSE");

        //=====================//GL_END===================================
        //=====================//GL_END===================================
        //=====================//GL_END===================================



        //=====================//PROGRAM_STARTS_AT_THIS_MOMENT===================================
        //=====================//PROGRAM_STARTS_AT_THIS_MOMENT===================================
        //=====================//PROGRAM_STARTS_AT_THIS_MOMENT===================================

        GLFW_Manager.init();
    }

    public static void STOP(){
        tray.delite();
    }
}
