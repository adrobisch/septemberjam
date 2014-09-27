package septemberjam.input;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;

/**
 * @author andreas
 */
public class SpaceShipCollistionListener implements PhysicsCollisionListener {
    @Override
    public void collision(PhysicsCollisionEvent event) {
        System.out.println(event.getNodeA().getName());
        System.out.println(event.getNodeB().getName());
    }
}
