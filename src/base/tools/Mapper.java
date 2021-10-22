package base.tools;

import base.GlobalVariables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Mapper {
    public int count=0;
    public String err="Mapper";
    private Map<String,Integer> mapper=new HashMap<String,Integer>();
    private ArrayList<Object[]> list=new ArrayList<Object[]>();

    public ArrayList<Object[]> getList() { return list; }
    public Map<String, Integer> getMapper() { return mapper; }

    public void addAutoname(Object object){
        String autoname="auto-"+count;
        count++;
        if(!mapper.containsKey(autoname)){
            int id=list.size();
            list.add(new Object[]{object,autoname});
            mapper.put(autoname,id);
        }else{
            if(GlobalVariables.printErrors)System.err.println("Object with name : "+autoname+" already exists.//"+err);
        }
    }

    public void add(Object object, String name){
        if(!mapper.containsKey(name)){
            int id=list.size();
            list.add(new Object[]{object,name});
            mapper.put(name,id);
        }else{
            if(GlobalVariables.printErrors)System.err.println("Object with name : "+name+" already exists.//"+err);
        }
    }

    public void remove(String name){
        if(mapper.containsKey(name)){
            int id=mapper.get(name);
            mapper.remove(name);
            if(id<list.size()){
                list.remove(id);

                mapper.remove(list.get(list.size()-1));
                list.add(id,list.get(list.size()-1));
                String name0=(String)list.get(id)[1];
                list.remove(list.get(list.size()-1));
                mapper.put(name0,id);

            }else{
                if(GlobalVariables.printErrors)System.err.println("Object with name: "+name+" and id: "+id+" doesn't presents, name removed from mapper.//"+err);
            }
        }else{
            if(GlobalVariables.printErrors)System.err.println("Object with name: "+name+" doesn't presents.//"+err);
        }
    }

    public void remove(int id){
        if(id<list.size()){
            {
                String name=(String)list.get(id)[1];
                list.remove(id);
                if(mapper.containsKey(name)){
                    mapper.remove(name);
                }else{
                    if(GlobalVariables.printErrors)System.err.println("Object with name: "+name+" and id: "+id+" doesn't presents, name removed from mapper.//"+err);
                }
            }
            if(list.size()>0){
                mapper.remove(list.get(list.size()-1));
                list.add(id,list.get(list.size()-1));
                String name=(String)list.get(id)[1];
                list.remove(list.get(list.size()-1));
                mapper.put(name,id);
            }
        }else{
            if(GlobalVariables.printErrors)System.err.println("Object with id: "+id+" doesn't presents.//"+err);
        }
    }

    public void change(int id0,int id1){
        if(id0<list.size()&&id1<list.size()&&id0!=id1){
            int local0=Math.min(id0,id1);
            int local1=Math.max(id0,id1);
            Object[] save0=list.get(local0).clone();
            Object[] save1=list.get(local1).clone();

            list.remove(local1);
            list.remove(local0);

            mapper.remove((String)save0[1]);
            mapper.remove((String)save1[1]);

            list.add(local0,save1);
            list.add(local1,save0);

            mapper.put((String)save1[1],local0);
            mapper.put((String)save0[1],local1);
        }
    }

    public Object get(String name){
        if(mapper.containsKey(name)){
            int id=mapper.get(name);
            if(id<list.size()){
                return list.get(id)[0];
            }else{
                if(GlobalVariables.printErrors)System.err.println("Object with name: "+name+" and id: "+id+" doesn't presents, return null.//"+err);
                return null;
            }
        }else{
            if(GlobalVariables.printErrors)System.err.println("Object with name: "+name+" doesn't presents, return null.//"+err);
            return null;
        }
    }

    public Object get(int id){
        if(id<list.size()){
            return list.get(id)[0];
        }else{
            if(GlobalVariables.printErrors)System.err.println("Object with id: "+id+" doesn't presents, return null.//"+err);
            return null;
        }
    }

    public String name(int id){
        if(id<list.size()){
            return (String)list.get(id)[1];
        }else{
            if(GlobalVariables.printErrors)System.err.println("Name with id: "+id+" doesn't presents, return null.//"+err);
            return null;
        }
    }

    public Integer id(String name){
        if(mapper.containsKey(name)){
            return mapper.get(name);
        }else{
            if(GlobalVariables.printErrors)System.err.println("Id with name: "+name+" doesn't presents, return null.//"+err);
            return null;
        }
    }
    public int size(){
        return list.size();
    }
}
