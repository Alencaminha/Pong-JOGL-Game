package graphics;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.gl2.GLUT;

public class MainMenu implements GLEventListener, MouseListener {
    // Engine renderers
    private static GL2 gl2;
    private static final GLUT glut = new GLUT();

    // Screen sizes
    private final float screenWidth = Renderer.pixelsWide;
    private final float screenHeight = Renderer.screenHeight / (Renderer.screenWidth / Renderer.pixelsWide);

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        // Setting the background color to white
        gl2 = glAutoDrawable.getGL().getGL2();
        gl2.glClearColor(1, 1, 1, 1);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
        // Kill scene
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        gl2 = glAutoDrawable.getGL().getGL2();
        gl2.glClear(GL2.GL_COLOR_BUFFER_BIT);

        renderText(0, 1, "PONG");
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        gl2 = glAutoDrawable.getGL().getGL2();
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();
        gl2.glOrtho(-screenWidth / 2, screenWidth / 2, -screenHeight / 2, screenHeight / 2, -1, 1);
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseWheelMoved(MouseEvent mouseEvent) {

    }

    private static void renderText(float x, float y, String text) {
        gl2.glColor3f(0, 0, 0);
        gl2.glRasterPos2f(x, y);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, text);
    }
}
