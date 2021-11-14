package com.SandMethods;
import com.*;
public class Gravity{
    public static void structGravity(int x, int y, Sand sand){
        try{
            if(!(sand.sand[x-1][y-1].type != 0 || sand.sand[x+1][y-1].type != 0 )){//sides are touching
                if(sand.sand[x][y-1].type == 0 ){
                    sand.swap(x, y, x, y-1,sand.sand[x][y].type);
                }
            }
        }catch(Exception e){}
    }

    public static void clusterGravity(int x, int y, Sand sand){
        
        try{
            if((sand.sand[x][y-1].isfalling || !(sand.sand[x+1][y].isfalling && sand.sand[x-1][y].isfalling)) && sand.sand[x][y-1].type==0){
                sand.sand[x][y].isfalling =true;
                sand.swap(x,y,x,y-1,sand.sand[x][y].type);
            }else{
                sand.sand[x][y].isfalling = false;
            }
        }catch(Exception e){}
    }

    public static void sandGravity(int x, int y, Sand sand){
        sand.sand[x][y].isfalling = true;
        if(sand.sand[x][y-1].type == 0){
            //move down{up matrix inverted}
            sand.swap(x, y, x, y-1,sand.sand[x][y].type);
        }else {
            //both side are open randomly pick side.
            if(x!=sand.sand.length-1 && x!=0){
                if(sand.sand[x-1][y-1].type==0 && sand.sand[x+1][y-1].type==0 ){
                    if(sand.rand.nextBoolean()){
                        sand.swap(x, y, x+1, y-1,sand.sand[x][y].type);
                    }else{
                        sand.swap(x, y, x-1, y-1,sand.sand[x][y].type);
                    }
                }else if(sand.sand[x-1][y-1].type==0){
                    sand.swap(x, y, x-1, y-1,sand.sand[x][y].type);
                }else if(sand.sand[x+1][y-1].type==0){
                    sand.swap(x, y, x+1, y-1,sand.sand[x][y].type);
                }else{
                    sand.sand[x][y].isfalling = false;
                }
            }else{
                if(x!=0){
                    if(sand.sand[x-1][y-1].type==0){
                        sand.swap(x, y, x-1, y-1,sand.sand[x][y].type);
                    }
                }else if(sand.sand[x+1][y-1].type==0){
                    sand.swap(x, y, x+1, y-1,sand.sand[x][y].type);
                }else{
                    sand.sand[x][y].isfalling = false;
                }
            } 
        }
    }
    public static void waterGravity(int x, int y, Sand sand){
        int curType = sand.sand[x][y].type;
        int dir = sand.sand[x][y].xSpeed;
        //check below
        while(dir == 0){
                dir = -1;
                
               // System.out.println(sand.sand[x][y].xSpeed);
            }
        sand.sand[x][y].xSpeed = dir;
        if(sand.sand[x][y-1].type == 0){
            sand.swap(x,y,x,y-1,curType);
        }else{
                try{
                if(sand.sand[x+dir][y-1].type == 0){
                    sand.swap(x,y,x+dir,y-1,curType);
                    sand.sand[x][y].xSpeed *= -1;
                }else
                if(sand.sand[x-dir][y-1].type == 0){
                    sand.swap(x,y,x-dir,y-1,curType);
                    
                }else
                if (sand.sand[x-dir][y].type == 0){
                    sand.swap(x,y,x-dir,y,curType);   
                                   
                }else
                if (sand.sand[x+dir][y].type == 0){
                    sand.swap(x,y,x+dir,y,curType); 
                    sand.sand[x][y].xSpeed *= -1;  

                }}catch(Exception e){}
                try{
                    if (sand.sand[x-dir][y].type == 7){
                        sand.sand[x][y].xSpeed =sand.sand[x-dir][y].xSpeed;                  
                    }else
                    if (sand.sand[x+dir][y].type == 7){
                        sand.sand[x][y].xSpeed =sand.sand[x+dir][y].xSpeed;    
                    }}catch(Exception e){}
        }

    }
    
}