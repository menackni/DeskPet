package base.graphics.frameBuffers;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class FrameBuffer {

    public int WIDTH;
    public int HEIGHT;
     
    private int FrameBuffer;
    private int Texture;
     
    public FrameBuffer(int w, int h) {
    	WIDTH=w;
    	HEIGHT=h;
        initialiseFrameBuffer();
    }
 
    public void cleanUp() {
        GL30.glDeleteFramebuffers(FrameBuffer);
        GL11.glDeleteTextures(Texture);
    }
 
    public void bindFrameBuffer() {
        bindFrameBuffer(FrameBuffer);
    }
     
    public void unbindCurrentFrameBuffer(){
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
    }
 
    public int getTexture() {
        return Texture;
    }
    
    public void updateTexture(int w, int h){
        WIDTH=w;
        HEIGHT=h;
    	GL11.glBindTexture(GL11.GL_TEXTURE_2D, Texture);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, w, h,0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, 0);
        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0,GL11.GL_TEXTURE_2D, Texture, 0);
    }
     
    private void initialiseFrameBuffer() {
        FrameBuffer = createFrameBuffer();
        Texture = createTextureAttachment(WIDTH,HEIGHT);
        unbindCurrentFrameBuffer();
    }
     
    private void bindFrameBuffer(int frameBuffer){
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
    }
 
    private int createFrameBuffer() {
        int frameBuffer = GL30.glGenFramebuffers();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
        GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);
        return frameBuffer;
    }
 
    private int createTextureAttachment( int width, int height) {
        int texture = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height,0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, 0);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, texture, 0);
        return texture;
    }
}