package base.graphics.textures;

import org.joml.Vector2f;

public class TextureAssetCombine extends TextureAsset{
    private String textureName;
    private String combineName;

    public TextureAssetCombine(Vector2f leftTop, Vector2f rightBottom, Vector2f textureSize, int id, String combineName, String textureName) {
        super(leftTop, rightBottom, textureSize, id);
        this.combineName=combineName;
        this.textureName=textureName;
    }

    public TextureAssetCombine(Vector2f leftTop, Vector2f rightBottom, Vector2f textureSize, int id, Vector2f offset, String combineName, String textureName) {
        super(leftTop, rightBottom, textureSize, id, offset);
        this.combineName=combineName;
        this.textureName=textureName;
    }

    @Override
    public Vector2f getLeftTop() { return new Vector2f(super.getLeftTop()).add(new Vector2f(TextureManager.getCombines(combineName).getTextureData(textureName).pos)); }
    @Override
    public Vector2f getRightBottom() { return new Vector2f(super.getRightBottom()).add(new Vector2f(TextureManager.getCombines(combineName).getTextureData(textureName).pos)); }
    @Override
    public Vector2f getLeftTopD() { return new Vector2f(super.getLeftTop()).add(new Vector2f(TextureManager.getCombines(combineName).getTextureData(textureName).pos)).div(TextureManager.getCombines(combineName).getW(),TextureManager.getCombines(combineName).getH()); }
    @Override
    public Vector2f getRightBottomD() { return new Vector2f(super.getRightBottom()).add(new Vector2f(TextureManager.getCombines(combineName).getTextureData(textureName).pos)).div(TextureManager.getCombines(combineName).getW(),TextureManager.getCombines(combineName).getH()); }
}
