package objects;

import com.jogamp.opengl.GL2;

public class DiamondBlock {
    private final GL2 gl2;

    // Position, size and rotation
    private float xPosition;
    private float yPosition;
    private final float width;
    private final float height;
    private final float rotation;

    // Colors
    private float redLevel = 0.5f;
    private float greenLevel = 0;
    private float blueLevel = 0.5f;

    public DiamondBlock(GL2 gl2, float x, float y) {
        this.gl2 = gl2;
        this.xPosition = x;
        this.yPosition = y;
        this.width = 1;
        this.height = 1;
        this.rotation = 45;
    }

    public void renderShape(float xPosition, float yPosition) {
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
        this.redLevel = Math.max(0, Math.min(1, Math.abs(r)));
        this.greenLevel = Math.max(0, Math.min(1, Math.abs(g)));
        this.blueLevel = Math.max(0, Math.min(1, Math.abs(b)));
    }
}
