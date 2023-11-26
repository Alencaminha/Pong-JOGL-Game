package inputs;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

public class KeyboardInput implements KeyListener {
    private float playerSpeed;

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        float speed = 0.2f;

        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                playerSpeed = -speed;
                System.out.println("Left");
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                playerSpeed = speed;
                System.out.println("Right");
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                playerSpeed = 0;
                System.out.println("Left");
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                playerSpeed = 0;
                System.out.println("Right");
                break;
        }
    }

    public float getPlayerSpeed() {
        return playerSpeed;
    }
}
