package base.gui;

import base.tools.Mapper;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class GUI_Manager {
    private static boolean processInput=false;

    private static int ids=0;
    private static int moveOn=0;
    private static int select=0;
    private static int taken=0;
    public static float scale=1;
    private static GUI_Input input=new GUI_Input();
    private static Mapper elements=new Mapper();
    private static Mapper elementsAlwaysOnBottom=new Mapper();

    public static void addGuiEliment(GUI_Element element,String name){ getElements().add(element,name); }

    public static Mapper getElements() { return elements; }
    public static GUI_Input getInput() { return input; }

    public static int getIds() { return ids; }
    public static int genId(){ ids++;return getIds(); }

    public static void init(float scale0){
        scale=scale0;
        init();
    }

    public static void init(){
        if(processInput){
            getInput().init();
            input.getMapper().getKey(GLFW.GLFW_MOUSE_BUTTON_LEFT).addClickSupplier(()->{ clickUpd();return true; }, "clickMain");
            input.getMapper().getKey(GLFW.GLFW_MOUSE_BUTTON_LEFT).addReleaseSupplier(()->{ releaseUpd();return true; }, "releaseMain");
        }
    }

    public static void clickUpd(){
        boolean selectOccupation=false;
        for(int i=getElements().size()-1;i>=0;i--){
            GUI_Element eliment=(GUI_Element)getElements().get(i);
            if(eliment.isMoveOn()&&moveOn==eliment.getId()&&!selectOccupation){
                selectOccupation=true;
                taken=eliment.getId();
                select=eliment.getId();
                eliment.select();
                eliment.clickUpd();
                getElements().change(i,getElements().size()-1);
            }else{
                if(select==eliment.getId()){
                    select=0;
                }
                eliment.deselect();
            }
        }
    }

    public static void releaseUpd(){
        taken=0;
        for(int i=getElements().size()-1;i>=0;i--){
            GUI_Element eliment=(GUI_Element)getElements().get(i);
            eliment.releaseUpd();
        }
    }

    public static void updMove(){
        boolean moveOccupation=false;
        for(int i=getElements().size()-1;i>=0;i--){
            GUI_Element eliment=(GUI_Element)getElements().get(i);
            if(eliment.moveOver()&&!moveOccupation){
                moveOccupation=true;
                moveOn=eliment.getId();
                eliment.setMoveOn(true);
            }else{
                eliment.setMoveOn(false);
            }
        }
    }

    public static void upd(){
        for(int i=getElements().size()-1;i>=0;i--){
            GUI_Element eliment=(GUI_Element)getElements().get(i);
            eliment.upd();
        }
        updMove();
    }

    public static void draw(){
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,0);
        for(int i=0;i<getElements().size();i++){
            GUI_Element eliment=(GUI_Element)getElements().get(i);
            eliment.draw();
        }
    }
}
