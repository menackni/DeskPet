package pet;

import base.glfw.GLFW_Manager;
import org.joml.Vector2i;

import java.awt.*;
import java.util.ArrayList;

public class CaptureMouse{
    private static ArrayList<Vector2i> poseHistory=new ArrayList<Vector2i>();
    public static ArrayList<Vector2i>getPoseHistory(){return poseHistory;}

    private static double captureMoveCap=1.0/256.0;
    private static double lastCaptureMove=0;
    private static int captureMoveSize=18;

    public static void upd(){
        double currentTime=(double)System.nanoTime()/(double)1000000000L;
        if(currentTime-lastCaptureMove>captureMoveCap){
            lastCaptureMove=currentTime;
            if(getPoseHistory().size()>=captureMoveSize-1) getPoseHistory().remove(0);
            PointerInfo pointer=MouseInfo.getPointerInfo();
            getPoseHistory().add(new Vector2i(pointer.getLocation().x-GLFW_Manager.getDefaultWindow().getPosition().x,pointer.getLocation().y-GLFW_Manager.getDefaultWindow().getPosition().y));//getInput().getMovePos()
            if(getPoseHistory().get(0).x>GLFW_Manager.getDefaultWindow().getSize().x&&getPoseHistory().get(0).y>GLFW_Manager.getDefaultWindow().getSize().y&&
                    getPoseHistory().get(0).x<GLFW_Manager.getDefaultWindow().getPosition().x&&getPoseHistory().get(0).y<GLFW_Manager.getDefaultWindow().getPosition().y){
                GLFW_Manager.getDefaultWindow().getDraw().canDraw(false);
            }else{
                GLFW_Manager.getDefaultWindow().getDraw().canDraw(true);
            }
        }
    }
}
