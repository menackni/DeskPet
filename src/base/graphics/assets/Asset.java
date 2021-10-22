package base.graphics.assets;

import org.joml.Vector2f;

public class Asset{
    private AssetPack parentPack;
    private String name;
    private Vector2f leftTop;
    private Vector2f rightBottom;

    public Vector2f getLeftTop() { return leftTop; }
    public void setLeftTop(Vector2f leftTop) { this.leftTop = leftTop; }

    public Vector2f getRightBottom() { return rightBottom; }
    public void setRightBottom(Vector2f rightBottom) { this.rightBottom = rightBottom; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public AssetPack getParentPack() { return parentPack; }

    public Vector2f getLeftTopD() { return new Vector2f(leftTop.x/parentPack.getTexture().getW(),leftTop.y/parentPack.getTexture().getH()); }
    public Vector2f getRightBottomD() { return new Vector2f(rightBottom.x/parentPack.getTexture().getW(),rightBottom.y/parentPack.getTexture().getH()); }

    public boolean upd(){
        return false;
    }

    public Asset(Vector2f leftTop, Vector2f rightBottom, String name, AssetPack parentPack){
        this.name=name;
        this.leftTop=leftTop;
        this.rightBottom=rightBottom;
        this.parentPack=parentPack;
    }

    @Override
    public Asset clone(){
        try { return (Asset) super.clone(); } catch (CloneNotSupportedException e) { e.printStackTrace(); }
        return null;
    }
}
