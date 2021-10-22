package base.glfw.input;

import base.glfw.GLFW_Manager;
import org.lwjgl.glfw.GLFWCharCallback;

public class CallbackChar extends GLFWCharCallback {
    @Override
    public void invoke(long l, int i) { GLFW_Manager.getInput().chr(l, i); }
}