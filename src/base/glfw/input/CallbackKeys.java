package base.glfw.input;

import base.glfw.GLFW_Manager;
import org.lwjgl.glfw.GLFWKeyCallback;

public class CallbackKeys extends GLFWKeyCallback {
    @Override
    public void invoke(long win, int key, int scancode, int action, int mods) { GLFW_Manager.getInput().key(win, key, action, mods); }
}
