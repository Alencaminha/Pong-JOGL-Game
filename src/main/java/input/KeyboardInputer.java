package input;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

public class KeyboardInputer implements KeyListener {
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_A || keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            System.out.println("Left");
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_D || keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            System.out.println("Right");
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
