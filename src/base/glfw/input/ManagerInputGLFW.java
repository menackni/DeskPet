package base.glfw.input;

import base.glfw.GLFW_Manager;
import base.tools.Mapper;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;

public class ManagerInputGLFW {

    private Mapper listeners=new Mapper();

    public void addListener(Listener listener, String name){
        listeners.add(listener, name);
    }

    public Mapper getListeners() {
        return listeners;
    }

    public boolean allowInput=true;

    public void init(long window){
        GLFW.glfwSetCursorPosCallback(window,new CallbackMousePos());
        GLFW.glfwSetKeyCallback(window,new CallbackKeys());
        GLFW.glfwSetMouseButtonCallback(window,new CallbackMouseKeys());
        GLFW.glfwSetWindowSizeCallback(window,new CallbackWindow());
        GLFW.glfwSetCharCallback(window,new CallbackChar());
        GLFW.glfwSetWindowPosCallback(window,new CallbackWindowPosition());
    }

    public void moveWindow(long window, double x, double y){
        GLFW_Manager.getDefaultWindow().setPosition(new Vector2i((int)x,(int)y));
    }

    public void move(long window, double x, double y){
        if(allowInput)
        for(int i=0;i<listeners.size();i++){
            if(((Listener)listeners.get(i)).type==Types.MOVE){
                ((MoveListenerGLFW)listeners.get(i)).move(window, x, y);
            }
        }
    }

    public void key(long window,int key, int action, int mods){
        if(allowInput)
        for(int i=0;i<listeners.size();i++){
            if(((Listener)listeners.get(i)).type==Types.KEY){
                ((KeyListenerGLFW)listeners.get(i)).key(window, key, action, mods);
            }
        }
    }

    public void chr(long window,int unicodeChar){
        if(allowInput)
        for(int i=0;i<listeners.size();i++){
            if(((Listener)listeners.get(i)).type==Types.CHR){
                ((CharListenerGLFW)listeners.get(i)).chr(window, unicodeChar);
            }
        }
    }

}
