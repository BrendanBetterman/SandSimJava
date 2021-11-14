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
    public GameLoop(int worldSize,int xchunk,int ychunk){
        this.worldSize = worldSize;
        this.xchunk =xchunk;
        this.ychunk = ychunk;
        SandList = new Sand[worldSize];
        for (int i=0; i<worldSize; i++){
            SandList[i] = new Sand(new SandObj[xchunk][ychunk]);
        }
        init();
    }
    private void init(){
        Noise noise = new Noise(new Random(2),2f,worldSize*chunkSize,worldSize*chunkSize);
        noise.initialise();
        //System.out.println(NumJa.stringArray(noise.grid_));
        System.out.println((worldSize*chunkSize));
        offset = chunkSize;
        int k=0;
        int ktot = worldSize*chunkSize;
        for (int i=0; i<worldSize; i++){
            int[] plist = new int[chunkSize];
            for(int u=0; u<chunkSize;u++){//wip
                //generate noise
                int xoff = (int)Math.round(chunkSize*(Math.cos((float)((u*i))/(worldSize*chunkSize)) +1));
                int yoff = (int)Math.round(chunkSize*(Math.sin((float)((u*i))/(worldSize*chunkSize)) +1));
                
                plist[u] = (int)Math.round(10*noise.grid_[xoff][yoff]);
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
        if(controls.GetKey("Key_1")){
            this.sandType = 1;
        }
        if(controls.GetKey("Key_2")){
            this.sandType = 2;
        }
        if(controls.GetKey("Key_3")){
            this.sandType = 3;
        }
        if(controls.GetKey("Key_4")){
            this.sandType = 4;
        }
        if(controls.GetKey("Key_5")){
            this.sandType = 5;
        }
        if(controls.GetKey("Key_6")){
            this.sandType = 6;
        }
        if(controls.GetKey("Key_7")){
            this.sandType = 7;
        }
        if(controls.GetKey("Key_8")){
            this.sandType = 8;
        }
        if(controls.MouseOnClick("Mouse_Left")){
            int cursorX = (int)SandSim.getCursorPosX(SandSim.window)/(int)gridSize;
            int cursorY = (int)SandSim.getCursorPosY(SandSim.window)/(int)gridSize;
            int relCurY = (ychunk*(int)gridSize)/(int)gridSize-1-cursorY+(S_height - ychunk*(int)gridSize)/(int)gridSize;
            if (!(cursorX < 0 || cursorX > this.xchunk-1 || relCurY < 0 || relCurY > this.ychunk-1)){
                int tmpCurChunk = wrapList((cursorX+(int)offset)/(int)xchunk);
                //SandList[tmpCurChunk].add();
                //System.out.println(tmpCurChunk);
                int firstChunk = (int)offset/xchunk-1;
                int xoffset = (int)(offset+.5f) - (firstChunk*xchunk+xchunk);
                int relCurX = ((cursorX+xoffset)%xchunk);
                if(controls.GetKey("Key_Q")){
                    SandList[wrapList(tmpCurChunk)].addcube(relCurX,relCurY,3,this.sandType);
                }else if(controls.GetKey("Key_W")){
                    SandList[wrapList(tmpCurChunk)].addcube(relCurX,relCurY,9,this.sandType);
                }else{
                    SandList[wrapList(tmpCurChunk)].addtype(relCurX,relCurY,this.sandType);
                }
                
                //System.out.println(NumJa.stringArray(SandList[wrapList(tmpCurChunk)].sand));
            }
        }
        if(controls.MouseOnClick("Mouse_Right")){
            int cursorX = (int)SandSim.getCursorPosX(SandSim.window)/(int)gridSize;
            int cursorY = (int)SandSim.getCursorPosY(SandSim.window)/(int)gridSize;
            int relCurY = (ychunk*(int)gridSize)/(int)gridSize-1-cursorY+(S_height - ychunk*(int)gridSize)/(int)gridSize;
            if (!(cursorX < 0 || cursorX > this.xchunk-1 || relCurY < 0 || relCurY > this.ychunk-1)){
                int tmpCurChunk = wrapList((cursorX+(int)offset)/(int)xchunk);
                //SandList[tmpCurChunk].add();
                //System.out.println(tmpCurChunk);
                int firstChunk = (int)offset/xchunk-1;
                int xoffset = (int)(offset+.5f) - (firstChunk*xchunk+xchunk);
                int relCurX = ((cursorX+xoffset)%xchunk);
                if(controls.GetKey("Key_Q")){
                    SandList[wrapList(tmpCurChunk)].addcube(relCurX,relCurY,3,0);
                }else if(controls.GetKey("Key_W")){
                    SandList[wrapList(tmpCurChunk)].addcube(relCurX,relCurY,9,0);
                }else{
                    SandList[wrapList(tmpCurChunk)].addtype(relCurX,relCurY,0);
                }
                
                //System.out.println(NumJa.stringArray(SandList[wrapList(tmpCurChunk)].sand));
            }
        }
        
    }
}