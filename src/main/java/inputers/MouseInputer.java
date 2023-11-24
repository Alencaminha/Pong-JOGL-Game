package inputers;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

public class MouseInputer implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        System.out.println("Mouse Click: " + mouseEvent.getX() + " / " + mouseEvent.getY());
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
        System.out.println("Mouse Move: " + mouseEvent.getX() + " / " + mouseEvent.getY());
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseWheelMoved(MouseEvent mouseEvent) {

    }
}
