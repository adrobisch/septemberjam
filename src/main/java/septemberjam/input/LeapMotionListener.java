package septemberjam.input;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Listener;
import septemberjam.GameApplication;

public class LeapMotionListener extends Listener {

    GameApplication gameApplication;

    public void onConnect(Controller controller) {
        System.out.println("Connected");
    }

    public void onFrame(Controller controller) {
        System.out.println();
        if (controller.frame().hands().count() == 1) {
            controller.frame().hand(0).wristPosition();
        }
    }
}
