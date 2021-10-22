package base.gui;

import base.glfw.input.MoveListenerGLFW;

public class GUI_MoveListener extends MoveListenerGLFW {
    public void move(long window, double x, double y){
        GUI_Manager.getInput().move(window, x, y);
    }
}
