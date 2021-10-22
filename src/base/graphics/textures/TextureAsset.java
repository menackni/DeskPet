package base.graphics.textures;

import org.joml.Vector2f;

public class TextureAsset {
    private Vector2f leftTop;
    private Vector2f rightBottom;
    private Vector2f textureSize;
    private int id;
    private Vector2f offset;

    public TextureAsset(Vector2f leftTop, Vector2f rightBottom, Vector2f textureSize, int id){
        this.leftTop=leftTop;
        this.rightBottom=rightBottom;
        this.id=id;
        this.offset=new Vector2f(0);
        this.textureSize=textureSize;
    }

    public TextureAsset(Vector2f leftTop, Vector2f rightBottom, Vector2f textureSize, int id, Vector2f offset){
        this.leftTop=leftTop;
        this.rightBottom=rightBottom;
        this.id=id;
        this.offset=offset;
    }

    public Vector2f getTextureSize() { return textureSize; }

    public int getId() { return id; }

    public Vector2f getOffset(){ return offset; }

    public Vector2f getLeftTop() { return leftTop; }
    public Vector2f getRightBottom() { return rightBottom; }

    public Vector2f getLeftTopD() { return new Vector2f(leftTop).div(textureSize); }
    public Vector2f getRightBottomD() { return new Vector2f(rightBottom).div(textureSize); }

}
