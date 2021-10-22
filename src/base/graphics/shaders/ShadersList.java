package base.graphics.shaders;
import base.tools.Mapper;

public class ShadersList{
    public static String shadersFolderPath="shaders/";

    private static Mapper shaders=new Mapper();

    public static Mapper getShaders(){ return shaders; }

    public static void add(Shader s, String name){
        getShaders().add(s,name);
    }

    public static Shader get(String name){
        return (Shader)getShaders().get(name);
    }
}
