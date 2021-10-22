package base.glfw.input;

import base.glfw.GLFW_Manager;
import org.lwjgl.glfw.GLFWCursorPosCallback;

public class CallbackMousePos extends GLFWCursorPosCallback {
    @Override
    public void invoke(long win, double x, double y){
        GLFW_Manager.getInput().move(win, x, y);
    }
}
