package base.graphics.tiles;

import base.graphics.assets.Asset;
import org.joml.Vector3f;

public class Tile{
    private QuadTile quad=null;
    private Asset asset=null;
    private Vector3f pos;
    private Vector3f lt;
    private Vector3f rb;

    public void setPos(Vector3f pos){ this.pos=pos; }
    public Vector3f getPos(){ return pos; }

    public void setLt(Vector3f lt){ this.lt=lt; }
    public Vector3f getLt(){ return lt; }

    public void setRb(Vector3f rb){ this.rb=rb; }
    public Vector3f getRb(){ return rb; }

    public void setAsset(Asset asset){ this.asset=asset; }
    public Asset getAsset(){ return asset; }

    public void updTexture(){
        boolean upd=getAsset().upd();

        getQuad().setTexturePosLt(getAsset().getLeftTopD());
        getQuad().setTexturePosRb(getAsset().getRightBottomD());

        if(upd)getQuad().editTexture();
    }

    public void updTextureDontCheckUpd(){
        getQuad().setTexturePosLt(getAsset().getLeftTopD());
        getQuad().setTexturePosRb(getAsset().getRightBottomD());
        getQuad().editTexture();
    }

    public void updGeometry(){
        getQuad().setLt(new Vector3f(getPos()).add(getLt()));
        getQuad().setRb(new Vector3f(getPos()).add(getRb()));
        getQuad().editPos();
    }


    public void setPoint(QuadTile quad){ this.quad=quad; }
    public QuadTile getQuad(){ return quad; }
}
