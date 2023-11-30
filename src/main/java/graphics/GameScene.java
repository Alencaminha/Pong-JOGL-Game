package graphics;

import com.jogamp.newt.event.*;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.gl2.GLUT;
import objects.Ball;
import objects.Obstacle;
import objects.Paddle;

import java.util.Random;

public class GameScene implements GLEventListener {
    // Engine renderers
    private GL2 gl2;
    private final GLUT glut = new GLUT();

    // Screen sizes
    public static final float screenWidth = 10.0f;
    private static final float screenHeight = Renderer.getHeight() / (Renderer.getWidth() / screenWidth);

    // Game objects
    private Paddle paddle;
    private Ball ball;
    private Obstacle obstacle;

    // Game variables
    private int gameState = 0;
    private int gamePhase = 0;
    private float ballXSpeed = 0.0f;
    private float ballYSpeed = 0.0f;
    private float mouseXPosition;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        // Setting the background color to white
        gl2 = glAutoDrawable.getGL().getGL2();
        gl2.glClearColor(1, 1, 1, 1);

        // Creating the objects
        ball = new Ball(gl2, 0, 0);
        paddle = new Paddle(gl2, 0, -2.5f);
        obstacle = new Obstacle(gl2, 0, 2);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
        // Kill scene
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        // Renderer
        gl2 = glAutoDrawable.getGL().getGL2();
        gl2.glClear(GL2.GL_COLOR_BUFFER_BIT);

        // Ball
        ball.x += ballXSpeed;
        ball.y += ballYSpeed;
        ball.renderShape(ball.x, ball.y);

        // Paddle
        paddle.x = paddle.getPosition(mouseXPosition);
        paddle.renderShape(paddle.x, paddle.y);

        // Obstacle
        if (gamePhase == 0) {
            obstacle.renderShape(obstacle.x, obstacle.y);
        }
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
                // Ends the program when the player presses the Escape key
                System.exit(0);
            }
        }
    };

    MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            super.mouseClicked(mouseEvent);
            gameState++;
            if (gameState == 1) {
                // Starts the game
                float ballSpeed = 0.025f;
                if (new Random().nextBoolean()) {
                    ballXSpeed = ballSpeed;
                } else {
                    ballXSpeed = -ballSpeed;
                }
                ballYSpeed = ballSpeed;
            } else if (gameState % 2 == 0) {
                // Pause
            } else {
                // Unpause
            }
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            super.mouseMoved(mouseEvent);
            mouseXPosition = screenWidth * ((float) mouseEvent.getX() / Renderer.getWidth()) - (screenWidth / 2);
        }
    };

    private void renderText(float x, float y, String text) {
        gl2.glColor3f(0, 0, 0);
        gl2.glRasterPos2f(x, y);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, text);
    }
}
