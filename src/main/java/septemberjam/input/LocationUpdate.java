package septemberjam.input;

import java.util.concurrent.Callable;
import septemberjam.SpaceShipActions;

public class LocationUpdate implements Callable<Void> {
    
    private static final float CORRECT_LEAP_MOTION_HAND_DISTANCE = 8.5f;
    
    private final SpaceShipActions actions;
    
    private float x;
    private float y;
    
    public LocationUpdate(final float x, final float y, SpaceShipActions actions) {
        this.x = x;
        this.y = y;
        
        this.actions = actions;
    }

    @Override
    public Void call() throws Exception {
        actions.move(x, y - CORRECT_LEAP_MOTION_HAND_DISTANCE);
        return null;
    }
}
