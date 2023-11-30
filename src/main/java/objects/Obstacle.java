package objects;

import com.jogamp.opengl.GL2;
import others.GameValues;

public class Obstacle {
    // Engine renderer
    private final GL2 gl2;

    // Positions and size
    public float x;
    public float y;
    public final float width = GameValues.obstacleWidth;
    public final float height = GameValues.obstacleHeight;

    // Colors
    private float red = GameValues.obstacleStartingRed;
    private float green = GameValues.obstacleStartingGreen;
    private float blue = GameValues.obstacleStartingBlue;

    public Obstacle(GL2 gl2, float x, float y) {
        this.gl2 = gl2;
        this.x = x;
        this.y = y;
    }

    public void renderShape(float xPosition, float yPosition) {
        // Rotation setup
        final float rotation = GameValues.obstacleRotation;
        gl2.glTranslatef(xPosition, yPosition, 0);
        gl2.glRotatef(-rotation, 0, 0, 1);

        // Molding the shape
        gl2.glColor3f(red, green, blue);
        gl2.glBegin(GL2.GL_QUADS);
        gl2.glVertex2f(-width / 2, -height / 2);
        gl2.glVertex2f(width / 2, -height / 2);
        gl2.glVertex2f(width / 2, height / 2);
        gl2.glVertex2f(-width / 2, height / 2);
        gl2.glEnd();
        gl2.glFlush();

        // Rotation cleanup
        gl2.glRotatef(rotation, 0, 0, 1);
        gl2.glTranslatef(-xPosition, -yPosition, 0);
    }

    public void setColor(float r, float g, float b) {
        red = Math.max(0, Math.min(1, Math.abs(r)));
        green = Math.max(0, Math.min(1, Math.abs(g)));
        blue = Math.max(0, Math.min(1, Math.abs(b)));
    }
}
