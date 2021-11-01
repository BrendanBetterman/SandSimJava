
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
    private float offset =0f;
    private float gridSize=10.0f;
    private int S_height =0;
    private IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
    private int nframes = 0;
    private ChunkBorders chunk = new ChunkBorders();
    private Controllers controls = new Controllers();
    public GameLoop(int worldSize){
        this.worldSize = worldSize;
        SandList = new Sand[worldSize];
        for (int i=0; i<worldSize; i++){
            SandList[i] = new Sand(new int[chunkSize][chunkSize]);
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
            for(int u=0; u<chunkSize;u++){
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
        int tmpChunks = (int)offset/chunkSize;
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
        int tmpChunks = (int)offset/chunkSize;
        float[] tmp = new float[3];
        int u=0;
        for(int i = tmpChunks-1;i<tmpChunks+2;i++ ){
            tmp[u++] = offset - (chunkSize*i);
        }
        return tmp;
    }
    public void update(){
        //Only update the previous chunk and the next chunk does wrap.
        
        int tmpChunks = (int)offset/chunkSize;
        for(int i = tmpChunks;i<tmpChunks+3;i++ ){
            SandList[wrapList(i)].update();
            
        }
        chunk.update(SandList[wrapList(tmpChunks)],SandList[wrapList(tmpChunks+1)],SandList[wrapList(tmpChunks+2)]);
        glfwGetWindowSize(SandSim.window,null,heightBuffer);
        S_height = heightBuffer.get(0);
        nframes++;
        if(controls.GetKey("Key_A")){
            offset-=1f;
        }
        if(controls.GetKey("Key_D")){
            offset+=1f;
        }
        if(offset <0){
            offset+= chunkSize*worldSize;
        }
        
        if(offset >=(chunkSize*worldSize)){
            offset = 0f;
        }
        if(controls.MouseOnClick("Mouse_Left")){
            int cursorX = (int)SandSim.getCursorPosX(SandSim.window)/(int)gridSize;
            int cursorY = (int)SandSim.getCursorPosY(SandSim.window)/(int)gridSize;
            int relCurY = (chunkSize*(int)gridSize)/(int)gridSize-1-cursorY+(S_height - chunkSize*(int)gridSize)/(int)gridSize;
            if (!(cursorX < 0 || cursorX > this.chunkSize-1 || relCurY < 0 || relCurY > this.chunkSize-1)){
                int tmpCurChunk = wrapList((cursorX+(int)offset)/(int)chunkSize);
                //SandList[tmpCurChunk].add();
                //System.out.println(tmpCurChunk);
                int firstChunk = (int)offset/chunkSize-1;
                int xoffset = (int)(offset+.5f) - (firstChunk*chunkSize+chunkSize);
                int relCurX = ((cursorX+xoffset)%chunkSize);
                SandList[wrapList(tmpCurChunk)].add(relCurX,relCurY);
            }
        }
        if(controls.MouseOnClick("Mouse_Right")){
            int cursorX = (int)SandSim.getCursorPosX(SandSim.window)/(int)gridSize;
            int cursorY = (int)SandSim.getCursorPosY(SandSim.window)/(int)gridSize;
            int relCurY = (chunkSize*(int)gridSize)/(int)gridSize-1-cursorY+(S_height - chunkSize*(int)gridSize)/(int)gridSize;
            if (!(cursorX < 0 || cursorX > this.chunkSize-1 || relCurY < 0 || relCurY > this.chunkSize-1)){
                int tmpCurChunk = wrapList((cursorX+(int)offset)/(int)chunkSize);
                //SandList[tmpCurChunk].add();
                //System.out.println(tmpCurChunk);
                int firstChunk = (int)offset/chunkSize-1;
                int xoffset = (int)(offset+.5f) - (firstChunk*chunkSize+chunkSize);
                int relCurX = ((cursorX+xoffset)%chunkSize);
                SandList[wrapList(tmpCurChunk)].remove(relCurX,relCurY);
            }
        }

    }
}