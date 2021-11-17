package com;
import com.SandMethods.*;
import com.Lua.*;
public class SandType{
    //https://coolors.co/1f1a20-221d23-4f3824-d1603d-d78d52-ddb967-7b7d26-d0e37f-dceaa0
    private static float RGBToF(int a){
        return (float)((float)a/256);
    }
    public static float[] backgroundColor(){
        return new float[]{RGBToF(220),RGBToF(234),RGBToF(160),1.0f};
    }
    public static void moveFromType(int type,Sand sand){
        switch(type){
            case 1:
            case 2:
            case 3:
                //regular Gravity
                //sand.sandGravity();
                Gravity.sandGravity(sand.selected[0],sand.selected[1],sand);
                break;
            case 5:
                Burn.burnFlamable(sand.selected[0],sand.selected[1],sand);
                Burn.burnout(sand.selected[0],sand.selected[1],sand,16);
                Gravity.structGravity(sand.selected[0],sand.selected[1],sand);
                
                
                //sand.grow();
                break;
            case 6:
                //grow
                Gravity.structGravity(sand.selected[0],sand.selected[1],sand);
                Grow.bush(sand.selected[0],sand.selected[1],sand);
                //sand.structGravity();
                //sand.harden(4);
                break;
            case 7:
                Gravity.waterGravity(sand.selected[0],sand.selected[1],sand);
                break;
            case 8:
                MultiStruct.multiBlockStructure(sand.selected[0],sand.selected[1],sand,2);
                Gravity.structGravity(sand.selected[0],sand.selected[1],sand);   
                break;
            case 15:
            case 16:
            case 17:
                Gravity.clusterGravity(sand.selected[0],sand.selected[1],sand);
                break;
            case 4:
                break;
            default:
                //no Gravity
                if(sand.selected[0] >1){
                    SandMethodLua Lua = SandMethodLua.getInstance();
                if(sand.rand.nextInt(4)==1){
                    
                    Lua.luaSand(type,sand);
                }
                }
                
                
                break;
        }
    }
    public static boolean flamable(int type){
        switch(type){
            case 6:
            case 4:
            case 15:
            case 16:
            case 17:
            
                return true;
            default:
                return false;
        }
    }
    public static float[] colorFromType(int type){
        switch(type){
            case 1:
                return new float[]{RGBToF(221),RGBToF(185),RGBToF(103),1.0f};//sand
            case 2:
            
                return new float[]{RGBToF(209),RGBToF(96),RGBToF(61),1.0f};//red sand
            case 3:
                return new float[]{RGBToF(215),RGBToF(141),RGBToF(82),1.0f};//orange sand
            case 4:
            case 15:
                return new float[]{RGBToF(79),RGBToF(56),RGBToF(36),1.0f};//brown
            case 5:
            case 16:
                return new float[]{RGBToF(34),RGBToF(29),RGBToF(35),1.0f};//black
            case 6:
                return new float[]{RGBToF(123),RGBToF(125),RGBToF(38),1.0f};//green
            case 7:
            case 17:
                return new float[]{RGBToF(96),RGBToF(123),RGBToF(125),1.0f};//blue
            default:
                return new float[]{0.0f,0.0f,0.0f,1.0f};
        }
    }
}