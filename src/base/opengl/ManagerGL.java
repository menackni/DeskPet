package base.opengl;

import base.tools.SupplierMap;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class ManagerGL {
    private static DrawGL draw=new DrawGL();

    static SupplierMap before=new SupplierMap();
    static SupplierMap after=new SupplierMap();

    public static SupplierMap getBefore() { return before; }
    public static SupplierMap getAfter() { return after; }

    public static DrawGL getDraw() { return draw; }

    public static void init(){
        System.out.println("OPENGL_INIT_START");
        getBefore().run();
        createCapabilities();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        getAfter().run();
        System.out.println("OPENGL_INIT_END");
    }

    public static void createCapabilities(){ GL.createCapabilities(); }

}
