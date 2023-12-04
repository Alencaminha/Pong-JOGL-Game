package objects;

import com.jogamp.opengl.GL2;
import graphics.GameScene;

public class Paddle {
    // Engine renderer
    private final GL2 gl2;

    // Positions and size
    public float x = 0;
    public float y = -2.5f;
    public final float width = 2;
    public final float height = 0.5f;

    // Colors
    private float red = 0.5f;
    private float green = 0;
    private float blue = 0.5f;

    public Paddle(GL2 gl2) {
        this.gl2 = gl2;
    }

    public void renderShape(float x, float y) {
        gl2.glTranslatef(x, y, 0);

        // Molding the shape
        gl2.glColor3f(red, green, blue);
        gl2.glBegin(GL2.GL_QUADS);
        gl2.glVertex2f(-width / 2, -height / 2);
        gl2.glVertex2f(width / 2, -height / 2);
        gl2.glVertex2f(width / 2, height / 2);
        gl2.glVertex2f(-width / 2, height / 2);
        gl2.glEnd();
        gl2.glFlush();

        gl2.glTranslatef(-x, -y, 0);
    }

    public void setColor(float r, float g, float b) {
        red = Math.max(0, Math.min(1, Math.abs(r)));
        green = Math.max(0, Math.min(1, Math.abs(g)));
        blue = Math.max(0, Math.min(1, Math.abs(b)));
    }

    public float getPosition(float mouseXPosition) {
        float paddleLimit = (GameScene.screenWidth / 2) - (this.width / 2);
        if (mouseXPosition < -paddleLimit) {
            return -paddleLimit;
        } else return Math.min(mouseXPosition, paddleLimit);
    }
}
