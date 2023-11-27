package graphics;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;

public class Renderer {
    private static GLWindow glWindow = null;
    private static GLProfile glProfile = null;
    public static int screenWidth = 640;
    public static int screenHeight = 360;
    public static float pixelsWide = 10;

    public static void init() {
        // Initial mandatory setup for everything to have standard settings
        GLProfile.initSingleton();
        glProfile = GLProfile.get(GLProfile.GL2);
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);

        // Creating and setting some window configurations
        glWindow = GLWindow.create(glCapabilities);
        glWindow.setSize(screenWidth, screenHeight);
        glWindow.setResizable(true);
        // glWindow.setFullscreen(true);

        // Adding the listeners
        MainMenu mainMenu = new MainMenu();
        glWindow.addGLEventListener(mainMenu);
        glWindow.addMouseListener(mainMenu);
        /*GameScene gameScene = new GameScene();
        glWindow.addGLEventListener(gameScene);
        glWindow.addKeyListener(gameScene);*/

        // Setting the game to 60 FPS
        FPSAnimator fpsAnimator = new FPSAnimator(glWindow, 60);
        fpsAnimator.start();

        // Properly ends the program when the window is closed
        glWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDestroyNotify(WindowEvent windowEvent) {
                fpsAnimator.stop();
                System.exit(0);
            }
        });

        // Finally setting the window visible, starting the game
        glWindow.setVisible(true);
    }

    public static GLProfile getGlProfile() {
        return glProfile;
    }

    public static void main(String[] args) {
        init();
    }
}
