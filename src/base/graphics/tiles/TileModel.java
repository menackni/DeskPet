package base.graphics.tiles;

import base.tools.Mapper;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class TileModel{
    private int maxTiles;
    private QuadModel quadModel;
    private Mapper tiles=new Mapper();

    public int getMaxTiles(){ return maxTiles; }

    public QuadModel getQuadModel(){ return quadModel; }

    public Mapper getTiles() { return tiles; }

    public Tile getTile(String name){
        return (Tile)getTiles().get(name);
    }

    public Tile getTile(int id){
        return (Tile)getTiles().get(id);
    }

    public void addTile(Tile tile,String name){
        if(getTiles().size()<getMaxTiles()) getTiles().add(tile,name);
    }

    public TileModel(int maxTiles){
        this.maxTiles=maxTiles;
        quadModel=new QuadModel(getMaxTiles());
        for(int i=0;i<getMaxTiles();i++){
            Tile tile=new Tile();
            tile.setPoint(getQuadModel().getQuadTile(i));
            tile.setAsset(null);

            tile.getQuad().setLt(new Vector3f(0,0,0));
            tile.getQuad().setRb(new Vector3f(0,0,0));
            tile.getQuad().setTexturePosLt(new Vector2f(0,0));
            tile.getQuad().setTexturePosRb(new Vector2f(0,0));
            tile.getQuad().editFull();

            addTile(tile,"t"+i);
        }
    }

    public void upd(){
        for(int i=0;i<getTiles().size();i++){
            getTile(i).updTexture();
        }
    }

    public void cleanup(){
        getQuadModel().getModel().cleanUp();
    }
}
