package base.glfw.window;

import base.tools.SupplierMap;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.function.Supplier;

public class Draw {
    private GLFW_Window window;

    private boolean canDraw=true;

    public void canDraw(boolean bool){
        this.canDraw=bool;
    }
    public boolean isCanDraw(){ return canDraw; }

    private SupplierMap open=new SupplierMap();
    private SupplierMap inside=new SupplierMap();
    private SupplierMap close=new SupplierMap();

    public SupplierMap getOpen() { return open; }
    public SupplierMap getInside() { return inside; }
    public SupplierMap getClose() { return close; }

    private ArrayList<Supplier> tasks = new ArrayList<Supplier>();

    public void addTask(Supplier s){
        tasks.add(s);
    }

    public void doTasks(){
        int itrs=tasks.size();
        for(int i=0;i<itrs;i++){
            if(tasks.get(0)!=null){
                tasks.get(0).get();
                tasks.remove(0);
            }
        }
    }

    public void setWindow(GLFW_Window window){
        this.window=window;
    }

    public GLFW_Window getWindow() { return window; }

    public Draw(){
        getOpen().add(()->{
            GLFW.glfwPollEvents();
        return this;},"POLL_EVENTS");
    }

    public void draw(){
        if(isCanDraw()) {
            getOpen().run();
            getInside().run();
            doTasks();
            getClose().run();
        }
    }
}
