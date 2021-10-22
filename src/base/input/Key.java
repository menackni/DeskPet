package base.input;

import base.tools.SupplierMap;

import java.util.function.Supplier;

public class Key {
    private int key;
    private boolean press=false;

    SupplierMap clickActions=new SupplierMap();
    SupplierMap pressActions=new SupplierMap();
    SupplierMap releaseActions=new SupplierMap();

    public void addClickSupplier(Supplier action,String name){
        clickActions.add(action,name);
    }
    public void addSupplier(Supplier action,String name){
        pressActions.add(action,name);
    }
    public void addReleaseSupplier(Supplier action,String name){
        releaseActions.add(action,name);
    }

    public void doClickAction(){
        clickActions.run();
    }
    public void doAction(){
        pressActions.run();
    }
    public void doReleaseAction(){
        releaseActions.run();
    }

    public Key(int key){
        this.key=key;
    }

    public int getKey() { return key; }

    public boolean getPress() { return press; }

    public void setPress(boolean press){
        if(!this.press&&press){
            this.press = press;
            doClickAction();
        }else if(this.press&&!press){
            this.press = press;
            doReleaseAction();
        }
    }
}
