package objects;

import com.jogamp.opengl.GL2;
import others.GameValues;

public class Ball {
    // Engine renderer
    private final GL2 gl2;

    // Positions and size
    public float x;
    public float y;
    public final float radius = GameValues.ballRadius;

    public Ball(GL2 gl2, float x, float y) {
        this.gl2 = gl2;
        this.x = x;
        this.y = y;
    }

    public void renderShape(float xPosition, float yPosition) {
        gl2.glTranslatef(xPosition, yPosition, 0);

        // Molding the shape
        gl2.glColor3f(GameValues.ballRed, GameValues.ballGreen, GameValues.ballBlue);
        gl2.glBegin(GL2.GL_POLYGON);
        for (int i = 0; i < 360; i++) {
            float angle = (float) (i * Math.PI / 180);
            gl2.glVertex2f((float) (radius * Math.cos(angle)), (float) (radius * Math.sin(angle)));
        }
        gl2.glEnd();
        gl2.glFlush();

        gl2.glTranslatef(-xPosition, -yPosition, 0);
    }
}
