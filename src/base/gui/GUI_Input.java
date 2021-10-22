package base.gui;

import base.glfw.GLFW_Manager;
import base.input.Key;
import base.input.KeyMapper;
import base.input.ManagerInput;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

public class GUI_Input {
    private TextBox edit=null;
    private int editPos=0;

    public final String mapperName="gui";
    private KeyMapper mapper=new KeyMapper();

    public KeyMapper getMapper() { return mapper; }

    private GUI_MoveListener move=new GUI_MoveListener();
    private GUI_KeyListener key=new GUI_KeyListener();
    private GUI_CharListener chr=new GUI_CharListener();

    private Vector2f movePos=new Vector2f(0);

    public Vector2f getMovePos() { return movePos; }

    public GUI_MoveListener getMove() { return move; }
    public GUI_KeyListener getKey() { return key; }
    public GUI_CharListener getChar() { return chr; }

    public void init(){
        mapper.addKey(new Key(GLFW.GLFW_MOUSE_BUTTON_LEFT));
        mapper.addKey(new Key(GLFW.GLFW_MOUSE_BUTTON_RIGHT));
        mapper.addKey(new Key(GLFW.GLFW_KEY_BACKSPACE));
        mapper.addKey(new Key(GLFW.GLFW_KEY_ENTER));
        mapper.addKey(new Key(GLFW.GLFW_KEY_LEFT_SHIFT));
        mapper.addKey(new Key(GLFW.GLFW_KEY_DELETE));
        ManagerInput.addMapper(mapper,mapperName);
        GLFW_Manager.getInput().addListener(getKey(),"gui_key");
        GLFW_Manager.getInput().addListener(getMove(),"gui_move");
        GLFW_Manager.getInput().addListener(getChar(),"gui_char");
    }

    public void move(long window, double x, double y){ getMovePos().set((float)x,(float)y); }

    public void setTextBoxToEdit(TextBox textBox, int pos){
        this.edit=textBox;
        this.editPos=pos;
    }

    public TextBox getTextBox() { return edit; }

    public void chr(long window, int unicodeChar){
        if(edit!=null){
            edit.addChar((char)unicodeChar,editPos);
            editPos++;
        }
    }

}
