package base.opengl;

import base.glfw.window.Draw;
import base.graphics.renderers.Camera;
import org.lwjgl.opengl.GL11;

public class DrawGL extends Draw {

    @Override
    public void draw(){
        if(isCanDraw()) {
            getOpen().run();

            GL11.glClearColor(0, 0, 0, 0);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);

            getInside().run();
            super.doTasks();
            getClose().run();
        }
    }

    public void deepTest(boolean bool){
        if(bool)GL11.glEnable(GL11.GL_DEPTH_TEST);else GL11.glDisable(GL11.GL_DEPTH_TEST);
    }

    public void setOrthoOnCamera(Camera cam){
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(cam.getCamPos().x-((getWindow().getSize().x/2.0f)/cam.getZoom()), cam.getCamPos().x+((getWindow().getSize().x/2.0f)/cam.getZoom()), cam.getCamPos().y+((getWindow().getSize().y/2.0f)/cam.getZoom()), cam.getCamPos().y-((getWindow().getSize().y/2.0f)/cam.getZoom()), -100, 100);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
    }
    
    public void resetOrtho(){
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, getWindow().getSize().x, getWindow().getSize().y, 0, 2048, -2048);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
    }

    public void resetViewport(){
        GL11.glViewport(0, 0, getWindow().getSize().x, getWindow().getSize().y);
    }
}
