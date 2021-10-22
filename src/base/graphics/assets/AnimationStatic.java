package base.graphics.assets;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;

public class AnimationStatic extends Asset{
    private Asset placeHolder=null;
    private Asset[] frames;
    private int frame=0;
    private int fps=6;
    private boolean upd=false;

    private long lastUpd=System.currentTimeMillis();
    public boolean repeat=true;

    public Asset getAssetFrame() { return frames[frame]; }

    public int getFrame() { return frame; }
    public void setFrame(int frame) { this.frame = frame; }

    public int getFps() { return fps; }
    public void setFps(int fps) { this.fps = fps; }

    public Asset getPlaceHolder(){ return placeHolder; }
    public void setPlaceHolder(Asset placeHolder){ this.placeHolder=placeHolder; }

    public Vector2f getSize(){
        return new Vector2f(getLeftTop()).sub(getRightBottom());
    }

    public void updStatic(int texture){
        if(placeHolder!=null&&upd){
            upd=false;
            Asset frm=getAssetFrame();
            Vector2f size=getSize().absolute();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D,texture);

            GL11.glBegin(GL11.GL_QUADS);

            GL11.glTexCoord2f(frm.getLeftTopD().x,frm.getLeftTopD().y);
            GL11.glVertex2f(placeHolder.getLeftTop().x,placeHolder.getLeftTop().y);

            GL11.glTexCoord2f(frm.getRightBottomD().x,frm.getLeftTopD().y);
            GL11.glVertex2f(placeHolder.getRightBottom().x,placeHolder.getLeftTop().y);

            GL11.glTexCoord2f(frm.getRightBottomD().x,frm.getRightBottomD().y);
            GL11.glVertex2f(placeHolder.getRightBottom().x,placeHolder.getRightBottom().y);

            GL11.glTexCoord2f(frm.getLeftTopD().x,frm.getRightBottomD().y);
            GL11.glVertex2f(placeHolder.getLeftTop().x,placeHolder.getRightBottom().y);

            GL11.glEnd();
        }
    }

    public void zero(){
        lastUpd=System.currentTimeMillis();
        frame=0;
    }

    @Override
    public boolean upd(){
        long crnt=System.currentTimeMillis();
        if(crnt-lastUpd>1000/fps){
            frame++;
            if(frame>=frames.length){
                if(repeat) {
                    frame=0;
                }else{
                    frame=frames.length-1;
                }
            }
            lastUpd=crnt;
            upd=true;
        }
        return false;
    }

    public Vector2f getLeftTop() { return frames[frame].getLeftTop(); }
    public void setLeftTop(Vector2f leftTop) { frames[frame].setLeftTop(leftTop); }

    public Vector2f getRightBottom() { return frames[frame].getRightBottom(); }
    public void setRightBottom(Vector2f rightBottom) { frames[frame].setRightBottom(rightBottom); }

    public AssetPack getParentPack() { return frames[frame].getParentPack();}

    public Vector2f getLeftTopD() { return new Vector2f(getLeftTop().x/getParentPack().getTexture().getW(),getLeftTop().y/getParentPack().getTexture().getH()); }
    public Vector2f getRightBottomD() { return new Vector2f(getRightBottom().x/getParentPack().getTexture().getW(),getRightBottom().y/getParentPack().getTexture().getH()); }

    public AnimationStatic(Asset[] frames, String name) {
        super(null, null, name, null);
        this.frames=frames;
    }
}
