package base.glfw.window;

import org.joml.Vector2i;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class GLFW_Window{

    private Vector2i size;
    private Vector2i position;
    private long initID;
    private long id;
    private String title="Null";
    private Draw draw=null;


    public long getId(){ return id; }

    private void setPos(Vector2i position){ this.position=position; }
    public void setPosition(int x,int y){
        setPosition(new Vector2i(x,y));
    }
    public void setPosition(Vector2i position){
        setPos(position);
        GLFW.glfwSetWindowPos(getId(), position.x, position.y);
    }

    private void setSz(Vector2i size){ this.size=size; }
    public void setSize(int x,int y){
        setSize(new Vector2i(x,y));
    }
    public void setSize(Vector2i size){
        setSz(size);
        GLFW.glfwSetWindowSize(getId(), size.x, size.y);
    }

    public void setDraw(Draw draw){ this.draw=draw; }
    public void setTitle(String title){ this.title=title; }

    public Vector2i getPosition(){
        int x[]=new int[1];
        int y[]=new int[1];
        GLFW.glfwGetWindowPos(getId(),x,y);
        setPos(new Vector2i(x[0],y[0]));
        return this.position;
    }
    public Vector2i getSize(){
        int x[]=new int[1];
        int y[]=new int[1];
        GLFW.glfwGetWindowSize(getId(),x,y);
        setSz(new Vector2i(x[0],y[0]));
        return this.size;
    }

    public Draw getDraw(){ return draw; }
    public String getTitle(){ return title; }

    public GLFW_Window(Vector2i size,Vector2i position,long initID,String title){
        this.size=size;
        this.title=title;
        this.initID=initID;
        this.position=position;
    }

    public void init(){
        GLFW.glfwWindowHint(GLFW.GLFW_FLOATING, 1);
        GLFW.glfwWindowHint(GLFW.GLFW_TRANSPARENT_FRAMEBUFFER, 1);
        GLFW.glfwWindowHint(GLFW.GLFW_MOUSE_PASSTHROUGH, 1);
        GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, 0);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, 0);

        this.id=GLFW.glfwCreateWindow(this.size.x,this.size.y,getTitle(),0,initID);

        placeWindowByCenter(this.position);

        setContext();
        showWindow();
    }

    public void changeAttributs(int attrib,int value){ GLFW.glfwSetWindowAttrib(getId(),attrib, value); }

    public void placeWindowByCenter(Vector2i position){ setPosition(new Vector2i(position).sub(new Vector2i(getSize()).div(2))); }

    public void setContext(){ GLFW.glfwMakeContextCurrent(getId()); }

    public void showWindow(){ GLFW.glfwShowWindow(getId()); }

    public void hideWindow(){ GLFW.glfwHideWindow(getId()); }

    public void destroy(){ GLFW.glfwDestroyWindow(getId()); }

    public void swapBuff(){ GLFW.glfwSwapBuffers(getId()); }

    public void focus(){ GLFW.glfwFocusWindow(getId());}

    public void loadIcon(String path){
        BufferedImage buff=null;
        try{ buff=ImageIO.read(new FileInputStream(path)); }catch(IOException e){ e.printStackTrace(); }
        if(buff!=null){

            GLFWImage icon=imageToGLFWImage(buff);

            GLFWImage.Buffer ib=GLFWImage.malloc(1);

            ib.put(0,icon);

            GLFW.glfwSetWindowIcon(getId(),ib);
        }
    }

    private GLFWImage imageToGLFWImage(BufferedImage image) {
        if (image.getType() != BufferedImage.TYPE_INT_ARGB_PRE) {
            final BufferedImage convertedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
            final Graphics2D graphics = convertedImage.createGraphics();
            final int targetWidth = image.getWidth();
            final int targetHeight = image.getHeight();
            graphics.drawImage(image, 0, 0, targetWidth, targetHeight, null);
            graphics.dispose();
            image = convertedImage;
        }
        final ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int colorSpace = image.getRGB(j, i);
                buffer.put((byte) ((colorSpace << 8) >> 24));
                buffer.put((byte) ((colorSpace << 16) >> 24));
                buffer.put((byte) ((colorSpace << 24) >> 24));
                buffer.put((byte) (colorSpace >> 24));
            }
        }
        buffer.flip();
        final GLFWImage result = GLFWImage.create();
        result.set(image.getWidth(), image.getHeight(), buffer);

        GLFWImage.Buffer ib=GLFWImage.malloc(1);

        return result;
    }
}
