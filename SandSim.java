
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.ArrayList;
import java.util.Random;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class SandSim {

	// The window handle
	public static long window;
    private int S_width;
    private int S_height;
	private IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
	private IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");
		//System.out.println("Hellow LWJGL");
		init();
		loop();

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	private void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(720, 720, "Hello World!", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            
			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
	}
    
	public static double getCursorPosX(long windowID) {
		DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(windowID, posX, null);
		return posX.get(0);
	}
	public static double getCursorPosY(long windowID) {
		DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(windowID, null, posY);
		return posY.get(0);
	}
    
	private void loop() {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		Random rand = new Random();
		float[] oldRGB = new float[]{0.0f,0.0f,0.0f};
		float[] newRGB = NumJa.randFloatArray(rand, 3);
		int height = 720;
		int width = 720;
		GameLoop gameLoop = new GameLoop(5);
		// Set the clear color
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glViewport(0, 0, width, height);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(); // Resets any previous projection matrices
		glOrtho(0, height, 0, width, 1, 0);

		IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
        
		glMatrixMode(GL_MODELVIEW);
		float gridSize =10.0f;
		Sand sand = new Sand(new int[height/(int)gridSize][width/(int)gridSize]);

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.

		float rot =0f;
		int nframes =0;
		
		while ( !glfwWindowShouldClose(window) ) {

			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
			nframes+=1;
			
			if (nframes %1 ==0){
				/*Sync screen
				glfwGetWindowSize(SandSim.window,widthBuffer,heightBuffer);
        		int Cheight = heightBuffer.get(0);
				int Cwidth = widthBuffer.get(0);
				glViewport(0, 0, Cwidth, Cheight);
				*/
				//sand.add(70,width/(int)2/(int)gridSize);
				
               // glfwGetWindowSize(window,null,heightBuffer);
               // S_height = heightBuffer.get(0);
                //sand.update();
				oldRGB = NumJa.lerp(oldRGB,newRGB,0.05f);
				if (oldRGB[0] > newRGB[0] -0.0001f){
					newRGB = NumJa.randFloatArray(rand, 3);
				}/*
				int cursorX = (int)getCursorPosX(window)/(int)gridSize;
				int cursorY = (int)getCursorPosY(window)/(int)gridSize;
				int relCurY = (height)/(int)gridSize -1- cursorY + (S_height - height)/(int)gridSize;
				if (!(cursorX < 0 || cursorX > sand.sand[0].length-1 || relCurY < 0 || relCurY > sand.sand.length-1)){
					sand.add(cursorX,relCurY);
				}*/
				gameLoop.update();
				gameLoop.update();
				gameLoop.update();
            }
			drawSandArray(gameLoop.draw(),gridSize,gameLoop.getOffset());
           	
			//drawSand(sand,gridSize,0);
           

			glfwSwapBuffers(window); // swap the color buffers
			glClearColor(oldRGB[0],oldRGB[1],oldRGB[2],0.0f);
			glfwPollEvents();
		}
	}
	public void drawSandArray(Sand[] tmp, float gridSize, float[] offset){
		for(int i=0; i<tmp.length; i++){
			drawSand(tmp[i], gridSize, offset[i]);
		}
	}
	public void drawSand(Sand sand,float gridSize,float offset){
		try{
		int[][] c = NumJa.findHCluster(sand.sand, sand.rowsFilled);
		
		for(int i=0; i<c.length; i++){
			drawQuad((float)(c[i][0])*gridSize,(float)(c[i][2]-offset)*gridSize,(float)(c[i][1]+1)*gridSize,gridSize);
        }
		drawQuad((float)offset* gridSize,0.0f,(float)(sand.sand.length+gridSize) * gridSize ,(float)sand.rowsFilled * gridSize );
		}catch(Exception e){}	
	}
	
	private void drawQuad(float x,float y, float width,float height){
		Random rand = new Random();
		//float temp = rand.nextFloat();
		glColor3f(0.0f,0.0f,0.0f);
		glBegin(GL_POLYGON);
		glVertex2f(y,x);
		glVertex2f(height+y,x);
		glVertex2f(height+y,width+x);
		glVertex2f(y,width+x);
		glEnd();
		glFlush(); 
	}
	
	
	public static void main(String[] args) {
		new SandSim().run();
		
	}

}