package base.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SupplierMap {
    private ArrayList<Object[]> list = new ArrayList<Object[]>();
    private Map<String,Integer> map=new HashMap<String,Integer>();

    public void clear(){
        map.clear();
        list.clear();
    }

    public void add(Supplier s,String n){
        list.add(new Object[]{s,n});
        map.put(n,list.size()-1);
    }

    public Supplier get(String n){
        if(map.containsKey(n)){
            int id=map.get(n);
            if(id<list.size()){
                return (Supplier)list.get(id)[0];
            }else{
                System.err.println("Supplier with name: "+n+" and id: "+id+" doesn't exists");
                return null;
            }
        }else{
            System.err.println("Supplier "+n+" doesn't exists");
            return null;
        }
    }

    public Supplier get(int id){
        Supplier s=null;
        if(id<list.size()){
            return (Supplier)list.get(id)[0];
        }else{
            System.err.println("Supplier with id: "+id+" doesn't exists");
            return null;
        }
    }

    public void remove(String n){
        if(map.containsKey(n)){
            int id=map.get(n);
            map.remove(n);
            list.remove(id);
        }else{
            System.err.println("Cant remove supplier with name: "+n+" because he doesn't exists");
        }
    }

    public void remove(int id){
        Supplier s=null;
        if(id<list.size()){
            if(map.containsKey(list.get(id)[1])){
                map.remove(list.get(id)[1]);
            }else{
                System.err.println("Cant remove supplier mapper with id: "+id+" because he doesn't exists");
            }
            list.remove(id);
        }else{
            System.err.println("Cant remove supplier with id: "+id+" because he doesn't exists");
        }
    }

    public String getName(int id){
        if(list.size()<id){
            return (String)list.get(id)[1];
        }else{
            System.err.println("Cant get supplier name for id: "+id);
            return null;
        }
    }

    public Integer getId(String n){
        if(map.containsKey(n)){
            return map.get(n);
        }else{
            System.err.println("Cant get supplier id for name: "+n);
            return null;
        }
    }

    public void run(){
        for(int i=0;i<list.size();i++){
            ((Supplier)list.get(i)[0]).get();
        }
    }

    public int getCount(){
        return list.size();
    }

}
