package graphics;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.gl2.GLUT;
import objects.DiamondBlock;

public class GameScene implements GLEventListener, KeyListener {
    // Engine renderers
    private static GL2 gl2;
    private static final GLUT glut = new GLUT();

    // Screen sizes
    private final float screenWidth = Renderer.pixelsWide;
    private final float screenHeight = Renderer.screenHeight / (Renderer.screenWidth / Renderer.pixelsWide);

    // Game objects
    private DiamondBlock diamondBlock;
    private float playerXPosition = -1;
    private float playerSpeed = 0;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        // Setting the background color to white
        gl2 = glAutoDrawable.getGL().getGL2();
        gl2.glClearColor(1, 1, 1, 1);

        // Creating the objects
        diamondBlock = new DiamondBlock(gl2);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
        // Kill scene
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        gl2 = glAutoDrawable.getGL().getGL2();
        gl2.glClear(GL2.GL_COLOR_BUFFER_BIT);

        renderText(0, 0, "TEST");

        playerXPosition += playerSpeed;
        diamondBlock.renderShape(playerXPosition, 0);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        gl2 = glAutoDrawable.getGL().getGL2();
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();
        gl2.glOrtho(-screenWidth / 2, screenWidth / 2, -screenHeight / 2, screenHeight / 2, -1, 1);
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        float speed = 0.2f;
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_A, KeyEvent.VK_LEFT:
                playerSpeed = -speed;
                System.out.println("Left");
                break;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT:
                playerSpeed = speed;
                System.out.println("Right");
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_A, KeyEvent.VK_LEFT, KeyEvent.VK_D, KeyEvent.VK_RIGHT:
                playerSpeed = 0;
                break;
        }
    }

    private static void renderText(float x, float y, String text) {
        gl2.glColor3f(0, 0, 0);
        gl2.glRasterPos2f(x, y);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, text);
    }
}
