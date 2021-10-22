package pet;

import base.glfw.GLFW_Manager;
import base.opengl.ManagerGL;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class TrayIcon{
    private boolean isPetScreen=true;
    private boolean isPetScreenOverlay=true;
    private boolean isPetScreenOnTop=true;

    private PopupMenu menu;
    private java.awt.TrayIcon trayIcon;
    private SystemTray tray;
    private Image icon;

    private int size=Main.petSize;

    public void init(){
        icon=Toolkit.getDefaultToolkit().getImage("Assets/textures/tray.gif");
        trayIcon=new java.awt.TrayIcon(icon,"Pet app");
        tray=SystemTray.getSystemTray();

        menu = new PopupMenu();

        //PET//PET//PET//PET//PET//PET//PET//PET//PET//PET//PET
        MenuItem petButton = new MenuItem("Pet: "+Main.pet.getIcon());
        petButton.addActionListener(e->{
            Main.pet.setIcon(Main.pet.getIcon()=="dog"?"cat":"dog");
            petButton.setLabel("Pet: "+Main.pet.getIcon());
            GLFW_Manager.getDefaultWindow().getDraw().addTask(()->{Main.pet.checkForModelAndCreate();return false;});
        });
        menu.add(petButton);
        menu.addSeparator();

        //SIZE//SIZE//SIZE//SIZE//SIZE//SIZE//SIZE//SIZE//SIZE//SIZE
        MenuItem sizeButton = new MenuItem("Pet size: "+size);
        sizeButton.addActionListener(e->{
            switch(size){
                case 16:
                    size=32;
                    break;
                case 32:
                    size=64;
                    break;
                case 64:
                    size=128;
                    break;
                case 128:
                    size=256;
                    break;
                default:
                    size=16;
                    break;
            }

            sizeButton.setLabel("Pet size: "+size);

            Main.petSize=size;
            Main.windowSize=(int)(Main.petSize*1.3f);
            if(Main.windowSize<160)Main.windowSize=160;

            Main.pet.setSize(size);
            Main.pet.setPosition((int)(Main.windowSize/2.0f),(int)(Main.windowSize/2.0f));

            GLFW_Manager.getDefaultWindow().changeAttributs(GLFW.GLFW_RESIZABLE, 1);
            GLFW_Manager.getDefaultWindow().setSize(Main.windowSize,Main.windowSize);
            GLFW_Manager.getDefaultWindow().changeAttributs(GLFW.GLFW_RESIZABLE, 0);

            GLFW_Manager.getDefaultWindow().getDraw().addTask(()->{
                ManagerGL.getDraw().resetViewport();

                return false;
            });
        });
        menu.add(sizeButton);
        menu.addSeparator();

        //FRAMECAP//FRAMECAP//FRAMECAP//FRAMECAP//FRAMECAP//FRAMECAP
        MenuItem framecapButton = new MenuItem("Framerate: "+GLFW_Manager.getFrameCap());
        framecapButton.addActionListener(e->{
            switch(GLFW_Manager.getFrameCap()){
                case 30:
                    GLFW_Manager.setFrameCap(60);
                    break;
                case 60:
                    GLFW_Manager.setFrameCap(120);
                    break;
                default:
                    GLFW_Manager.setFrameCap(30);
                    break;
            }
            framecapButton.setLabel("Framerate: "+GLFW_Manager.getFrameCap());
            makeWindow();
        });
        menu.add(framecapButton);
        menu.addSeparator();

        //ONTOP//ONTOP//ONTOP//ONTOP//ONTOP//ONTOP//ONTOP//ONTOP
        MenuItem onTopButton = new MenuItem("On top: ON");
        onTopButton.addActionListener(e->{
            onTopButton.setLabel("On top: "+(isPetScreenOnTop?"OFF":"ON"));
            isPetScreenOnTop=isPetScreenOnTop?false:true;
            makeWindow();
        });
        menu.add(onTopButton);
        menu.addSeparator();

        //WINDOW//WINDOW//WINDOW//WINDOW//WINDOW//WINDOW//WINDOW
        MenuItem windowButton = new MenuItem("Window: OFF");
        windowButton.addActionListener(e->{
            windowButton.setLabel("Window: "+(isPetScreenOverlay?"ON":"OFF"));
            isPetScreenOverlay=isPetScreenOverlay?false:true;
            makeWindow();
        });
        menu.add(windowButton);
        menu.addSeparator();

        //EXIT//EXIT//EXIT//EXIT//EXIT//EXIT//EXIT//EXIT//EXIT//EXIT
        MenuItem exitButton = new MenuItem("Exit");
        exitButton.addActionListener(e->{
            GLFW.glfwSetWindowShouldClose(GLFW_Manager.getDefaultWindow().getId(),true);
        });
        menu.add(exitButton);

        trayIcon.setPopupMenu(menu);
        trayIcon.setImageAutoSize(true);

        try{tray.add(trayIcon);}catch(AWTException e){ e.printStackTrace(); }
    }

    public void delite(){
        tray.remove(trayIcon);
    }

    public void makeWindow(){
        if(isPetScreen){
            GLFW_Manager.getDefaultWindow().changeAttributs(GLFW.GLFW_FLOATING, isPetScreenOnTop?1:0);
            GLFW_Manager.getDefaultWindow().changeAttributs(GLFW.GLFW_TRANSPARENT_FRAMEBUFFER, 1);
            GLFW_Manager.getDefaultWindow().changeAttributs(GLFW.GLFW_MOUSE_PASSTHROUGH, isPetScreenOverlay?1:0);
            GLFW_Manager.getDefaultWindow().changeAttributs(GLFW.GLFW_DECORATED, isPetScreenOverlay?0:1);
        }else{
            GLFW_Manager.getDefaultWindow().changeAttributs(GLFW.GLFW_FLOATING, 0);
            GLFW_Manager.getDefaultWindow().changeAttributs(GLFW.GLFW_TRANSPARENT_FRAMEBUFFER, 1);
            GLFW_Manager.getDefaultWindow().changeAttributs(GLFW.GLFW_MOUSE_PASSTHROUGH, 0);
            GLFW_Manager.getDefaultWindow().changeAttributs(GLFW.GLFW_DECORATED, 1);
        }
    }
}
