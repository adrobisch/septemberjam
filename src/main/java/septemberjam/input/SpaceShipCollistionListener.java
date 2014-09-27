package septemberjam.input;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import septemberjam.GameApplication;

/**
 * @author andreas
 */
public class SpaceShipCollistionListener implements PhysicsCollisionListener {

    GameApplication gameApplication;

    public SpaceShipCollistionListener(GameApplication gameApplication) {
        this.gameApplication = gameApplication;
    }

    @Override
    public void collision(PhysicsCollisionEvent event) {
        String aName = event.getNodeA().getName();
        String bName = event.getNodeB().getName();

        if (aName.equals("fighter") || bName.equals("fighter")) {
            gameApplication.handleShipCollision();
        }
    }
}
