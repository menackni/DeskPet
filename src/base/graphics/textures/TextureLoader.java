package base.graphics.textures;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class TextureLoader {
    private int id;
    private int h;
    private int w;

    public void setSize(int w,int h){
        this.w=w;this.h=h;
    }

    public void startLoad(){
        id=GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, w, h, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, 0);
    }

    public void loadPart(String file,int x, int w,int h){
        BufferedImage buff;
        try {
            buff = ImageIO.read(new FileInputStream(new File(file)));
            h = buff.getHeight();
            w = buff.getWidth();
            int[] img;
            img = buff.getRGB(0, 0, w, h, null, 0, w);
            ByteBuffer pixels=BufferUtils.createByteBuffer(w*h*4);
            for(int ly=0;ly<h;ly++){
                for(int lx=0;lx<w;lx++){
                    int pixel=img[w*ly+lx];
                    pixels.put((byte)((pixel>>16)&0xFF));//r
                    pixels.put((byte)((pixel>>8)&0xFF)); //g
                    pixels.put((byte)((pixel)&0xFF));    //b
                    pixels.put((byte)((pixel>>24)&0xFF));//a
                }
            }
            pixels.flip();
            GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, x, 0, w, h, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
            pixels.clear();
            buff.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getW(){
        return w;
    }
    public int getH(){
        return h;
    }
    public int getId(){
        return id;
    }
}
