package septemberjam;

import com.jme3.app.SimpleApplication;
import com.jme3.math.FastMath;
import com.jme3.scene.Spatial;

public class GameApplication extends SimpleApplication {
    @Override
    public void simpleInitApp() {

        Spatial fighter = assetManager.loadModel("Models/fighter.j3o");
        fighter.scale(0.7f);
        fighter.rotate(0, degreeAsRadian(180), 0);
        fighter.setLocalTranslation(0, -2.5f, 0);
        rootNode.attachChild(fighter);
    }

    public float degreeAsRadian(float degree) {
        return (degree / 180 * FastMath.PI);
    }

    public static void main(String[] args) {
        new GameApplication().start();
    }
}
