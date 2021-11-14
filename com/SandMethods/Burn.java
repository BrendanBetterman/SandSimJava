package com.SandMethods;
import com.*;
public class Burn{
    public static void burnFlamable(int x, int y, Sand sand){
        int radius = 1;
        if(sand.rand.nextInt(4)==1){
            for(int i = -radius; i<=radius; i++){
                for(int u = -radius; u <=radius; u++){
                    try{
                        if(SandType.flamable(sand.sand[x+i][y+u].type)){
                            sand.addtype(x+i, u+y, sand.sand[x][y].type);
                        }
                    }catch(Exception e){}
                }
            }
        }
    }

    public static void burnout(int x, int y, Sand sand,int time){
        if(sand.rand.nextInt(time) ==1){
            sand.remove(x,y);
        }
    }



    /*
    public void burn(){
        
        if(rand.nextInt(4)==1){
            for(int x =-1; x<2; x++){
                try{
                    if(SandType.flamable(sand[this.selected[0]+x][this.selected[1]].type)){
                        addtype(this.selected[0]+x, this.selected[1], sand[this.selected[0]][this.selected[1]].type);
                    }
                }catch(Exception e){}
                x++;
            }
            for(int y=-1; y<2; y++){
                try{
                    if(SandType.flamable(sand[this.selected[0]][this.selected[1]+y].type)){
                        addtype(this.selected[0], this.selected[1]+y, sand[this.selected[0]][this.selected[1]].type);
                    }
                }catch(Exception e){}
                y++;
            }
        }
       
    }*/
}