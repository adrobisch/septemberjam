package septemberjam.input;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Vector;
import septemberjam.GameApplication;

public class LeapMotionListener extends Listener {

    public static final float MOVE_FACTOR = 30f;
    GameApplication gameApplication;

    public LeapMotionListener(GameApplication gameApplication) {
        this.gameApplication = gameApplication;
    }

    public void onConnect(Controller controller) {
        System.out.println("Connected");
    }

    public void onFrame(Controller controller) {
        System.out.println();
        Hand rightHand = controller.frame().hands().rightmost();
        if (rightHand.isValid()) {
            Vector palmPosition = rightHand.stabilizedPalmPosition();
            gameApplication.updateSpaceShipLocation(palmPosition.getX() / MOVE_FACTOR, palmPosition.getY() / MOVE_FACTOR);
        }
    }
}
