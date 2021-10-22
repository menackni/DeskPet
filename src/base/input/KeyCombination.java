package base.input;

import base.tools.SupplierMap;

import java.util.function.Supplier;

public class KeyCombination{
    private int firstKey;
    private int secondKey;
    private boolean pressFirst=false;
    private boolean pressSecond=false;

    public KeyCombination(int firstKey, int secondKey) {
        this.firstKey=firstKey;
        this.secondKey=secondKey;
    }

    SupplierMap clickActions=new SupplierMap();
    SupplierMap pressActions=new SupplierMap();
    SupplierMap releaseActions=new SupplierMap();

    public void addClickSupplier(Supplier action, String name){
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

    public int getFirstKey(){ return firstKey; }
    public boolean isPressFirst() { return pressFirst; }

    public int getSecondKey() { return secondKey; }
    public boolean isPressSecond() { return pressSecond; }

    public void setPress(boolean press, int key){
        if(key==firstKey){
            this.pressFirst=press;
            if(!press){
                this.pressSecond=press;
                if(this.pressSecond){
                    doReleaseAction();
                }
            }
        }if(key==this.secondKey&&this.pressFirst){
            if(!this.pressSecond&&press){
                this.pressSecond=press;
                doClickAction();
            }else if(this.pressSecond&&!press){
                this.pressSecond=press;
                doReleaseAction();
            }
        }
    }
}
