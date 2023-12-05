package graphics;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;

public class GameRenderer {
    private static GLWindow glWindow;
    public static FPSAnimator fpsAnimator;

    public static void main(String[] args) {
        // Initial mandatory setup for everything to have standard settings
        GLProfile.initSingleton();
        GLProfile glProfile = GLProfile.get(GLProfile.GL2);
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);

        // Creating the window
        glWindow = GLWindow.create(glCapabilities);
        glWindow.setFullscreen(true);

        // Adding the listeners
        GameScene gameScene = new GameScene();
        glWindow.addGLEventListener(gameScene);
        glWindow.addKeyListener(gameScene.keyAdapter);
        glWindow.addMouseListener(gameScene.mouseAdapter);

        // Setting the game to target 60 FPS
        fpsAnimator = new FPSAnimator(glWindow, 60, true);
        fpsAnimator.setFPS(60);
        fpsAnimator.start();

        // Properly ends the program when the window is closed
        glWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDestroyNotify(WindowEvent windowEvent) {
                super.windowDestroyNotify(windowEvent);
                fpsAnimator.stop();
                System.exit(0);
            }
        });

        // Finally setting the window visible, starting the game
        glWindow.setVisible(true);
    }

    public static int getWidth() {
        return glWindow.getWidth();
    }

    public static int getHeight() {
        return glWindow.getHeight();
    }
}
