package objects;

import com.jogamp.opengl.GL2;

public class Ball {
    // Engine renderer
    private final GL2 gl2;

    // Colors
    private float redLevel = 0.5f;
    private float greenLevel = 0;
    private float blueLevel = 0.5f;

    public Ball(GL2 gl2) {
        this.gl2 = gl2;
    }

    public void renderShape(float xPosition, float yPosition) {
        // Size values
        final float radius = 0.25f;

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

    public void setColor(float r, float g, float b) {
        this.redLevel = Math.max(0, Math.min(1, Math.abs(r)));
        this.greenLevel = Math.max(0, Math.min(1, Math.abs(g)));
        this.blueLevel = Math.max(0, Math.min(1, Math.abs(b)));
    }
}