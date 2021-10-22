package base.graphics.assets;

import org.joml.Vector2f;

public class AnimationDynamic extends Asset{
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

    public Vector2f getSize(){
        return new Vector2f(getLeftTop()).sub(getRightBottom());
    }

    public boolean isUpd(){ return upd; }

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
            return true;
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

    public AnimationDynamic(Asset[] frames, String name) {
        super(null, null, name, null);
        this.frames=frames;
    }
}
