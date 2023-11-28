package graphics;

import com.jogamp.newt.event.*;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.gl2.GLUT;
import objects.Ball;
import objects.DiamondBlock;
import objects.Paddle;

public class GameScene implements GLEventListener {
    // Engine renderers
    private GL2 gl2;
    private final GLUT glut = new GLUT();

    // Screen sizes
    private final float screenWidth = Renderer.pixelsWide;
    private final float screenHeight = Renderer.screenHeight / (Renderer.screenWidth / Renderer.pixelsWide);

    // Game objects
    private Paddle paddle;
    private Ball ball;
    private DiamondBlock diamondBlock;
    private float mouseXPosition = 0;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        // Setting the background color to white
        gl2 = glAutoDrawable.getGL().getGL2();
        gl2.glClearColor(1, 1, 1, 1);

        // Creating the objects
        paddle = new Paddle(gl2);
        ball = new Ball(gl2);
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

        paddle.renderShape(paddle.getPosition(mouseXPosition), -2);
        ball.renderShape(2, 0);
        diamondBlock.renderShape(0, 2);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        gl2 = glAutoDrawable.getGL().getGL2();
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();
        gl2.glOrtho(-screenWidth / 2, screenWidth / 2, -screenHeight / 2, screenHeight / 2, -1, 1);
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
    }

    KeyAdapter keyAdapter = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            super.keyPressed(keyEvent);
            if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }
        }
    };

    MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            super.mouseMoved(mouseEvent);
            float screenSize = Renderer.pixelsWide;
            mouseXPosition = screenSize * ((float) mouseEvent.getX() / Renderer.screenWidth) - (screenSize / 2);
        }
    };

    private void renderText(float x, float y, String text) {
        gl2.glColor3f(0, 0, 0);
        gl2.glRasterPos2f(x, y);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, text);
    }
}
