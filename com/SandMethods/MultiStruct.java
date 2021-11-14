package com.SandMethods;
import com.*;
public class MultiStruct{
    private static int[][] catus = new int[][]{
        {0,0,0,0,0},
        {0,0,6,0,0},
        {6,0,6,0,6},
        {6,0,6,0,6},
        {0,6,6,6,0},
        {0,0,6,0,0},
        {0,0,6,0,0}};
    private static int[][] house = new int[][]{
        {0,0,0,0,0},
        {0,0,16,0,0},
        {16,16,16,16,16},
        {0,15,15,15,0},
        {0,15,17,15,0},
        {0,15,17,15,0},
        {0,15,17,15,0}};
    private static int[][] getStruct(int id){
        int[][] tmp = new int[5][5];
        if(id == 1){
            return catus;
        }else if(id ==2){
            return house;
        }
        return tmp;
    }
    public static void multiBlockStructure(int x, int y,Sand sand,int id){
        int[][] struct = getStruct(id);
        sand.sand[x][y].type = 0;
        for(int i =0; i< struct.length+1; i++){
            for(int u =0; u<struct[0].length+1; u++){
                try{
                    int curType = sand.sand[i+x][u+y].type;
                    if(curType == 0 || curType == 6){//if nothing there
                        sand.sand[i+x][u+y].type = struct[struct[0].length-u][struct.length-i];
                    }
                }catch(Exception e){}
            }
        }
    }
}