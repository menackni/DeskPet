package base.graphics.textures;

import org.joml.Vector2i;

public class TextureData {
    public String path;
    public Vector2i pos;
    public int w;
    public int h;

    public TextureData(String path){
        this.path=path;
        this.pos=new Vector2i(0);
        this.w=0;
        this.h=0;
    }
}
