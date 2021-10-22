package base.glfw.input;

import base.glfw.GLFW_Manager;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class CallbackMouseKeys extends GLFWMouseButtonCallback {

    public void invoke(long win, int key, int action, int mods){
        GLFW_Manager.getInput().key(win, key, action, mods);
    }

}
