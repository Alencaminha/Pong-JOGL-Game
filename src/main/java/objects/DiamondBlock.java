package objects;

import com.jogamp.opengl.GL2;

public class DiamondBlock {
    // Engine renderer
    private final GL2 gl2;

    // Colors
    private float redLevel = 0.5f;
    private float greenLevel = 0;
    private float blueLevel = 0.5f;

    public DiamondBlock(GL2 gl2) {
        this.gl2 = gl2;
    }

    public void renderShape(float xPosition, float yPosition) {
        // Size and rotation values
        final float width = 1;
        final float height = 1;
        final float rotation = 45;

        // Rotation setup
        gl2.glTranslatef(xPosition, yPosition, 0);
        gl2.glRotatef(-rotation, 0, 0, 1);

        // Molding the shape
        gl2.glColor3f(redLevel, greenLevel, blueLevel);
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
        redLevel = Math.max(0, Math.min(1, Math.abs(r)));
        greenLevel = Math.max(0, Math.min(1, Math.abs(g)));
        blueLevel = Math.max(0, Math.min(1, Math.abs(b)));
    }
}
