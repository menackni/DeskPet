package base.gui;

import base.tools.Mapper;
import org.joml.Vector2f;

public class GUI_Conteiner extends GUI_Element{
    private int ids=0;
    private int moveOn=0;
    private int select=0;
    private int taken=0;

    private Mapper elements=new Mapper();
    private Mapper elementsAlwaysOnBottom=new Mapper();

    public Mapper getElementsOnBottom() { return elementsAlwaysOnBottom; }
    public void addGuiElimentOnBottom(GUI_Element element,String name){ getElementsOnBottom().add(element,name); }

    public Mapper getElements() { return elements; }


    public int getIds() { return ids; }
    public int genId(){ ids++;return getIds(); }

    public int getSelect() { return select; }
    public void setSelect(int select) { this.select = select; }

    @Override
    public boolean moveOver(){
        if(isShow()){
            float scale=GUI_Manager.scale;
            Vector2f move=GUI_Manager.getInput().getMovePos();
            if(move.x>=getPos().x*scale&&move.y>=getPos().y*scale&&move.x<=(getPos().x+getSize().x)*scale&&move.y<=(getPos().y+getSize().y)*scale)return true;
            else{
                for(int i=0;i<getElements().size();i++){
                    GUI_Element element=(GUI_Element)getElements().get(i);
                    if(element!=null) if(element.moveOver()) return true;
                }
                for(int i=getElementsOnBottom().size()-1;i>=0;i--) {
                    GUI_Element element=(GUI_Element)getElementsOnBottom().get(i);
                    if(element!=null) if(element.moveOver()) return true;
                }
            }
        }
        return false;
    }

    public boolean moveOverInterior(){
        if(isShow()) {
            float scale = GUI_Manager.scale;
            Vector2f move = GUI_Manager.getInput().getMovePos();
            if (move.x >= getPos().x * scale && move.y >= getPos().y * scale && move.x <= (getPos().x + getSize().x) * scale && move.y <= (getPos().y + getSize().y) * scale) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setMoveOn(boolean moveOn){
        super.setMoveOn(moveOn);
        if(!moveOn){
            for(int i=getElements().size()-1;i>=0;i--){
                GUI_Element eliment=(GUI_Element)getElements().get(i);
                eliment.setMoveOn(false);
            }
            for(int i=getElementsOnBottom().size()-1;i>=0;i--) {
                GUI_Element element=(GUI_Element)getElementsOnBottom().get(i);
                element.setMoveOn(false);
            }
        }
    }

    public GUI_Conteiner(Vector2f pos, Vector2f size, int id) {
        super(pos, size, id);
    }

    public GUI_Conteiner(Vector2f pos, Vector2f size, int id, GUI_Element parent){
        super(pos, size, id,parent);
    }

    @Override
    public void clickUpd(){
        super.clickUpd();
        boolean selectOccupation = false;
        for (int i = getElements().size() - 1; i >= 0; i--) {
            GUI_Element element = (GUI_Element) getElements().get(i);
            if (element.isMoveOn() && moveOn == element.getId() && !selectOccupation) {
                selectOccupation = true;
                select = element.getId();
                element.select();
                element.clickUpd();
                getElements().change(i, getElements().size() - 1);
            } else {
                if (select == element.getId()) {
                    select = 0;
                }
                element.deselect();
            }
        }
        for (int i = getElementsOnBottom().size() - 1; i >= 0; i--) {
            GUI_Element element = (GUI_Element) getElementsOnBottom().get(i);
            if (element.isMoveOn() && moveOn == element.getId() && !selectOccupation) {
                selectOccupation = true;
                taken = element.getId();
                select = element.getId();
                element.select();
                element.clickUpd();
            } else {
                if (select == element.getId()) {
                    select = 0;
                }
                element.deselect();
            }
        }
    }

    @Override
    public void releaseUpd(){
        if(isShow()) {
            super.releaseUpd();
            taken = 0;
            for (int i = getElements().size() - 1; i >= 0; i--) {
                GUI_Element element = (GUI_Element) getElements().get(i);
                element.releaseUpd();
            }
            for (int i = getElementsOnBottom().size() - 1; i >= 0; i--) {
                GUI_Element element = (GUI_Element) getElementsOnBottom().get(i);
                element.releaseUpd();
            }
        }
    }

    public void updMove(){
        boolean moveOccupation=false;
        for(int i=getElements().size()-1;i>=0;i--){
            GUI_Element element=(GUI_Element)getElements().get(i);
            if(element.moveOver()&&!moveOccupation){
                moveOccupation=true;
                moveOn=element.getId();
                element.setMoveOn(true);
            }else{
                element.setMoveOn(false);
            }
        }
        for(int i=getElementsOnBottom().size()-1;i>=0;i--){
            GUI_Element element=(GUI_Element)getElementsOnBottom().get(i);
            if(element.moveOver()&&!moveOccupation){
                moveOccupation=true;
                moveOn=element.getId();
                element.setMoveOn(true);
            }else{
                element.setMoveOn(false);
            }
        }
    }

    @Override
    public void upd(){
        super.upd();
        for(int i=getElements().size()-1;i>=0;i--){
            GUI_Element element=(GUI_Element)getElements().get(i);
            element.upd();
        }
        for(int i=getElementsOnBottom().size()-1;i>=0;i--){
            GUI_Element element=(GUI_Element)getElementsOnBottom().get(i);
            element.upd();
        }
        updMove();
    }

    @Override
    public void deselect(){
        super.deselect();
        for(int i=getElements().size()-1;i>=0;i--){
            GUI_Element element=(GUI_Element)getElements().get(i);
            element.deselect();
        }
        for(int i=0;i<getElementsOnBottom().size();i++){
            GUI_Element element=(GUI_Element)getElementsOnBottom().get(i);
            element.deselect();
        }
    }

    @Override
    public void draw() {
        super.draw();
        if(isShow()) {
            for (int i = 0; i < getElementsOnBottom().size(); i++) {
                GUI_Element element = (GUI_Element) getElementsOnBottom().get(i);
                element.draw();
            }
            for (int i = 0; i < getElements().size(); i++) {
                GUI_Element element = (GUI_Element) getElements().get(i);
                element.draw();
            }
        }
    }

    public void drawOnlyExteriorBackground() {
        super.draw();
    }

    public void drawOnlyChildrens(){
        if(isShow()) {
            for (int i = 0; i < getElementsOnBottom().size(); i++) {
                GUI_Element element = (GUI_Element) getElementsOnBottom().get(i);
                element.draw();
            }
            for (int i = 0; i < getElements().size(); i++) {
                GUI_Element element = (GUI_Element) getElements().get(i);
                element.draw();
            }
        }
    }
}
