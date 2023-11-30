package others;

import objects.Ball;
import objects.Paddle;

public class GameValues {
    // Screen
    public static float screenRed = 0;
    public static float screenGreen = 0;
    public static float screenBlue = 0.2f;
    public static float screenAlpha = 1;

    // Ball
    public static float ballStartingX = 0;
    public static float ballStartingY = 0;
    public static float ballRadius = 0.25f;
    public static float ballRed = 1;
    public static float ballGreen = 1;
    public static float ballBlue = 1;
    public static float[] speed = new float[] {0, 0.025f};

    // Paddle
    public static float paddleStartingX = 0;
    public static float paddleStartingY = -2.5f;
    public static float paddleWidth = 2;
    public static float paddleHeight = 0.5f;
    public static float paddleStartingRed = 0.5f;
    public static float paddleStartingGreen = 0;
    public static float paddleStartingBlue = 0.5f;

    // Obstacle
    public static float obstacleStartingX = 0;
    public static float obstacleStartingY = 2;
    public static float obstacleWidth = 1;
    public static float obstacleHeight = 1;
    public static float obstacleStartingRed = 0.5f;
    public static float obstacleStartingGreen = 0;
    public static float obstacleStartingBlue = 0.5f;
    public static int obstacleRotation = 45;

    public static int getBallBounceAngleFromPaddle(Ball ball, Paddle paddle) {
        float relativeIntersection = (paddle.x + (paddle.width / 2)) - ball.x;
        float normalizedIntersection = relativeIntersection / (paddle.width / 2);
        return (int) (normalizedIntersection * 75);
    }
}
