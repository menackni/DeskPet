package base.graphics.textures;

import org.joml.Vector2i;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Texture {
    private int id;
    private int h;
    private int w;

    public int getId(){ return id; }
    public void setId(int id){ this.id=id; }

    public Vector2i getSize(){ return new Vector2i(w,h); }
    public void setSize(Vector2i size){ setW(size.x); setH(size.y);}

    public int getH(){ return h; }
    public void setH(int h){ this.h=h; }
    public int getW(){ return w; }
    public void setW(int w){ this.w=w; }

    public Texture(int id, int w, int h){
        this.id=id;
        this.w=w;
        this.h=h;
    }

    public Texture(String filename){
        BufferedImage buff;
        try {
            buff=ImageIO.read(new FileInputStream(filename));
            int h=buff.getHeight();
            int w=buff.getWidth();
            System.out.println(w+" "+h);
            int[] img;
            img=buff.getRGB(0, 0, w, h, null, 0, w);

            ByteBuffer pixels=BufferUtils.createByteBuffer(w*h*4);
            for(int y=0;y<h;y++){
                for(int x=0;x<w;x++){
                    int pixel=img[w*y+x];
                    pixels.put((byte)((pixel>>16)&0xFF));//r
                    pixels.put((byte)((pixel>>8)&0xFF)); //g
                    pixels.put((byte)((pixel)&0xFF));    //b
                    pixels.put((byte)((pixel>>24)&0xFF));//a
                }
            }
            pixels.flip();
            id=GL11.glGenTextures();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, w, h, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
            pixels.clear();
            buff.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void bind(){
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
    }
}
