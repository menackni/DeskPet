package base.graphics.renderers;

import base.tools.Mapper;

public class RenderersList {
    private static Mapper renderers=new Mapper();

    public static Mapper getRenderers(){ return renderers; }

    public static void add(Render r, String name){
        getRenderers().add(r,name);
    }

    public static Render get(String name){
        return (Render)getRenderers().get(name);
    }
}
