package base.input;

import base.tools.Mapper;
import org.lwjgl.glfw.GLFW;

public class KeyMapper {
    public Integer controll=null;
    public String combo="null";

    private Mapper keys=new Mapper();
    private Mapper keyCombinations=new Mapper();
    private Mapper controlKeys=new Mapper();

    public Mapper getKeys() { return keys; }
    public Mapper getKeyCombinations() { return keyCombinations; }
    public Mapper getControlKeys() { return controlKeys; }

    //First add control key and only after add combinations with this key
    //Better use only ctrl, alt, shift for control keys

    public Key getKey(int key){ return (Key)keys.get(key+""); }
    public void addKey(Key key){ getKeys().add(key,key.getKey()+""); }

    public Key getControlKey(int key){ return (Key)controlKeys.get(key+""); }
    public  void addControlKey(Key key){ getControlKeys().add(key,key.getKey()+""); }

    public KeyCombination getKeyCombination(int key0,int key1){ return (KeyCombination) keyCombinations.get(key0+"_"+key1); }
    public void addKeyCombination(KeyCombination comb){ getKeyCombinations().add(comb,comb.getFirstKey()+"_"+comb.getSecondKey()); }

    public void keyUpd(int key, int action){
        if(action==GLFW.GLFW_RELEASE&&controll!=null){
            if(getControlKey(key)!=null&&key==controll){
                if(key==getControlKey(key).getKey()){
                    controll=null;
                    getControlKey(key).setPress(false);
                }
            }
        }else if(action==GLFW.GLFW_PRESS&&controll==null){
            if(getControlKey(key)!=null){
                if(key==getControlKey(key).getKey()) {
                    controll=getControlKey(key).getKey();
                    getControlKey(key).setPress(true);
                }
            }
        }

        if(controll!=null){
            for (int i=0;i<keyCombinations.size();i++){
                KeyCombination comb=(KeyCombination)keyCombinations.get(i);
                if(controll==comb.getFirstKey()){
                    if((key==comb.getFirstKey()||key==comb.getSecondKey())&&controll==comb.getFirstKey()){
                        if(action==GLFW.GLFW_PRESS&&combo.equals("null")){
                            comb.setPress(true,key);
                        }
                        if(action==GLFW.GLFW_RELEASE){
                            comb.setPress(false,key);
                        }
                        if(comb.isPressFirst()&&comb.isPressSecond()){
                            combo=comb.getFirstKey()+"_"+comb.getSecondKey();
                            break;
                        }else if(combo.equals(comb.getFirstKey()+"_"+comb.getSecondKey())){
                            combo="null";
                        }
                    }
                }
            }
        }else if(controll==null){
            combo = "null";
            for (int i = 0; i < keyCombinations.size(); i++) {
                ((KeyCombination) keyCombinations.get(i)).setPress(false, key);
            }
        }

        if(controll==null){
            if(getKey(key)!=null){
                if(key==getKey(key).getKey()){
                    if(action==GLFW.GLFW_PRESS)getKey(key).setPress(true);
                    else if(action==GLFW.GLFW_RELEASE)getKey(key).setPress(false);
                }
            }
        }
    }
}
