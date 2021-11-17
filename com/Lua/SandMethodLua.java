package com.Lua;
import com.*;

public class SandMethodLua{
    //Singleton
    private static SandMethodLua INSTANCE;
    public static SandMethodLua getInstance(){
        if(INSTANCE == null){
            INSTANCE = new SandMethodLua();
        }
        return INSTANCE;
    }

    private String[] lua = new String[10];
    public void luaSand(int id,Sand sand){
        try{
            InterperateLua.interperate(lua[id-8],sand);
        }catch(Exception e){
            ReadLua.CreateFile(String.valueOf(id)+".lua");
            lua[id-8] = ReadLua.Read(String.valueOf(id)+".lua");
        }
    }

}