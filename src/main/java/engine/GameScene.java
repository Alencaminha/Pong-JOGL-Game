package engine;

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
import textures.Heart;
import textures.Logo;
import textures.PlayButton;
import textures.QuitButton;

import java.util.Random;

public class GameScene implements GLEventListener {
    // Engine renderers
    private GL2 gl2;
    private final GLUT glut = new GLUT();

    // Screen size and colors
    public static final float screenWidth = 10.0f;
    public static final float screenHeight = GameRenderer.getHeight() / (GameRenderer.getWidth() / screenWidth);
    private static final float red = (float) 0 / 255;
    private static final float green = (float) 0 / 255;
    private static final float blue = (float) 0 / 255;

    // Plain objects
    private Paddle paddle;
    private Ball ball;
    private Obstacle obstacle;

    // Textured objects
    private Logo logo;
    private PlayButton playButton;
    private QuitButton quitButton;
    private Heart[] hearts;

    // Game variables
    private GamePhase gamePhase = GamePhase.MENU;
    private boolean isGamePaused = false;
    private int playerLives = 3;
    private int playerPoints = 0;
    private int gameDifficulty = 1;
    private final float[] speed = new float[] {0, 0.03f, 0.06f};
    private float ballXSpeed = speed[gameDifficulty];
    private float ballYSpeed = speed[gameDifficulty];
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

        // Creating textured menu objects
        logo = new Logo(gl2);
        playButton = new PlayButton(gl2);
        quitButton = new QuitButton(gl2);

        // Creating the plain game objects
        ball = new Ball(gl2);
        paddle = new Paddle(gl2);
        obstacle = new Obstacle(gl2);

        // Creating the life hearts
        hearts = new Heart[3];
        for (int i = 0; i < 3; i++) {
            hearts[i] = new Heart(gl2);
            hearts[i].x = -4.5f + 0.75f * i;
            hearts[i].y = 2;
        }

        // Lighting
        enableLighting();
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

        switch (gamePhase) {
            case MENU:
                renderMenu();
                break;
            case GAME:
                checkCollisions();
                renderGame();
                break;
            case END:
                renderEndScreen();
                break;
            case null, default:
                System.out.println("Error while attempting to select a game phase!!!");
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
            switch (keyEvent.getKeyCode()) {
                case KeyEvent.VK_ESCAPE:
                    // Ends the program when the player presses the Escape key
                    GameRenderer.fpsAnimator.stop();
                    System.exit(0);
                    break;
                case KeyEvent.VK_SPACE:
                    pauseUnpause();
                    break;
            }
        }
    };

    MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            super.mouseClicked(mouseEvent);
            float mx = screenWidth * ((float) mouseEvent.getX() / GameRenderer.getWidth()) - (screenWidth / 2);
            float my = -(screenHeight * ((float) mouseEvent.getY() / GameRenderer.getHeight()) - (screenHeight / 2));
            if (gamePhase == GamePhase.MENU) {
                if (mx > playButton.x - (playButton.width / 2) &&
                        mx < playButton.x + (playButton.width / 2) &&
                        my > playButton.y - (playButton.height / 2) &&
                        my < playButton.y + (playButton.height / 2)) {
                    // Starts the game
                    gamePhase = GamePhase.GAME;
                    resetBall();
                } else if (mx > quitButton.x - (quitButton.width / 2) &&
                        mx < quitButton.x + (quitButton.width / 2) &&
                        my > quitButton.y - (quitButton.height / 2) &&
                        my < quitButton.y + (quitButton.height / 2)) {
                    // Quits the game
                    GameRenderer.fpsAnimator.stop();
                    System.exit(0);
                }
            }
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            super.mouseMoved(mouseEvent);
            mouseXPosition = screenWidth * ((float) mouseEvent.getX() / GameRenderer.getWidth()) - (screenWidth / 2);
        }
    };

    private void renderText(float x, float y, String text) {
        gl2.glColor3f(1, 1, 1);
        gl2.glRasterPos2f(x, y);
        glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, text);
    }

    private void enableLighting() {
        gl2.glEnable(GL2.GL_LIGHTING);
        gl2.glEnable(GL2.GL_LIGHT0);
        gl2.glShadeModel(GL2.GL_FLAT);
        float[] lightPosition = {ball.x, ball.y, 1, 1};
        gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition, 0);
        float[] ambientLighting = {0.5f, 0.5f, 0.5f, 0.5f};
        gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLighting, 0);
        float[] specularLighting = {0.5f, 0.5f, 0.5f, 0.5f};
        gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specularLighting, 0);
        gl2.glMateriali(GL2.GL_FRONT, GL2.GL_SHININESS, 1);
        gl2.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, specularLighting, 0);
    }

    private void renderMenu() {
        logo.renderShape();
        playButton.renderShape();
        quitButton.renderShape();
        renderText(-4, 1, "INSTRUÇÕES: MOVA O MOUSE PARA MEXER A TÁBUA");
        renderText(-4, 0.5f, "FAÇA A BOLA BATER NOS CANTOS, NA TÁBUA E OBSTÁCULO");
        renderText(-4, 0, "PARA GANHAR PONTOS. AO ATINGIR 5 PONTOS VOCÊ SOBE");
        renderText(-4, -0.5f, "DE FASE, E COM 15 VOCÊ VENCE O JOGO!");
        renderText(-4, -1, "A TECLA DE ESPAÇO PAUSA E DESPAUSA O JOGO");
        renderText(-4, -1.5f, "E A TECLA DE ESCAPE FECHA O JOGO.");
    }

    private void renderEndScreen() {
        if (playerPoints >= 15) renderText(-0.8f, 0, "PARABÉNS");
        else renderText(-0.8f, 0, "GAME OVER");
    }

    private void checkCollisions() {
        if (ball.getCeilingCollision()) {
            ballYSpeed = -speed[gameDifficulty];
            playerPoints++;
            checkPoints();
        } else if (ball.getSideWallsCollision()) {
            ballXSpeed = -ballXSpeed;
            playerPoints++;
            checkPoints();
        } else if (ball.getFloorCollision()) {
            playerLives--;
            if (playerLives > 0) resetBall();
            else gamePhase = GamePhase.END;
        } else if (ball.getPaddleCollision(paddle)) {
            int bounceAngle = ball.getPaddleBounceAngle(paddle);
            ball.y += 0.025f;
            ballXSpeed = (float) (speed[gameDifficulty] * Math.cos(bounceAngle));
            ballYSpeed = speed[gameDifficulty];
            playerPoints++;
            checkPoints();
        } else if (ball.getObstacleCollision(obstacle) && gameDifficulty > 1) {
            ballXSpeed = -speed[gameDifficulty];
            ballYSpeed = -speed[gameDifficulty];
            playerPoints++;
            checkPoints();
        }
    }

    private void renderGame() {
        // Applying movement rules
        ball.x += ballXSpeed;
        ball.y += ballYSpeed;
        if (playerCanMove) paddle.x = paddle.getPosition(mouseXPosition);

        // Rendering objects
        renderText(3.5f, 2, "Points: " + playerPoints);
        for (int i = 0; i < playerLives; i++) {
            hearts[i].renderShape();
        }
        ball.renderShape(ball.x, ball.y);
        paddle.renderShape(paddle.x, paddle.y);
        if (gameDifficulty > 1) obstacle.renderShape(obstacle.x, obstacle.y);

        // Lighting
        float[] lightPosition = {ball.x, ball.y, 1, 0.05f};
        gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition, 0);
    }

    private void resetBall() {
        ball.x = 0;
        ball.y = 0;
        if (new Random().nextBoolean()) {
            ballXSpeed = speed[gameDifficulty];
        } else {
            ballXSpeed = -speed[gameDifficulty];
        }
        ballYSpeed = -speed[gameDifficulty];
    }

    private void pauseUnpause() {
        isGamePaused = !isGamePaused;
        if (isGamePaused) {
            xSpeed = ballXSpeed;
            ballXSpeed = 0;
            ySpeed = ballYSpeed;
            ballYSpeed = 0;
            playerCanMove = false;
        } else {
            ballXSpeed = xSpeed;
            ballYSpeed = ySpeed;
            playerCanMove = true;
        }
    }

    private void checkPoints() {
        if (playerPoints < 5) gameDifficulty = 1;
        else if (playerPoints < 15) gameDifficulty = 2;
        else gamePhase = GamePhase.END;
    }
}
