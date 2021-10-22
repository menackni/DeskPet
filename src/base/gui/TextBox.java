package base.gui;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class TextBox{
    private String checkMatch=null;

    private String string;

    private final Integer maxSize;

    public void setString(String string){this.string=string;}

    public void setCheckMatch(String checkMatch) { this.checkMatch=checkMatch; }
    public String getCheckMatch() { return checkMatch; }

    private ArrayList<Character> chars=new ArrayList<Character>();

    public ArrayList<Character> getChars() { return chars; }

    public String getString(){ return string; }

    public void addChar(char chr){
        if(checkMatch!=null){
            if((chr+"").matches(checkMatch)){
                if(getMaxSize()!=null){
                    if(getChars().size()-1!=getMaxSize()){
                        getChars().add(chr);
                        string=getChars().stream().map(e->e.toString()).collect(Collectors.joining());
                    }
                }else{
                    getChars().add(chr);
                    string=getChars().stream().map(e->e.toString()).collect(Collectors.joining());
                }
            }
        }else {
            if(getMaxSize()!=null){
                if(getChars().size()-1!=getMaxSize()){
                    getChars().add(chr);
                    string=getChars().stream().map(e->e.toString()).collect(Collectors.joining());
                }
            }else{
                getChars().add(chr);
                string=getChars().stream().map(e->e.toString()).collect(Collectors.joining());
            }
        }
    }

    public void addChar(char chr,int pos){
        if(checkMatch!=null) {
            if((chr+"").matches(checkMatch)){
                if(getMaxSize()!=null) {
                    if (getChars().size()-1!=getMaxSize()) {
                        if(pos>=getChars().size())getChars().add(getChars().size(),chr);
                        else getChars().add(pos,chr);
                        string=getChars().stream().map(e->e.toString()).collect(Collectors.joining());
                    }
                }else{
                    if(pos>=getChars().size())getChars().add(getChars().size(),chr);
                    else getChars().add(pos,chr);
                    string=getChars().stream().map(e->e.toString()).collect(Collectors.joining());
                }
            }
        }else{
            if(getMaxSize()!=null) {
                if (getChars().size()-1!=getMaxSize()) {
                    if(pos>=getChars().size())getChars().add(getChars().size(),chr);
                    else getChars().add(pos,chr);
                    string=getChars().stream().map(e->e.toString()).collect(Collectors.joining());
                }
            }else{
                if(pos>=getChars().size())getChars().add(getChars().size(),chr);
                else getChars().add(pos,chr);
                string=getChars().stream().map(e->e.toString()).collect(Collectors.joining());
            }
        }
    }

    public Character getChar(int pos){if(pos>=0&&pos<getChars().size())return getChars().get(pos);else return null;}

    public void delete(int pos){
        if(pos>=0&&pos<getChars().size()) {
            getChars().remove(pos);
            string=getChars().stream().map(e->e.toString()).collect(Collectors.joining());
        }
    }

    public int size(){return getChars().size();}

    public Integer getMaxSize(){return maxSize;}

    public TextBox(){ this.maxSize=null; }

    public TextBox(int maxSize){ this.maxSize=maxSize; }
}
