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
        //if(rand.nextInt(2) == 1){
        int curX = x;
        int curY = y;
        int curType = sand.sand[curX][curY].type;
        if(sand.sand[curX][curY-1].type == 0){
            sand.swap(curX,curY,curX,curY-1,curType);
            
        }else{
            if(sand.rand.nextInt(2) == 1){
            if(sand.sand[curX][curY].xSpeed ==0 ){
                int tmp =0;
                while (tmp ==0){
                    tmp = sand.rand.nextInt(2)-1;
                }
                sand.sand[curX][curY].xSpeed = tmp;
            }
                try{
                    if(sand.sand[curX+1][curY].type == 0){
                        sand.swap(curX,curY, curX+1, curY, curType);
                        
                    }else if (sand.sand[curX-1][curY].type == 0){
                        sand.swap(curX,curY, curX-1, curY, curType);
                    }else{
                        sand.sand[curX][curY].xSpeed *=-1;
                    }
                }catch(Exception e){}
            }
        }
    }
}