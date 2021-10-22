package base.glfw;

import base.glfw.input.ManagerInputGLFW;
import base.glfw.window.ManagerWindow;
import base.glfw.window.GLFW_Window;
import base.tools.SupplierMap;
import org.lwjgl.glfw.GLFW;

public class GLFW_Manager extends Thread{
    private final static ManagerWindow windowManager=new ManagerWindow();
    private final static ManagerInputGLFW input=new ManagerInputGLFW();

    private final static SupplierMap before=new SupplierMap();
    private final static SupplierMap after=new SupplierMap();
    private final static SupplierMap running=new SupplierMap();
    private final static SupplierMap exit=new SupplierMap();

    public static GLFW_Window getDefaultWindow(){ return getWindowManager().getWindow();}

    public static SupplierMap getRunning() { return running; }
    public static SupplierMap getBefore() { return before; }
    public static SupplierMap getAfter() { return after; }
    public static SupplierMap getExit() { return exit; }

    public static ManagerWindow getWindowManager() { return windowManager; }
    public static ManagerInputGLFW getInput() { return input; }

    @Override
    public void run() {
        init();
    }

    public static void init(){
        getBefore().run();
        getWindowManager().init();
        //getInput().init(getDefaultWindow().getId());
        getAfter().run();
        loop();
    }

    private static void loop(){
        while(!GLFW.glfwWindowShouldClose(getDefaultWindow().getId())){
            doStuff();
        }
        getExit().run();
    }

    public static void setFrameCap(int frameCap){ fc=frameCap;
        GLFW_Manager.frameCap=1.0/fc; }
    public static void setLogicCap(int logicCap){ lc=logicCap;
        GLFW_Manager.logicCap=1.0/lc; }

    public static int getFrameCap(){ return fc; }
    public static int getLogicCap(){ return lc; }

    private static int lc=128;
    private static int fc=60;

    private static double logicCap=1.0/lc;
    private static double frameCap=1.0/fc;

    private static double lastTimeLogic=0;
    private static double lastTimeFrame=0;

    private static void doStuff(){
        double currentTime=(double)System.nanoTime()/(double)1000000000L;
        if(currentTime-lastTimeLogic>logicCap){
            lastTimeLogic=currentTime;
            getRunning().run();
        }
        if(currentTime-lastTimeFrame>frameCap){
            lastTimeFrame=currentTime;
            getDefaultWindow().getDraw().draw();
        }
    }
}
