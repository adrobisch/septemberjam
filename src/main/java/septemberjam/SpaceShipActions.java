package septemberjam;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import septemberjam.baem.WeaponControl;


public class SpaceShipActions {
    
    private static final float MOVE_SPEED = 0.2f;
    private static final float SPACE_SHIP_IS_NOT_FLYING = 0;
    
    private static final float CAMERA_MOVE_FACTOR = 0.5f;
    
    private final Spatial fighter;
    private final Camera camera;
    
    private final Vector3f cameraOrigin;

    public SpaceShipActions(Camera camera, Spatial fighter) {
        this.cameraOrigin = camera.getLocation().clone();
        
        this.camera = camera;
        this.fighter = fighter;
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

    public void move(float x, float y) {
        updateLocation(new Vector3f(x, y, fighter.getLocalTranslation().z));
    }
    
    public void shoot() {
        fighter.getControl(WeaponControl.class).setFire(true);
    }

    private void updateLocation(int x, int y) {
        Vector3f shipLocation = fighter.getLocalTranslation();
        shipLocation.x += MOVE_SPEED * x;
        shipLocation.y += MOVE_SPEED * y;
        shipLocation.z = SPACE_SHIP_IS_NOT_FLYING;
        
        updateLocation(shipLocation);
    }
    
    private void updateLocation(Vector3f location) {
        fighter.getControl(RigidBodyControl.class).setPhysicsLocation(location);
        fighter.setLocalTranslation(location.x, location.y, location.z);
        
        Vector3f cameraLocation = location.mult(CAMERA_MOVE_FACTOR).add(cameraOrigin);
        
        camera.setLocation(cameraLocation);
    }

}
