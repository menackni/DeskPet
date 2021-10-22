package base.glfw.input;

import base.glfw.GLFW_Manager;
import org.lwjgl.glfw.GLFWWindowPosCallback;

public class CallbackWindowPosition extends GLFWWindowPosCallback{
    @Override
    public void invoke(long window, int xpos, int ypos){ GLFW_Manager.getInput().moveWindow(window,xpos,ypos); }
}
