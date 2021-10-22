package base.graphics.textures;

import base.tools.Mapper;
import org.joml.Vector2i;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TextureCombine extends Texture{

    //public int prePos=0;

    public Mapper texturesData=new Mapper();

    public TextureCombine(){
        super(0,0,0);
    }

    public Vector2i getTexturePosition(String name){
        TextureData data=(TextureData)texturesData.get(name);
        return new Vector2i(data.pos.x,0);
    }

    public Vector2i getTextureSize(String name){
        TextureData data=(TextureData)texturesData.get(name);
        return new Vector2i(data.w,data.h);
    }

    public TextureData getTextureData(String name){
        return (TextureData)texturesData.get(name);
    }

    public void addTextureToCombine(String path,String name){
        texturesData.add(new TextureData(path),name);
        try {
            BufferedImage buff= ImageIO.read(new FileInputStream(new File(path)));
            //prePos+=buff.getWidth();
            setW(getW()+buff.getWidth());
            setH(buff.getHeight()>getH()?buff.getHeight():getH());
        }catch(IOException e) {e.printStackTrace();}
    }

    public void combineTextures(){
        TextureLoader textureLocal = new TextureLoader();

        int w=0;
        int h=0;

        try {
            for(int i=0;i<texturesData.size();i++){
                TextureData data=(TextureData)texturesData.get(i);
                BufferedImage buff= ImageIO.read(new FileInputStream(data.path));
                System.out.println(data.path);

                int wLocal=buff.getWidth();
                int hLocal=buff.getHeight();

                data.pos=new Vector2i(w,0);
                data.w=wLocal;
                data.h=hLocal;

                w+=wLocal;
                if(hLocal>h){h=hLocal;}
            }
        } catch (IOException e) {e.printStackTrace();}

        textureLocal.setSize(w,h);
        textureLocal.startLoad();

        int x=0;
        int y=0;
        for(int i=0;i<texturesData.size();i++){
            TextureData data=(TextureData)texturesData.get(i);
            int wLocal=data.w;
            int hLocal=data.h;
            textureLocal.loadPart(data.path,data.pos.x,wLocal,hLocal);
            x+=wLocal;
        }
        setId(textureLocal.getId());
        setW(textureLocal.getW());
        setH(textureLocal.getH());
    }

}
