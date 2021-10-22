package base.gui;

import base.glfw.input.KeyListenerGLFW;
import base.input.ManagerInput;

public class GUI_KeyListener extends KeyListenerGLFW {
    public void key(long window, int key, int action, int mods){
        ManagerInput.keyUpd(key, GUI_Manager.getInput().mapperName,action);
    }
}
