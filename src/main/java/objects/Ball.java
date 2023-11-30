package objects;

import com.jogamp.opengl.GL2;

public class Ball {
    // Engine renderer
    private final GL2 gl2;

    // Positions
    public float x;
    public float y;

    public Ball(GL2 gl2, float x, float y) {
        this.gl2 = gl2;
        this.x = x;
        this.y = y;
    }

    public void renderShape(float xPosition, float yPosition) {
        // Size and color values
        final float radius = 0.25f;
        final float redLevel = 0.5f;
        final float greenLevel = 0;
        final float blueLevel = 0.5f;

        gl2.glTranslatef(xPosition, yPosition, 0);

        // Molding the shape
        gl2.glColor3f(redLevel, greenLevel, blueLevel);
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
