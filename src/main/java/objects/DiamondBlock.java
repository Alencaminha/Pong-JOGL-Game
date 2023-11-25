package objects;

import com.jogamp.opengl.GL2;
import graphics.EventListener;

public class DiamondBlock {
    private static float redLevel = 0.5f;
    private static float greenLevel = 0;
    private static float blueLevel = 0.5f;
    private static float alphaLevel = 0.5f;
    private static float rotation = 45;

    public static void fillRect(float x, float y, float width, float height) {
        GL2 gl = EventListener.gl;

        gl.glTranslatef(x, y, 0);
        gl.glRotatef(-rotation, 0, 0, 1);

        // Creating a square
        gl.glColor4f(redLevel, greenLevel, blueLevel, alphaLevel);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(-width / 2, -height / 2);
        gl.glVertex2f(width / 2, -height / 2);
        gl.glVertex2f(width / 2, height / 2);
        gl.glVertex2f(-width / 2, height / 2);
        gl.glEnd();
        gl.glFlush();

        gl.glRotatef(rotation, 0, 0, 1);
        gl.glTranslatef(-x, -y, 0);
    }

    public static void setColor(float r, float g, float b, float a) {
        redLevel = Math.max(0, Math.min(1, Math.abs(r)));
        greenLevel = Math.max(0, Math.min(1, Math.abs(g)));
        blueLevel = Math.max(0, Math.min(1, Math.abs(b)));
        alphaLevel = Math.max(0, Math.min(1, Math.abs(a)));
    }

    public static void setRotation(float r) {
        rotation = r;
    }
}
