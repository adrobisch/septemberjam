package septemberjam;

import com.jme3.app.SimpleApplication;
import com.jme3.math.FastMath;
import com.jme3.scene.Spatial;
import septemberjam.input.LeapMotionInput;
import septemberjam.input.LeapMotionListener;

public class GameApplication extends SimpleApplication {
    LeapMotionInput leapMotionInput;
    private Spatial fighter;

    @Override
    public void simpleInitApp() {
        setupInput();

        fighter = assetManager.loadModel("Models/fighter.j3o");
        fighter.scale(0.7f);
        fighter.rotate(0, degreeAsRadian(180), 0);
        fighter.setLocalTranslation(0, -2.5f, 0);
        rootNode.attachChild(fighter);
    }

    public float degreeAsRadian(float degree) {
        return (degree / 180 * FastMath.PI);
    }

    private void setupInput() {
        leapMotionInput = new LeapMotionInput(new LeapMotionListener(this));
        leapMotionInput.start();
    }

    @Override
    protected void destroyInput() {
        super.destroyInput();
        leapMotionInput.shutdown();
    }

    public void updateSpaceShipLocation(float x, float y) {
        enqueue(new SpaceshipLocationUpdate(x, y, fighter));
    }

    public static void main(String[] args) {
        new GameApplication().start();
    }
}
