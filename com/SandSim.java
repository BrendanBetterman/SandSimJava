package com;
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
		window = glfwCreateWindow(1920, 1080, "Sand!", glfwGetPrimaryMonitor(), NULL);
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

		float gridSize =10.0f;
		int height = 1080;
		int width = 1920;
		
		// Set the clear color
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(); // Resets any previous projection matrices
		glOrtho(0, width, 0, height, 1, 0);

		IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
        
		glMatrixMode(GL_MODELVIEW);
		
		glViewport(0, 0, width, height);
		GameLoop gameLoop = new GameLoop(5,width/(int)gridSize,height/(int)gridSize);
		//Sand sand = new Sand(new int[width/(int)gridSize][height/(int)gridSize]);

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.

		float rot =0f;
		int nframes =0;
		
		while ( !glfwWindowShouldClose(window) ) {

			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
			nframes+=1;
			
			if (nframes %1 ==0){
				gameLoop.update();
            }
				drawSandArray(gameLoop.draw(),gridSize,gameLoop.getOffset());
				//drawPoly(new int[][]{{5,10},{5,15},{10,20},{20,15},{19,70}}, gridSize);
			//drawSandMarch(gameLoop.draw()[1], gridSize);
			
           	
			//drawSand(sand,gridSize,0);
           

			glfwSwapBuffers(window); // swap the color buffers
			//glClearColor(oldRGB[0],oldRGB[1],oldRGB[2],0.0f);
			float[] tmp = SandType.backgroundColor();
			glClearColor(tmp[0],tmp[1],tmp[2],0.0f);
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
		//System.out.println(NumJa.stringArray(c));
		for(int i=0; i<c.length; i++){
			drawQuad((float)(c[i][0])*gridSize,(float)(c[i][2]-offset)*gridSize,(float)(c[i][1]+1)*gridSize,gridSize,c[i][3]);
        }
		drawQuad((float)offset* gridSize,0.0f,(float)(sand.sand.length+gridSize) * gridSize ,(float)sand.rowsFilled * gridSize,1);
		}catch(Exception e){}	
	}
	
	private void drawQuad(float x,float y, float width,float height,int type){
		Random rand = new Random();
		//float temp = rand.nextFloat();
		float[] tmp;
		tmp = SandType.colorFromType(type);
		
		glColor4f(tmp[0],tmp[1],tmp[2],tmp[3]);
		
		glBegin(GL_POLYGON);
		glVertex2f(y,x);
		glVertex2f(height+y,x);
		glVertex2f(height+y,width+x);
		glVertex2f(y,width+x);
		glEnd();
		glFlush(); 
	}
	private void drawSandMarch(Sand sand,float gridSize){
		int[][] c = NumJa.marchingSquares(sand.sand);
		drawPoly(c, gridSize);
	}
	private void drawPoly(int[][] tmp,float gridSize){
		glColor4f(0,0,0,1);
		glBegin(GL_POLYGON);
		for(int i =0; i<tmp.length; i++){
			glVertex2f(tmp[i][0]*gridSize,tmp[i][1]*gridSize);
		}
		glEnd();
		glFlush(); 
	}
	
	public static void main(String[] args) {
		new SandSim().run();
		
	}

}