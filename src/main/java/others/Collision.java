package others;

import graphics.GameScene;
import objects.Ball;
import objects.Obstacle;
import objects.Paddle;

public class Collision {
    // All objects that can have a collision
    private final Ball ball;
    private final Paddle paddle;
    private final Obstacle obstacle;
    private final float screenWidth;
    private final float screenHeight;

    public Collision(Ball ball, Paddle paddle, Obstacle obstacle) {
        this.ball = ball;
        this.paddle = paddle;
        this.obstacle = obstacle;
        this.screenWidth = GameScene.screenWidth;
        this.screenHeight = GameScene.screenHeight;
    }

    public int getCollision() {
        if (ballAndPaddleCollision()) return 1;
        else if (ballAndObstacleCollision()) return 2;
        else return 0;
    }

    private boolean ballAndPaddleCollision() {
        boolean collisionX = ball.x + ball.radius >= paddle.x &&
                paddle.x + paddle.width >= ball.x;

        boolean collisionY = ball.y + ball.radius >= paddle.y &&
                paddle.y + paddle.height >= ball.y;

        return collisionX && collisionY;
    }

    private boolean ballAndObstacleCollision() {
        boolean collisionX = ball.x + ball.radius >= obstacle.x &&
                obstacle.x + obstacle.width >= ball.x;

        boolean collisionY = ball.y + ball.radius >= obstacle.y &&
                obstacle.y + obstacle.height >= ball.y;

        return collisionX && collisionY;
    }
}
