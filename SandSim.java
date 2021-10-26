
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
	private long window;
    private int S_width;
    private int S_height;
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

		// Set the clear color
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity(); // Resets any previous projection matrices
		glOrtho(0, height, 0, width, 1, 0);
		
		glMatrixMode(GL_MODELVIEW);
		float gridSize =10.0f;
		Sand sand = new Sand(new int[height/(int)gridSize][width/(int)gridSize]);
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		sand.add();

		float rot =0f;
		int nframes =0;
		
		while ( !glfwWindowShouldClose(window) ) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
			nframes+=1;
			
			if (nframes %2 ==0){
                sand.update();
				oldRGB = NumJa.lerp(oldRGB,newRGB,0.05f);
				if (oldRGB[0] > newRGB[0] -0.0001f){
					newRGB = NumJa.randFloatArray(rand, 3);
				}
               
				int cursorX = (int)getCursorPosX(window)/(int)gridSize;
					int cursorY = (int)getCursorPosY(window)/(int)gridSize;
					if (!(cursorX < 0 || cursorX > sand.sand[0].length-1 || sand.sand[0].length-1-cursorY < 0 || sand.sand[0].length-1-cursorY > sand.sand.length-1)){
						sand.add(S_height/(int)gridSize -1- cursorY + sand.sand[0].length,cursorX);
					}
                System.out.println(S_height/(int)gridSize - cursorY + sand.sand[0].length);
			}
           
			drawSand(sand,gridSize);
            //drawSands(sand,gridSize);

			glfwSwapBuffers(window); // swap the color buffers
			glClearColor(oldRGB[0],oldRGB[1],oldRGB[2],0.0f);
			glfwPollEvents();
		}
	}
	private void drawSands(Sand sand,float gridSize){
		for(int i=sand.rowsFilled; i<sand.sand.length; i++){
			for(int u=0; u<sand.sand[0].length; u++){
				if( !(sand.sand[i][u] == 0)){
					drawQuad((float)u * gridSize, (float)i*gridSize, gridSize, gridSize);
				}
			}
		}
		drawQuad(0.0f, 0.0f, (float)sand.sand[0].length * gridSize, (float)sand.rowsFilled * gridSize);
	}
	private void drawSand(Sand sand,float gridSize){
		int[][] c = NumJa.findHCluster(sand.sand, sand.rowsFilled);
		for(int i=0; i<c.length; i++){
			drawQuad((float)(c[i][0])*gridSize,(float)(c[i][2])*gridSize,(float)(c[i][1]+1)*gridSize,gridSize);
        }
		drawQuad(0.0f, 0.0f, (float)sand.sand[0].length * gridSize, (float)sand.rowsFilled * gridSize);
	}
	private void drawMatrix(int[][] array,float gridSize){
		for(int i=0; i<array.length;i++){
			for (int u=0; u<array[0].length; u++){
				if( !(array[i][u] == 0)){
					drawQuad((float)u * gridSize, (float)i*gridSize, gridSize, gridSize);
				}
			}
		}
	}
	private void drawQuad(float x,float y, float width,float height){
		Random rand = new Random();
		//float temp = rand.nextFloat();
		glColor3f(0.0f,0.0f,0.0f);
		glBegin(GL_POLYGON);
		glVertex2f(x, y);
		glVertex2f(x, height+y);
		glVertex2f(width+x,height+y);
		glVertex2f(width+x, y);
		glEnd();
		glFlush(); 
	}
	
	
	public static void main(String[] args) {
		new SandSim().run();
		
	}

}