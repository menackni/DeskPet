package base.graphics.assets;

import base.GlobalVariables;
import base.graphics.textures.Texture;
import base.tools.Mapper;

public class AssetPack{
    private int count=0;
    private String name;
    private Mapper assets=new Mapper();
    private Texture texture;

    public int getCount() { return count; }

    public Mapper getAssets() { return assets; }
    public Asset getAsset(String name){ return (Asset)assets.get(name); }
    public Asset getAsset(int id){ return (Asset)assets.get(id); }

    public void addAsset(Asset asset, String name){getAssets().add(asset,name);}
    public void addAsset(Asset asset){getAssets().add(asset,"asset-"+count);count++;}

    public void deleteAsset(int id){getAssets().remove(id);}
    public String getName() { return name; }
    public Texture getTexture() { return texture; }

    public AssetPack(String texturePath,String name){
        this.texture=new Texture(texturePath);
        if(GlobalVariables.printInfo)
        System.out.println("asset_with_new_texture: "+texturePath);
        this.name=name;
    }

    public AssetPack(Texture texture,String name){
        this.texture=texture;
        if(GlobalVariables.printInfo)
        System.out.println("asset_with_exist_texture: "+texture.getId());
        this.name=name;
    }
}
