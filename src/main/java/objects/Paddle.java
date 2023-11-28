package objects;

import com.jogamp.opengl.GL2;
import graphics.Renderer;

public class Paddle {
    // Engine renderer
    private final GL2 gl2;

    // Colors
    private float redLevel = 0.5f;
    private float greenLevel = 0;
    private float blueLevel = 0.5f;

    public Paddle(GL2 gl2) {
        this.gl2 = gl2;
    }

    public void renderShape(float xPosition, float yPosition) {
        // Size values
        final float width = 2;
        final float height = 0.5f;

        gl2.glTranslatef(xPosition, yPosition, 0);

        // Molding the shape
        gl2.glColor3f(redLevel, greenLevel, blueLevel);
        gl2.glBegin(GL2.GL_QUADS);
        gl2.glVertex2f(-width / 2, -height / 2);
        gl2.glVertex2f(width / 2, -height / 2);
        gl2.glVertex2f(width / 2, height / 2);
        gl2.glVertex2f(-width / 2, height / 2);
        gl2.glEnd();
        gl2.glFlush();

        gl2.glTranslatef(-xPosition, -yPosition, 0);
    }

    public float getPosition(float mouseXPosition) {
        float paddleLimit = (Renderer.pixelsWide / 2) - 1;
        if (mouseXPosition < -paddleLimit) {
            return -paddleLimit;
        } else return Math.min(mouseXPosition, paddleLimit);
    }

    public void setColor(float r, float g, float b) {
        redLevel = Math.max(0, Math.min(1, Math.abs(r)));
        greenLevel = Math.max(0, Math.min(1, Math.abs(g)));
        blueLevel = Math.max(0, Math.min(1, Math.abs(b)));
    }
}
