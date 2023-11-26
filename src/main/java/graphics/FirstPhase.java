package graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.gl2.GLUT;
import inputs.KeyboardInput;
import objects.DiamondBlock;

public class FirstPhase implements GLEventListener {
    // Engine renderers
    private static GL2 gl = null;
    private static final GLUT glut = new GLUT();

    // Screen sizes
    private final float screenWidth = Renderer.pixelsWide;
    private final float screenHeight = Renderer.screenHeight / (Renderer.screenWidth / Renderer.pixelsWide);

    // Game objects
    private DiamondBlock diamondBlock;
    private final KeyboardInput keyboardInput;

    // Player position
    private float playerXPosition = -1;
    private final float playerYPosition = 0;

    public FirstPhase(KeyboardInput keyboardInput) {
        this.keyboardInput = keyboardInput;
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        // Setting the background color to white
        gl = glAutoDrawable.getGL().getGL2();
        gl.glClearColor(1, 1, 1, 1);

        // Creating the objects
        diamondBlock = new DiamondBlock(gl, playerXPosition, playerYPosition);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
        // Kill scene
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

        renderText(0, 0, "HELLO");
        renderText(1, 1, "BA BA");

        // Player X position
        playerXPosition = playerXPosition + keyboardInput.getPlayerSpeed();
        diamondBlock.renderShape(playerXPosition, playerYPosition);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        gl = glAutoDrawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-screenWidth / 2, screenWidth / 2, -screenHeight / 2, screenHeight / 2, -1, 1);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    private static void renderText(float x, float y, String text) {
        gl.glColor4f(0, 0, 0, 1);
        gl.glRasterPos2f(x, y);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, text);
    }
}
