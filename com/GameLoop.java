package com;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import java.nio.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
import java.util.Random;
import java.lang.Math;

public class GameLoop{

    private Sand[] SandList;
    private int worldSize;
    private int chunkSize = 72;
    private int xchunk;
    private int ychunk;
    private float offset =0f;
    private float gridSize=10.0f;
    private int S_height =0;
    private IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
    private int nframes = 0;
    private ChunkBorders chunk = new ChunkBorders();
    private Controllers controls = new Controllers();
    private int sandType = 1;
    private int saveMedium = 0;
    private int wasMedium = 0;
    private int tmpCurChunk = 0;
    private int relCurX =0;
    private Save save = new Save("Save.file");
    public GameLoop(int worldSize,int xchunk,int ychunk,float gridSize){
        this.worldSize = worldSize;
        this.xchunk =xchunk;
        this.ychunk = ychunk;
        this.gridSize = gridSize;
        SandList = new Sand[worldSize];
        for (int i=0; i<worldSize; i++){
            SandList[i] = new Sand(new SandObj[xchunk][ychunk]);
        }
        init();
    }
    private void init(){
        Noise noise = new Noise(new Random(1),2f,worldSize*chunkSize,worldSize*chunkSize);
        noise.initialise();
        //System.out.println(NumJa.stringArray(noise.grid_));
        System.out.println((worldSize*chunkSize));
        offset = xchunk;
        int k=0;
        int ktot = worldSize*xchunk;
        for (int i=0; i<worldSize; i++){
            int[] plist = new int[xchunk];
            for(int u=0; u<xchunk;u++){//wip
                //generate noise
                int xoff = i;
                
                
                plist[u] = (int)Math.round(100*noise.grid_[xoff][0]);
            }
            SandList[i].generate(plist);
        }
    }
    private int wrapList(int slot){
        if(slot <0){
            return worldSize+slot;
        }else if(slot >= worldSize){
            return slot - worldSize;

        }else{
            return slot;
        }
    }
    public Sand[] draw(){
        int tmpChunks = (int)offset/xchunk;
        Sand[] tmpSand = new Sand[3];
       // System.out.println(wrapList(tmpChunks));
        int u =0;
        for(int i = tmpChunks-1;i<tmpChunks+2;i++ ){
           // System.out.println("wrap"+wrapList(i));
            tmpSand[u++] = SandList[wrapList(i)];
        }
        return tmpSand;
    }
    public float[] getOffset(){
        int tmpChunks = (int)offset/xchunk;
        float[] tmp = new float[3];
        int u=0;
        for(int i = tmpChunks-1;i<tmpChunks+2;i++ ){
            tmp[u++] = offset - (xchunk*i);
        }
        return tmp;
    }
    public void update(){
        //Only update the previous chunk and the next chunk does wrap.
        
        int tmpChunks = (int)offset/xchunk;
        if(! controls.GetKey("Key_Space")){
            for(int i = tmpChunks;i<tmpChunks+3;i++ ){
                SandList[wrapList(i)].update();
                
            }
        }

        chunk.update(SandList[wrapList(tmpChunks)],SandList[wrapList(tmpChunks+1)],SandList[wrapList(tmpChunks+2)]);
        glfwGetWindowSize(SandSim.window,null,heightBuffer);
        S_height = heightBuffer.get(0);
        nframes++;
        if(controls.GetKey("Key_A")){
            offset-=2f;
        }
        if(controls.GetKey("Key_D")){
            offset+=2f;
        }
        if(offset <0){
            offset+= xchunk*worldSize;
        }
        
        if(offset >=(xchunk*worldSize)){
            offset = 0f;
        }
        for(int i =1; i<9; i++){
            if(controls.GetKey("Key_" + i)){
                this.sandType = i;
            }
        }
        
        if(controls.GetKey("Key_0")){
            this.sandType = 10;
        }
        if(controls.KeyOnRelease("Key_S")){
            
            save.SaveGame(SandList);
        }
        if(controls.KeyOnRelease("Key_P")){
            
            SandList = save.GetSave(SandList,gridSize);

        }
        if(controls.GetKey("Key_M")){
            if(SandSim.gridSize <4.9f){
                SandSim.gridSize+=0.1f;
                this.gridSize = SandSim.gridSize;
            }
            
        }
        if(controls.GetKey("Key_N")){
            if(SandSim.gridSize>0.2f){
                SandSim.gridSize-=0.1f;
                this.gridSize = SandSim.gridSize;
            }
            
        }
        //mouse Position
        int cursorX = (int)SandSim.getCursorPosX(SandSim.window)/(int)gridSize;
        int cursorY = (int)SandSim.getCursorPosY(SandSim.window)/(int)gridSize;
        int relCurY = (ychunk*(int)gridSize)/(int)gridSize-1-cursorY+(S_height - ychunk*(int)gridSize)/(int)gridSize;
        
        if (!(cursorX < 0 || cursorX > this.xchunk-1 || relCurY < 0 || relCurY > this.ychunk-1)){
            tmpCurChunk = wrapList((cursorX+(int)offset)/(int)xchunk);
            int firstChunk = (int)offset/xchunk-1;
            int xoffset = (int)(offset+.5f) - (firstChunk*xchunk+xchunk);
            relCurX = ((cursorX+xoffset)%xchunk);
        }
        if(controls.KeyOnPress("Key_Shift")){
            saveMedium = SandList[wrapList(tmpCurChunk)].sand[relCurX][relCurY].type;
        }
        if(controls.MouseOnClick("Mouse_Left")){
            if(controls.GetKey("Key_Q")&&controls.GetKey("Key_Shift")){
                SandList[wrapList(tmpCurChunk)].addcubeNoDel(relCurX,relCurY,3,this.sandType,saveMedium);
            }else if(controls.GetKey("Key_W")&&controls.GetKey("Key_Shift")){
                SandList[wrapList(tmpCurChunk)].addcubeNoDel(relCurX,relCurY,9,this.sandType,saveMedium);
            }else if(controls.GetKey("Key_Shift")){
                SandList[wrapList(tmpCurChunk)].addtypeNoDel(relCurX,relCurY,this.sandType,saveMedium);
            }else if(controls.GetKey("Key_Q")){
                SandList[wrapList(tmpCurChunk)].addcube(relCurX,relCurY,3,this.sandType);
            }else if(controls.GetKey("Key_W")){
                SandList[wrapList(tmpCurChunk)].addcube(relCurX,relCurY,9,this.sandType);
            }else{
                SandList[wrapList(tmpCurChunk)].addtype(relCurX,relCurY,this.sandType);
            }
        }
        if(controls.MouseOnClick("Mouse_Right")){
            if(controls.GetKey("Key_Q")&&controls.GetKey("Key_Shift")){
                SandList[wrapList(tmpCurChunk)].addcubeNoDel(relCurX,relCurY,3,0,saveMedium);
            }else if(controls.GetKey("Key_W")&&controls.GetKey("Key_Shift")){
                SandList[wrapList(tmpCurChunk)].addcubeNoDel(relCurX,relCurY,9,0,saveMedium);
            }else if(controls.GetKey("Key_Shift")){
                SandList[wrapList(tmpCurChunk)].addtypeNoDel(relCurX,relCurY,0,saveMedium);
            }if(controls.GetKey("Key_Q")){
                SandList[wrapList(tmpCurChunk)].addcube(relCurX,relCurY,3,0);
            }else if(controls.GetKey("Key_W")){
                SandList[wrapList(tmpCurChunk)].addcube(relCurX,relCurY,9,0);
            }else{
                SandList[wrapList(tmpCurChunk)].addtype(relCurX,relCurY,0);
            }
        }
       
    }
}