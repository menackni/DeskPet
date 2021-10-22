package base.gui;

import base.glfw.input.CharListenerGLFW;

public class GUI_CharListener extends CharListenerGLFW {
    @Override
    public void chr(long window, int unicodeChar){
        GUI_Manager.getInput().chr(window, unicodeChar);
    }
}