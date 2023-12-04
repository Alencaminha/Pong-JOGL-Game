package graphics;

import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.MouseEvent;
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

    // Screen size and colors
    public static final float screenWidth = 10.0f;
    public static final float screenHeight = GameRenderer.getHeight() / (GameRenderer.getWidth() / screenWidth);
    private static final float red = (float) 68 / 255;
    private static final float green = (float) 85 / 255;
    private static final float blue = (float) 148 / 255;

    // Game objects
    private Paddle paddle;
    private Ball ball;
    private Obstacle obstacle;

    // Game variables
    private int gamePhase = 0;
    private int gameState = 0;
    private int playerLives = 3;
    private int playerPoints = 0;
    private final float[] speed = new float[] {0, 0.03f, 0.6f};
    private float ballXSpeed = speed[gamePhase];
    private float ballYSpeed = speed[gamePhase];
    private float mouseXPosition;

    // Game pause saved values
    private float xSpeed;
    private float ySpeed;
    private boolean playerCanMove = true;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        // Setting the background color
        gl2 = glAutoDrawable.getGL().getGL2();
        gl2.glClearColor(red, green, blue, 1);

        // Creating the objects
        ball = new Ball(gl2);
        paddle = new Paddle(gl2);
        obstacle = new Obstacle(gl2);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
        // Kill scene
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        // Renderer setup
        gl2 = glAutoDrawable.getGL().getGL2();
        gl2.glClear(GL2.GL_COLOR_BUFFER_BIT);

        if (gamePhase == 0) {
            // Main menu
            renderText(0, 0, "INSTRUCTIONS");
        } else if (gamePhase == 3) {
            // End screen
            renderText(-0.8f, 0, "GAME OVER");
        } else {
            // Checking for collisions
            if (ball.getPaddleCollision(paddle)) {
                int bounceAngle = ball.getPaddleBounceAngle(paddle);
                ballXSpeed = (float) (speed[gamePhase] * Math.cos(bounceAngle));
                ballYSpeed = (float) (speed[gamePhase] * -Math.sin(bounceAngle));
                paddle.setColor(new Random().nextFloat(1), new Random().nextFloat(1), new Random().nextFloat(1));
                playerPoints++;
                checkPhase();
            } else if (ball.getSideWallsCollision()) {
                ballXSpeed = -ballXSpeed;
                playerPoints++;
                checkPhase();
            } else if (ball.getCeilingCollision()) {
                ballYSpeed = -ballYSpeed;
                playerPoints++;
                checkPhase();
            } else if (ball.getFloorCollision()) {
                // Player loses a live and the ball resets its position and angle movement
                playerLives--;
                if (playerLives > 0) {
                    ball.x = 0;
                    ball.y = 0;
                    if (new Random().nextBoolean()) {
                        ballXSpeed = speed[gamePhase];
                    } else {
                        ballXSpeed = -speed[gamePhase];
                    }
                    ballYSpeed = speed[gamePhase];
                } else gamePhase = 3;
            }

            // The game continues while the player still have lives
            if (playerLives > 0) {
                // Applying movement rules
                ball.x += ballXSpeed;
                ball.y += ballYSpeed;
                if (playerCanMove) paddle.x = paddle.getPosition(mouseXPosition);

                // Rendering objects
                renderText(-4.5f, 2, "Lives: " + playerLives);
                ball.renderShape(ball.x, ball.y);
                paddle.renderShape(paddle.x, paddle.y);
                if (gamePhase > 0) obstacle.renderShape(obstacle.x, obstacle.y);
            } else {
                System.out.println("GAME OVER");
            }
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
                GameRenderer.fpsAnimator.stop();
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
                gamePhase++;
                if (new Random().nextBoolean()) {
                    ballXSpeed = speed[gamePhase];
                } else {
                    ballXSpeed = -speed[gamePhase];
                }
                ballYSpeed = -speed[gamePhase];
            } else if (gameState % 2 == 0) {
                // Pause the game
                xSpeed = ballXSpeed;
                ballXSpeed = 0;
                ySpeed = ballYSpeed;
                ballYSpeed = 0;
                playerCanMove = false;
            } else {
                // Unpause the game
                ballXSpeed = xSpeed;
                ballYSpeed = ySpeed;
                playerCanMove = true;
            }
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            super.mouseMoved(mouseEvent);
            mouseXPosition = screenWidth * ((float) mouseEvent.getX() / GameRenderer.getWidth()) - (screenWidth / 2);
        }
    };

    private void renderText(float x, float y, String text) {
        gl2.glColor3f(0, 0, 0);
        gl2.glRasterPos2f(x, y);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, text);
    }

    private void checkPhase() {
        if (playerPoints < 5) gamePhase = 1;
        else if (playerPoints < 15) gamePhase = 2;
        else gamePhase = 3;
    }
}
