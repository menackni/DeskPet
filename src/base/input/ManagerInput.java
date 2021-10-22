package base.input;

import base.tools.Mapper;

public class ManagerInput {
    private static Mapper keyMappers=new Mapper();

    public static void addMapper(KeyMapper mapper, String name){ keyMappers.add(mapper, name); }
    public static Mapper getKeyMappers() { return keyMappers; }

    //First add control key and only after add combinations with this key
    //Better use only ctrl, alt, shift for control keys

    public static void addKey(int key, String mapper){ ((KeyMapper)getKeyMappers().get(mapper)).addKey(new Key(key)); }
    public static void addControlKey(int key,String mapper){((KeyMapper)getKeyMappers().get(mapper)).getControlKey(key);}
    public static void addKeyCombination(int key0, int key1, String mapper){ ((KeyMapper)getKeyMappers().get(mapper)).addKeyCombination(new KeyCombination(key0, key1)); }

    public static Key getKey(int key, String mapper) { return ((KeyMapper)getKeyMappers().get(mapper)).getKey(key); }
    public static Key getControlKey(int key, String mapper) { return ((KeyMapper)getKeyMappers().get(mapper)).getControlKey(key); }
    public static KeyCombination getKeyCombination(int key0, int key1, String mapper) { return ((KeyMapper)getKeyMappers().get(mapper)).getKeyCombination(key0,key1); }

    public static void keyUpd(int key, String mapper, int action){
        if((getKeyMappers().get(mapper))!=null)((KeyMapper)getKeyMappers().get(mapper)).keyUpd(key,action);
    }
}
