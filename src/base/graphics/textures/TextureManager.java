package base.graphics.textures;

import base.tools.Mapper;

public class TextureManager {
    private static Mapper Textures=new Mapper();
    private static Mapper Combines=new Mapper();

    public static Mapper getCombines() { return Combines; }
    public static Mapper getTextures() { return Textures; }

    public static TextureCombine getCombines(String name) { return (TextureCombine)Combines.get(name); }
    public static Texture getTextures(String name) { return (Texture)Textures.get(name); }

    public static void init(){
        for(int i=0;i<Combines.size();i++){
            ((TextureCombine)Combines.get(i)).combineTextures();
            System.out.println("TEXTURE_COMBINED:"+Combines.name(i)+" /w:"+((TextureCombine)Combines.get(i)).getW()+" /h:"+((TextureCombine)Combines.get(i)).getH());
        }
    }
}
