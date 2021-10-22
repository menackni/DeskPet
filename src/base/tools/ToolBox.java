package base.tools;

import base.glfw.GLFW_Manager;
import base.graphics.renderers.RenderersList;
import base.opengl.ManagerGL;
import org.lwjgl.opengl.GL11;

public class ToolBox {
    public static void setOrtho(float l, float r, float b, float t){
        GL11.glColor4f(1,1,1,1);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(l,r,b,t, 100, -100);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        RenderersList.get("r2d").updProjectionMatrix(l,r,b,t);
    }

    public static void resetOrtho(){
        RenderersList.get("r2d").updProjectionMatrix(0, GLFW_Manager.getDefaultWindow().getSize().x, GLFW_Manager.getDefaultWindow().getSize().y,0);
        ManagerGL.getDraw().resetOrtho();
    }

    public static boolean isNumeric(String str) { if(str!=null)return str.matches("^(^[0-9.-]+?)");return false; }
    public static boolean isInteger(String str) { if(str!=null)return str.matches("^\\d+?");return false; }

}
