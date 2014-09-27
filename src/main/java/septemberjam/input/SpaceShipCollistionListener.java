package septemberjam.input;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
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
        if (!event.getNodeA().getName().equals("fighter") && !event.getNodeB().getName().equals("fighter")) {
            gameApplication.getRootNode().detachChild(event.getNodeA());
            gameApplication.getRootNode().detachChild(event.getNodeB());
            gameApplication.getFlame().setLocalTranslation(event.getNodeA().getLocalTranslation());
            gameApplication.getFlame().emitAllParticles();
        } else if (event.getNodeA().getName().equals("fighter") || event.getNodeB().getName().equals("fighter")) {
            gameApplication.handleShipCollision(getNonShipNode(event));
        }
    }

    private Spatial getNonShipNode(PhysicsCollisionEvent event) {
        if (event.getNodeA().getName().equals("fighter")) {
            return event.getNodeB();
        } else {
            return event.getNodeA();
        }
    }
}
