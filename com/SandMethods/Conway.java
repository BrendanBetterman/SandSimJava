package com.SandMethods;
import com.*;
public class Conway{
    private static int getNeighbors(int x, int y,Sand sand, int type){
        int neighbors=0;
        int radius = 1;
        for(int i = -radius; i<=radius; i++){
            for(int u = -radius; u <=radius; u++){
                if(!(u==0 && i == 0)){
                    try{
                        if(sand.sand[i+x][u+y].type == type){
                            neighbors++;
                        }
                    }catch(Exception e){}
                    
                }
            }
        }
        return neighbors;
    }
    public static void ConwayAir(int x, int y, Sand sand,int type){
        int neighbor =0;
            neighbor = getNeighbors(x, y,sand, type);
            if(neighbor == 3){
                sand.addtype(x,y,type);
            }
        
    }
    public static void ConwayLife(int x, int y, Sand sand,int type){
        int neighbor =0;
            neighbor = getNeighbors(x, y,sand, sand.sand[x][y].type);
            if (neighbor<2 || neighbor >3){
                sand.addtype(x,y,type);
            }   
    }
}