package base.graphics.assets;

import base.graphics.textures.TextureCombine;
import org.joml.Vector2f;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class LoadAsset{
    private static String texturesPath="Assets/textures/";
    private static String packsPath="Assets/packs/";
    private static String animationsPath="Assets/animations/";

    public static void init(){
        PackManager.addAsset(null,"null");
        scanForAssetPacks();
        scanForAnimations();
    }

    public static void scanForAssetPacks(){
        File f0[]=new File(packsPath).listFiles();
        if(f0!=null&&f0.length>0){
            for(int i=0;i<f0.length;i++){
                File f1=new File(f0[i].getPath());
                if(f1.getName().substring(f1.getName().lastIndexOf(".")+1).equals("assetpack")){
                    System.out.println("FIND ASSET: "+f1.getName());
                    loadAssetPackCombine(f1);
                }
            }
        }
    }

    public static void scanForAnimations(){
        File f0[]=new File(animationsPath).listFiles();
        if(f0!=null&&f0.length>0){
            for(int i=0;i<f0.length;i++){
                File f1=new File(f0[i].getPath());
                if(f1.getName().substring(f1.getName().lastIndexOf(".")+1).equals("animation")){
                    System.out.println("FIND ANIMATION: "+f1.getName());
                    loadAnimation(f1);
                }
            }
        }
    }

    public static void loadAnimation(File file){
        Scanner scn=null;
        try {scn=new Scanner(file);}catch(FileNotFoundException e){e.printStackTrace();}

        scn.useLocale(Locale.US);

        int prePos=0;
        if(scn.hasNext()){
            ArrayList<Asset> assets=new ArrayList<Asset>();
            Asset placeholder=null;
            int speed=6;
            int count=0;
            while(1==1){
                if(scn.hasNext()){
                    String thisScan=scn.next();
                    if(thisScan.equals("!s/")) {
                        speed=scn.nextInt();
                    }else if(thisScan.equals("!/")){
                        String assetName=null;
                        String packName=null;

                        while(1==1){
                            String thisScan0=scn.next();
                            if (thisScan0.equals("!as/")) {
                                assetName=scn.next();
                            }else if(thisScan0.equals("!ap/")) {
                                packName=scn.next();
                            }else if(thisScan0.equals("!|")){
                                break;
                            }
                        }
                        if(assetName!=null&&packName!=null){
                            Asset asset=PackManager.getAsset(assetName);
                            assets.add(asset);
                            count++;
                        }
                    }else if(thisScan.equals("!ph/")){
                        String assetName=null;
                        String packName=null;

                        while(1==1){
                            String thisScan0=scn.next();
                            if (thisScan0.equals("!as/")) {
                                assetName=scn.next();
                            }else if(thisScan0.equals("!ap/")) {
                                packName=scn.next();
                            }else if(thisScan0.equals("!|")){
                                break;
                            }
                        }
                        if(assetName!=null&&packName!=null){
                            Asset asset=PackManager.getAsset(assetName);
                            placeholder=asset;
                        }
                    }else if(thisScan.equals("!|")){
                        if(count>0){

                            Asset[] arr=new Asset[assets.size()];
                            for(int i=0;i<assets.size();i++){
                                arr[i]=assets.get(i);
                            }

                            if(placeholder!=null){
                                AnimationStatic animStc=new AnimationStatic(arr,file.getPath().substring(file.getPath().lastIndexOf("\\")+1,file.getPath().lastIndexOf(".")));
                                animStc.setFps(speed);
                                animStc.setPlaceHolder(placeholder);
                                PackManager.addStaticAnimation(animStc,animStc.getName());
                                PackManager.addAsset(animStc);
                            }else{
                                AnimationDynamic animDun=new AnimationDynamic(arr,file.getPath().substring(file.getPath().lastIndexOf("\\")+1,file.getPath().lastIndexOf(".")));
                                animDun.setFps(speed);
                                PackManager.addAsset(animDun);
                            }
                        }
                        break;
                    }
                }else{
                    System.out.println("I think this Animation is corrupted");
                    break;
                }
            }
        }else{
            System.out.println("Nothing inside this Animation");
        }
    }

    public static void loadAssetPackCombine(File file){
        TextureCombine textureCombine=PackManager.getTextureCombine();
        Scanner scn=null;
        try {scn=new Scanner(file);}catch(FileNotFoundException e){e.printStackTrace();}

        scn.useLocale(Locale.US);

        int prePos=0;
        if(scn.hasNext()){
            int count=0;
            AssetPack assetPack=null;
            while(1==1){
                if(scn.hasNext()){
                    String thisScan=scn.next();
                    if(thisScan.equals("!?")) {
                        prePos=textureCombine.getW();
                        System.out.println("prePos:"+prePos);
                        textureCombine.addTextureToCombine(texturesPath+scn.next(),file.getPath().substring(file.getPath().lastIndexOf("\\")+1,file.getPath().lastIndexOf(".")));
                        assetPack=new AssetPack(textureCombine,file.getPath().substring(file.getPath().lastIndexOf("\\")+1,file.getPath().lastIndexOf(".")));
                    }else if(thisScan.equals("!/")){
                        Asset asset=null;

                        Vector2f lt=null;
                        Vector2f rb=null;

                        String name=null;

                        while(1==1){
                            String thisScan0=scn.next();
                            if (thisScan0.equals("!lt/")) {
                                lt=new Vector2f((float)scn.nextDouble()+prePos,(float)scn.nextDouble());
                            }else if(thisScan0.equals("!rb/")) {
                                rb=new Vector2f((float)scn.nextDouble()+prePos,(float)scn.nextDouble());
                            }else if(thisScan0.equals("!nm/")) {
                                name=scn.next();
                            }else if(thisScan0.equals("!|")){
                                break;
                            }
                        }
                        if(lt!=null&&rb!=null){
                            if(name!=null){
                                asset=new Asset(lt,rb,name,assetPack);
                                assetPack.addAsset(asset,name);
                                PackManager.addAsset(asset,name);
                            }else{
                                asset=new Asset(lt,rb,file.getPath().substring(file.getPath().lastIndexOf("\\")+1,file.getPath().lastIndexOf("."))+"-"+count,assetPack);
                                assetPack.addAsset(asset);
                                PackManager.addAsset(asset);
                            }
                            System.out.println(asset.getName()+" "+asset.getParentPack().getName());
                            count++;
                        }
                    }else if(thisScan.equals("!|")){
                        if(count>0){
                            PackManager.addPack(assetPack);
                        }
                        break;
                    }
                }else{
                    System.out.println("I think this AssetPack is corrupted");
                    break;
                }
            }
        }else{
            System.out.println("Nothing inside this AssetPack");
        }
    }
}
