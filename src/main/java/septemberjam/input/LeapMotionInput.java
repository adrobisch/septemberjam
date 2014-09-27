package septemberjam.input;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Listener;
import septemberjam.GameApplication;

import java.util.concurrent.Callable;

/**
 * @author andreas
 */
public class LeapMotionInput {
    Listener listener;
    private Controller controller;

    public LeapMotionInput(Listener listener) {
        this.listener = listener;
    }

    public void start() {
        this.controller = new Controller();
        this.controller.addListener(listener);
    }

    public void shutdown() {
        if (controller != null) {
            controller.removeListener(listener);
        }
    }
}
