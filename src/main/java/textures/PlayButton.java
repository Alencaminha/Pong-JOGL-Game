package textures;

import com.jogamp.opengl.GL2;
import graphics.TextureRenderer;

public class PlayButton {
    // Engine renderer
    private final TextureRenderer textureRenderer = new TextureRenderer();
    private final GL2 gl2;

    // Position and size
    public final float x = -1;
    public final float y = -2.5f;
    public final float width = 1;
    public final float height = 1;

    public PlayButton(GL2 gl2) {
        this.gl2 = gl2;
    }

    public void renderShape() {
        gl2.glTranslatef(x, y, 0);
        textureRenderer.loadTexture(gl2, "images/Play.png");

        // Molding the shape
        gl2.glColor3f(1, 1, 1);
        gl2.glBegin(GL2.GL_QUADS);
            gl2.glTexCoord2f(0, 0);  gl2.glVertex2f(-width / 2, -height / 2);
            gl2.glTexCoord2f(1, 0);   gl2.glVertex2f(width / 2, -height / 2);
            gl2.glTexCoord2f(1, 1);    gl2.glVertex2f(width / 2, height / 2);
            gl2.glTexCoord2f(0, 1);   gl2.glVertex2f(-width / 2, height / 2);
        gl2.glEnd();
        gl2.glFlush();

        textureRenderer.disableTexture();
        gl2.glTranslatef(-x, -y, 0);
    }
}
