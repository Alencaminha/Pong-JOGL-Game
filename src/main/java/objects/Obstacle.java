package objects;

import com.jogamp.opengl.GL2;

public class Obstacle {
    // Engine renderer
    private final GL2 gl2;

    // Positions and size
    public float x = 0;
    public float y = 2;
    public final float width = 1;
    public final float height = 1;

    public Obstacle(GL2 gl2) {
        this.gl2 = gl2;
    }

    public void renderShape(float x, float y) {
        // Rotation setup
        final float rotation = 45;
        gl2.glTranslatef(x, y, 0);
        gl2.glRotatef(-rotation, 0, 0, 1);

        // Molding the shape
        gl2.glColor3f(0, 1, 0);
        gl2.glBegin(GL2.GL_QUADS);
            gl2.glVertex2f(-width / 2, -height / 2);
            gl2.glVertex2f(width / 2, -height / 2);
            gl2.glVertex2f(width / 2, height / 2);
            gl2.glVertex2f(-width / 2, height / 2);
        gl2.glEnd();
        gl2.glFlush();

        // Rotation cleanup
        gl2.glRotatef(rotation, 0, 0, 1);
        gl2.glTranslatef(-x, -y, 0);
    }
}
