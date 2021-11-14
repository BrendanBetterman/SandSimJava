package com.SandMethods;
import com.*;
public class Grow{
    public static void catus(int x, int y, Sand sand){
        if(sand.rand.nextInt(8) == 1){
            try{
                int curTypeA = sand.sand[x+1][y+1].type;
                int curTypeB = sand.sand[x-1][y+1].type;
                if((curTypeA == 0 || curTypeA == 7)&&(curTypeB == 0 || curTypeB == 7)){
                    int tmp = x+sand.rand.nextInt(3)-1;
                    int tmpy = y+1;
                    if (sand.sand[tmp][y+1].type == 0 || sand.sand[x][y+1].type == 7){
                        sand.addtype(tmp,tmpy,sand.sand[x][y].type);  
                    }
                } 
            }catch(Exception e){}
        }
    }

    public static void bush(int x, int y, Sand sand){
        if(sand.rand.nextInt(8) == 1){
            try{
                if(sand.sand[x][y+1].type == 0 || sand.sand[x][y+1].type == 7){
                    int tmp = x+sand.rand.nextInt(3)-1;
                    int tmpy = y+1;
                    if (sand.sand[tmp][y+1].type == 0 || sand.sand[x][y+1].type == 7){
                        sand.addtype(tmp,tmpy,sand.sand[x][y].type);
                    }
                }
            }catch(Exception e){}
        }
    }
    
    public static void harden(int x, int y, Sand sand,int type){
        int belowType = sand.sand[x][y-1].type;
        int curType = sand.sand[x][y].type;
        if(sand.rand.nextInt(2048) ==1 && belowType != 0){
            sand.addtype(x, y, type);
        }
    }

}