package graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import objects.DiamondBlock;

public class EventListener implements GLEventListener {
    public static GL2 gl = null;
    private float screenWidth = Renderer.pixelsWide;
    private float screenHeight = Renderer.screenHeight / (Renderer.screenWidth / Renderer.pixelsWide);

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        // Setting the background color to white
        gl = glAutoDrawable.getGL().getGL2();
        gl.glClearColor(1, 1, 1, 1);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

        DiamondBlock.setColor(0, 0, 1, 1);
        DiamondBlock.fillRect(0, 0, 1, 1);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        gl = glAutoDrawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-screenWidth / 2, screenWidth / 2, -screenHeight / 2, screenHeight / 2, -1, 1);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }
}
