package septemberjam;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;

import java.util.concurrent.Callable;

public class SpaceshipLocationUpdate implements Callable<Void> {
    private static final float MOVE_SPEED = 0.2f;
    public static final float CORRECT_LEAP_MOTION_HAND_DISTANCE = 8.5f;
    private static final float SPACE_SHIP_IS_NOT_FLYING = 0;
    
    private static final float CAMERA_MOVE_FACTOR = 0.5f;
    
    float x;
    float y;
    
    private final Spatial fighterSpatial;
    private Camera camera;
    
    private Vector3f cameraOrigin;
    
    public SpaceshipLocationUpdate(final float x, final float y, Spatial fighterSpatial, Camera camera) {
        this.x = x;
        this.y = y;

        this.cameraOrigin = camera.getLocation().clone();
        
        this.fighterSpatial = fighterSpatial;
        this.camera = camera;
    }

    public void moveRight() {
        updateLocation(1, 0);
    }

    public void moveLeft() {
        updateLocation(-1, 0);
    }

    public void moveDown() {
        updateLocation(0, -1);
    }

    public void moveUp() {
        updateLocation(0, 1);
    }

    @Override
    public Void call() throws Exception {
        updateLocation(new Vector3f(x, y - CORRECT_LEAP_MOTION_HAND_DISTANCE, fighterSpatial.getLocalTranslation().z));
        return null;
    }

    private void updateLocation(int x, int y) {
        Vector3f shipLocation = fighterSpatial.getLocalTranslation();
        shipLocation.x += MOVE_SPEED * x;
        shipLocation.y += MOVE_SPEED * y;
        shipLocation.z = SPACE_SHIP_IS_NOT_FLYING;
        
        updateLocation(shipLocation);
    }
    
    private void updateLocation(Vector3f location) {
        fighterSpatial.getControl(RigidBodyControl.class).setPhysicsLocation(location);
        fighterSpatial.setLocalTranslation(location.x, location.y, location.z);
        
        Vector3f cameraLocation = location.mult(CAMERA_MOVE_FACTOR).add(cameraOrigin);
        
        //camera.setLocation(cameraLocation);
    }

}
