package septemberjam;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import septemberjam.input.KeyboardInput;
import septemberjam.input.LeapMotionInput;
import septemberjam.input.LeapMotionListener;

public class GameApplication extends SimpleApplication {
    LeapMotionInput leapMotionInput;
    private Spatial fighter;
    private BulletAppState bulletAppState;

    @Override
    public void simpleInitApp() {
        setupPhysics();
        setupGui();
        addFighterModel();
		addRocks();
        setupInput();
        disableMovableCamera();
    }

    private void setupGui() {
        setDisplayStatView(false);
        setDisplayFps(false);
    }

    private void setupPhysics() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        getPhysicsSpace().enableDebug(assetManager);
        getPhysicsSpace().setGravity(Vector3f.ZERO);
    }

    public PhysicsSpace getPhysicsSpace() {
        return bulletAppState.getPhysicsSpace();
    }

    private void addFighterModel() {
        fighter = assetManager.loadModel("Models/fighter.j3o");
        fighter.scale(0.7f);
        fighter.rotate(0, degreeAsRadian(180), 0);
        rootNode.attachChild(fighter);

        fighter.addControl(new RigidBodyControl(2));
        fighter.getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(0, -2.5f, 0));
        getPhysicsSpace().add(fighter);
    }

    public float degreeAsRadian(float degree) {
        return (degree / 180 * FastMath.PI);
    }

	public void addRocks() {

        Spatial rock1 = createRock(new Vector3f(-3, 0, -10), 0.1f, "rock_01", 60f);
        Spatial rock2 = createRock(new Vector3f(-2, 0, -10), 0.1f, "rock_02", 60f);
        Spatial rock3 = createRock(new Vector3f(-1, 0, -10), 0.1f, "rock_03", 60f);
        Spatial rock4 = createRock(new Vector3f(0, 0, -10), 0.1f, "rock_04", 60f);
        Spatial rock5 = createRock(new Vector3f(1, 0, -10), 0.1f, "rock_05", 60f);
        Spatial rock6= createRock(new Vector3f(2, 0, -10), 0.1f, "rock_06", 60f);
        Spatial rockFit = createRock(new Vector3f(3, 0, -10), 0.005f, "rock_fit", 60f);

        rootNode.attachChild(rock1);
        rootNode.attachChild(rock2);
        rootNode.attachChild(rock3);
        rootNode.attachChild(rock4);
        rootNode.attachChild(rock5);
        rootNode.attachChild(rock6);
        rootNode.attachChild(rockFit);
	}

    private Spatial createRock(Vector3f position, float scale, String model, Float speed) {

        Spatial spatial = assetManager.loadModel("Models/" + model + ".j3o");
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

        material.setTexture("ColorMap", assetManager.loadTexture("Models/diffuse.tga"));

        spatial.setMaterial(material);

        spatial.scale(scale, scale, scale);

        spatial.addControl(new RigidBodyControl(0.5f));
        spatial.getControl(RigidBodyControl.class).setPhysicsLocation(position);
        spatial.getControl(RigidBodyControl.class).applyCentralForce(new Vector3f(0, 0, speed));
        getPhysicsSpace().add(spatial);

        return spatial;
    }


    private void setupInput() {
        if (Boolean.parseBoolean(System.getProperty("leap.motion.enabled"))) {
            setupLeapMotion();
        }
        KeyboardInput keyboardInput = new KeyboardInput();
        keyboardInput.setupKeyMapping(inputManager, new SpaceshipLocationUpdate(0f, 0f, fighter));
    }

    private void setupLeapMotion() {
        leapMotionInput = new LeapMotionInput(new LeapMotionListener(this));
        leapMotionInput.start();
    }

    private void disableMovableCamera() {
        getFlyByCamera().setEnabled(false);
    }

    @Override
    protected void destroyInput() {
        super.destroyInput();
        if (leapMotionInput != null) {
            leapMotionInput.shutdown();
        }
    }

    public void updateSpaceShipLocation(float x, float y) {
        enqueue(new SpaceshipLocationUpdate(x, y, fighter));
    }

    public static void main(String[] args) {
        new GameApplication().start();
    }
}
