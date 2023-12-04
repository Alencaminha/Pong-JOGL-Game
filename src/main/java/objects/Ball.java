package objects;

import com.jogamp.opengl.GL2;
import graphics.GameScene;

public class Ball {
    // Engine renderer
    private final GL2 gl2;

    // Positions and size
    public float x = 0;
    public float y = 0;
    public final float radius = 0.25f;

    // Screen
    private final float screenWidth = GameScene.screenWidth;
    private final float screenHeight = GameScene.screenHeight;

    public Ball(GL2 gl2) {
        this.gl2 = gl2;
    }

    public void renderShape(float x, float y) {
        gl2.glTranslatef(x, y, 0);

        // Molding the shape
        gl2.glColor3f(1, 1, 1);
        gl2.glBegin(GL2.GL_POLYGON);
        for (int i = 0; i < 360; i++) {
            float angle = (float) (i * Math.PI / 180);
            gl2.glVertex2f((float) (radius * Math.cos(angle)), (float) (radius * Math.sin(angle)));
        }
        gl2.glEnd();
        gl2.glFlush();

        gl2.glTranslatef(-x, -y, 0);
    }

    public boolean getPaddleCollision(Paddle paddle) {
        boolean collisionX = this.x + this.radius >= paddle.x &&
                paddle.x + paddle.width >= this.x;

        boolean collisionY = this.y + this.radius >= paddle.y &&
                paddle.y + paddle.height >= this.y;

        return collisionX && collisionY;
    }

    public int getPaddleBounceAngle(Paddle paddle) {
        float relativeIntersection = (paddle.x + (paddle.width / 2)) - this.x;
        float normalizedIntersection = relativeIntersection / (paddle.width / 2);
        return (int) (normalizedIntersection * 75);
    }

    public boolean getSideWallsCollision() {
        return this.x <= -((screenWidth / 2) - this.radius) || this.x >= (screenWidth / 2) - this.radius;
    }

    public boolean getCeilingCollision() {
        return this.y >= (screenHeight / 2) - this.radius;
    }

    public boolean getFloorCollision() {
        return this.y <= -((screenHeight / 2) - this.radius);
    }

    public boolean getObstacleCollision(Obstacle obstacle) {
        boolean collisionX = this.x + this.radius >= obstacle.x &&
                obstacle.x + obstacle.width >= this.x;

        boolean collisionY = this.y + this.radius >= obstacle.y &&
                obstacle.y + obstacle.height >= this.y;

        return collisionX && collisionY;
    }
}
