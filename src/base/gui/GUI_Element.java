package base.gui;

import base.graphics.models.EntityModel;
import org.joml.Vector2f;

public class GUI_Element {
    private EntityModel model;

    private GUI_Element parent;

    private Vector2f pos;
    private Vector2f size;
    private int id;
    private boolean show=true;
    private boolean block=false;
    private boolean taken=false;
    private boolean selected=false;
    private boolean moveOn=false;

    public GUI_Element getParent() { return parent; }

    public Vector2f getPosWithoutParent(){return pos;}
    public Vector2f getPos() { return new Vector2f(pos).add(getParent()!=null?getParent().getPos():new Vector2f(0)); }
    public Vector2f getSize() { return size; }

    public void setSize(Vector2f size){ this.size=size; }
    public void setSize(float x,float y){ this.size=new Vector2f(x,y); }

    public void setPos(Vector2f pos){ this.pos=pos; }
    public void setPos(float x,float y){ this.pos=new Vector2f(x,y); }

    public int getId() { return id; }

    public EntityModel getModel(){ return model; }

    public void setModel(EntityModel model){ this.model=model; }

    public boolean isBlock() { return block; }
    public void setShow(boolean show) { this.show = show; }
    public boolean isShow() {
        if(getParent()!=null)if(!getParent().isShow())return false;
        return show;
    }
    public boolean isSelected() { return selected; }
    public boolean isTaken() { return taken; }
    public boolean isMoveOn() { return moveOn; }
    public void setMoveOn(boolean moveOn) { this.moveOn = moveOn; }

    public boolean moveOverPlace(Vector2f pos,Vector2f size){
        float scale=GUI_Manager.scale;
        Vector2f move=GUI_Manager.getInput().getMovePos();
        if(isShow()&&move.x>=(pos.x+getPos().x)*scale&&move.y>=(pos.y+getPos().y)*scale&&move.x<=(pos.x+getPos().x+size.x)*scale&&move.y<=(pos.y+getPos().y+size.y)*scale){
            return true;
        }
        return false;
    }

    public boolean moveOver(){
        float scale=GUI_Manager.scale;
        Vector2f move=GUI_Manager.getInput().getMovePos();
        if(isShow()&&move.x>=getPos().x*scale&&move.y>=getPos().y*scale&&move.x<=(getPos().x+getSize().x)*scale&&move.y<=(getPos().y+getSize().y)*scale){
            return true;
        }
        return false;
    }

    public boolean moveOverInterior(){
        float scale=GUI_Manager.scale;
        Vector2f move=GUI_Manager.getInput().getMovePos();
        if(isShow()&&move.x>=getPos().x*scale&&move.y>=getPos().y*scale&&move.x<=(getPos().x+getSize().x)*scale&&move.y<=(getPos().y+getSize().y)*scale){
            return true;
        }
        return false;
    }

    public GUI_Element(Vector2f pos, Vector2f size, int id){
        this.pos=pos;
        this.size=size;
        this.id=id;
        this.parent=null;
    }

    public GUI_Element(Vector2f pos, Vector2f size, int id, GUI_Element parent){
        this.pos=pos;
        this.size=size;
        this.id=id;
        this.parent=parent;
    }

    public void upd(){
        if(getParent()!=null&&isShow()){
            if(!getParent().isSelected())deselect();
        }
    }

    public void clickUpd(){ if(isShow())select(); }

    public void releaseUpd(){ taken=false; }

    public void destroy(){ if(getModel()!=null)getModel().getRaw().cleanUp(); }

    public void updDraw(){ }

    public void select(){
        if(isShow()){
            if(parent!=null){
                if(parent.isSelected()){
                    this.selected=true;
                    this.taken=true;
                }
                else{
                    this.selected=false;
                    this.taken=false;
                }
            }else{
                this.selected=true;
                this.taken=true;
            }
        }else{
            this.selected=false;
            this.taken=false;
            this.moveOn=false;
        }
    }

    public void deselect(){
        this.selected=false;
        this.taken=false;
    }

    public void drawModel(int x, int y, float size){

    }

    public void draw(){

    }
}
