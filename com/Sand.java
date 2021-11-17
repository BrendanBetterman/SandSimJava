package com;
import java.util.Random;

import com.SandMethods.*;
public class Sand{
    public Random rand = new Random();
    public int rowsFilled = 0; 
    public SandObj[][]sand;
    
    public int[] selected= new int[]{0,0};
    private int count =0;
    public Sand(SandObj[][] grid){
        this.sand = grid;
        for(int i=0; i<sand.length; i++){
            for(int u=0; u<sand[0].length; u++){
                sand[i][u] = new SandObj(0);
            }
        }
    }
    public void generate(int[] seeds){
        for(int i=0; i<seeds.length; i++){
            for(int u=0; u<seeds[i]; u++){
                add(i,u);
            }
        }
    }
    public void removeRow(int y){
        for (int i =0; i<this.sand[0].length-1; i++){
            this.remove(i, y);
        }
    }
    private int highestPoint(int x){
        int tmp =0;
        for(int i=0; i< sand.length; i++){
            if(sand[x][i].type !=0){
                tmp=i;
            }
        }
        return tmp;
    }
    public void remove(int x,int y){
        this.sand[x][y].type = 0;
    }
    public void removeHighest(int x,int y){
        if(y <= highestPoint(x)){
            this.sand[x][highestPoint(x)].type = 0;
        }
    }
    public void addtype(int x,int y ,int type){
        this.sand[x][y].type = type;
    }
    public void addtypeNoDel(int x,int y ,int type,int saveMedium){
        if(this.sand[x][y].type == saveMedium){
            this.sand[x][y].type = type;
        }
        
    }
    public void add(int x,int y){
            this.sand[x][y].type = 1;
    }
    public void add(){
        if(sand.length-rowsFilled >0){
            this.add(rand.nextInt(sand.length-rowsFilled)+rowsFilled,rand.nextInt(sand[0].length));
        }
    }
    public void addcube(int x1,int y1,int radius,int type){
        for(int x = -radius; x<=radius; x++){
            for(int y = -radius; y <=radius; y++){
                try{
                    addtype(x1+x, y1+y, type);
                }catch(Exception e){}
            }
        }
    }
    public void addcubeNoDel(int x1,int y1,int radius,int type,int saveMedium){
        for(int x = -radius; x<=radius; x++){
            for(int y = -radius; y <=radius; y++){
                try{
                    
                        addtypeNoDel(x1+x, y1+y, type,saveMedium);
                    
                    
                }catch(Exception e){}
            }
        }
    }
    public void swap(int x1,int y1,int x2,int y2,int type){
        this.sand[x1][y1].type = 0;
        this.sand[x2][y2].type = type;
    }
    
    public void update(){
        //checkRowFill();
        
        for(int i=0; i<sand[0].length; i++){//i = y u= x
            for(int u=0; u<sand.length; u++){
                //Check If new row has been filled.
                
                if(i>rowsFilled && i<sand[0].length && sand[u][i].type !=0){//changed
                //Check If spot below is empty
                this.selected[0] = u;
                this.selected[1] =i;
                if(sand[u][i].type !=0){
                    SandType.moveFromType(sand[u][i].type,this);
                }
                //this.sandGravity();
                
                }  
            }
        }
    }
}