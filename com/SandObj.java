package com;
public class SandObj{
    public int type;
    public int xSpeed;
    public int ySpeed;
    public int level;
    public boolean isfalling = true;
    public SandObj(int type){
        this.type = type;
        this.level =1;
        
    }
    public void set(int type){
        this.type = type;
    }
    public void set(int type,int level){
        this.set(type);
        this.level = level;
    }
}