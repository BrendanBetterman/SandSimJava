package com;
import org.lwjgl.*;

import java.lang.ModuleLayer.Controller;
import java.nio.*;
import static org.lwjgl.glfw.GLFW.*;
import java.util.HashMap;

public class Controllers{
	HashMap<String,Integer> KeyDict = new HashMap<String,Integer>();
	public int[] lastPressed = new int[5];
    public Controllers(){
		String tmp = "Key_";
		for(int i=0; i <26; i++){
			KeyDict.put(tmp + (char)(65+i),65+i);
		}
		for(int i=0; i<10; i++){
			KeyDict.put(tmp + (char)(48+i),48+i);
		}
		KeyDict.put("Key_Space",32);

	}
    private double getCursorPosX(long windowID) {
		DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(windowID, posX, null);
		return posX.get(0);
	}
	private double getCursorPosY(long windowID) {
		DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(windowID, null, posY);
		return posY.get(0);
	}
	private boolean inArray(int[]a,int b){
		boolean tmp = false;
		for (int i=0; i<a.length; i++){
			if(a[i] == b){
				tmp = true;
			}
		}
		return tmp;
	}
	private int getFirstZero(int[]a){
		for (int i=0; i<a.length; i++){
			if(a[i] ==0){
				return i;
			}
		}
		return -1;
	}
	private int getArrayPos(int[]a,int b){
		for (int i=0; i<a.length; i++){
			if(a[i] == b){
				return i;
			}
		}
		System.out.println("In Controllers.java line getArrayPos");
		return -1;		
	}
    public void GetMouse(IntBuffer x, IntBuffer y){
        x.put(0,(int)getCursorPosX(SandSim.window));
        y.put(0,(int)getCursorPosY(SandSim.window));
    }
	public boolean MouseOnClick(String key){
		if(key.equals("Mouse_Left")){
			return (glfwGetMouseButton(SandSim.window,0)==1);
		}else if(key.equals("Mouse_Right")){
			return (glfwGetMouseButton(SandSim.window,1)==1);
		}else if(key.equals("Mouse_Middle")){
			//wip
		}
		return false;
	}
	
	public boolean KeyOnRelease(String key){
		if(!GetKey(key) && (inArray(lastPressed,this.KeyDict.get(key)))){
			lastPressed[getArrayPos(lastPressed, this.KeyDict.get(key))]= 0;
			return true;
		}else if(GetKey(key)){
			lastPressed[getFirstZero(lastPressed)] = this.KeyDict.get(key);
			
		}
		return false;
	}
	public boolean KeyOnPress(String key){
		if(GetKey(key) && !(inArray(lastPressed,this.KeyDict.get(key))) && getFirstZero(lastPressed) != -1){
			lastPressed[getFirstZero(lastPressed)] = this.KeyDict.get(key);
			return true;
		}
		return false;
	}
	public boolean GetKey(String key){
		return (glfwGetKey(SandSim.window,this.KeyDict.get(key))==1);
	}


}