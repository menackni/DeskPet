package base.glfw.window;

import base.glfw.GLFW_Manager;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWNativeWin32;
import org.lwjgl.system.windows.User32;

public class ManagerWindow {

    private GLFW_Window defaultWindow;

    public void setStartWindow(GLFW_Window startWindow){
        if(getWindow()==null) this.defaultWindow=startWindow;
    }

    public GLFW_Window getWindow(){
        return defaultWindow;
    }

    public void init(){
        System.out.println("INIT_GLFW_WINDOW_START");
        if(GLFW.glfwInit()!=true){
            System.err.println("INIT_GLFW_WINDOW_FAILED");
            GLFW_Manager.getExit().run();
            System.exit(1);
        }

        GLFW.glfwWindowHint(GLFW.GLFW_FLOATING, 1);
        GLFW.glfwWindowHint(GLFW.GLFW_TRANSPARENT_FRAMEBUFFER, 1);
        GLFW.glfwWindowHint(GLFW.GLFW_MOUSE_PASSTHROUGH, 1);
        GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, 0);

        defaultWindow.init();

        getWindow().setContext();
        getWindow().showWindow();

        long hz=GLFWNativeWin32.glfwGetWin32Window(getWindow().getId());
        long hz2=User32.SetWindowLongPtr(hz,User32.GWL_EXSTYLE,User32.GetWindowLongPtr(hz,User32.GWL_EXSTYLE)|User32.WS_EX_TRANSPARENT);

        System.out.println("INIT_GLFW_WINDOW_END");
    }

}
