package base.graphics.renderers;

import base.tools.math;
import org.joml.Vector3f;

public class Camera {
    private float zoom=1.0f;
    private Vector3f camPos=new Vector3f(0,0,0);
    private Vector3f camVec=new Vector3f(0,0,0);

    public void setPos(float x,float y,float z){ setPos(new Vector3f(x,y,z)); }
    public void setPos(Vector3f pos){
        camPos=pos;
    }
    public Vector3f getCamPos() { return camPos; }

    public Vector3f getCamVec() { return camVec; }
    public void setVec(Vector3f vec){
        camVec=vec;
    }

    public float getZoom() { return zoom; }
    public void setZoom(float zoom) {
        if(zoom>64)this.zoom=64;
        else if(zoom<=0.5f) this.zoom=0.5f;
        else this.zoom=zoom;
    }

    public void move(float  x, float z){
        Vector3f localCamVec=new Vector3f(0,camVec.y,0);
        Vector3f dir0= math.rotate(localCamVec,new Vector3f(x,0,z));
        Vector3f dir1=math.rotate(localCamVec,new Vector3f(0,0,0));
        camPos.x=camPos.x+dir0.x-dir1.x;
        camPos.z=camPos.z+dir0.z-dir1.z;
    }

}
