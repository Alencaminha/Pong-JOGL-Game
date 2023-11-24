package graphics;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;
import inputers.KeyboardInputer;
import inputers.MouseInputer;

public class Renderer {
    private static GLWindow window = null;
    public static int screenWidth = 640;
    public static int screenHeight = 360;
    public static float pixelsWide = 10;

    public static void init() {
        // Initial mandatory setup for everything to have standard settings
        GLProfile.initSingleton();
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(profile);

        // Creating and setting some window configurations
        window = GLWindow.create(caps);
        window.setSize(screenWidth, screenHeight);
        window.setResizable(true);

        // Adding the listeners to the window
        window.addGLEventListener(new EventListener());
        window.addKeyListener(new KeyboardInputer());
        window.addMouseListener(new MouseInputer());

        // Setting the game to 60 FPS
        FPSAnimator animator = new FPSAnimator(window, 60);
        animator.start();

        // Finally setting the window visible, starting the game
        window.setVisible(true);
    }
}
