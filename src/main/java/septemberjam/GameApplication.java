package septemberjam;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import septemberjam.control.CreateRockControl;
import septemberjam.input.KeyboardInput;
import septemberjam.input.LeapMotionInput;
import septemberjam.input.LeapMotionListener;
import septemberjam.input.SpaceShipCollistionListener;
import septemberjam.input.LocationUpdate;


public class GameApplication extends SimpleApplication {
    
    LeapMotionInput leapMotionInput;
    
    private Spatial fighter;
    
    private SpaceShipActions actions;
    
    private BulletAppState bulletAppState;

    @Override
    public void simpleInitApp() {
        setupPhysics();
        setupGui();
        setupRootControls();
        addFighterModel();
        
        setupActions();
		addRocks();
        setupInput();
        disableMovableCamera();
    }

    private void setupRootControls() {
        rootNode.addControl(new CreateRockControl(this, 0.5f));
    }

    private void setupGui() {
        setDisplayStatView(false);
        setDisplayFps(false);
    }

    private void setupPhysics() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        //getPhysicsSpace().enableDebug(assetManager);
        getPhysicsSpace().setGravity(Vector3f.ZERO);
        getPhysicsSpace().addCollisionListener(new SpaceShipCollistionListener(this));
    }

    public PhysicsSpace getPhysicsSpace() {
        return bulletAppState.getPhysicsSpace();
    }

    private void addFighterModel() {
        fighter = assetManager.loadModel("Models/fighter.j3o");
        fighter.scale(0.7f);
        fighter.rotate(0, degreeAsRadian(180), 0);
        fighter.setName("fighter");
        rootNode.attachChild(fighter);

        fighter.addControl(new RigidBodyControl(2));
        fighter.getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(0, -2.5f, 0));
        fighter.getControl(RigidBodyControl.class).setKinematic(true);
        getPhysicsSpace().add(fighter);
    }

    public float degreeAsRadian(float degree) {
        return (degree / 180 * FastMath.PI);
    }

    public Spatial createRock(Vector3f position, float scale, String model, float rockSpeed) {
        Spatial spatial = assetManager.loadModel("Models/" + model + ".j3o");
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

        material.setTexture("ColorMap", assetManager.loadTexture("Models/diffuse.tga"));

        spatial.setMaterial(material);

        spatial.scale(scale, scale, scale);

        Vector3f shipDirection = fighter.getLocalTranslation().subtract(position).normalize().mult(rockSpeed);

        spatial.addControl(new RigidBodyControl(new CapsuleCollisionShape(0.1f, 0.1f), 0.5f));
        spatial.getControl(RigidBodyControl.class).setPhysicsLocation(position);
        spatial.getControl(RigidBodyControl.class).applyCentralForce(shipDirection);
        spatial.getControl(RigidBodyControl.class).applyTorque(new Vector3f(0.1f, 0.1f, 0.1f));
        getPhysicsSpace().add(spatial);

        return spatial;
    }

    public void addRocks() {
        rootNode.addControl(new CreateRockControl(this, 0.7f));

        float startZ = -30f;
        float rockSpeed = 60f;
	    Spatial rock1 = createRock(new Vector3f(-3, 1, startZ), 0.1f, "rock_01", rockSpeed);
        Spatial rock2 = createRock(new Vector3f(-2, 2, startZ), 0.1f, "rock_02", rockSpeed);
        Spatial rock3 = createRock(new Vector3f(-1, -2, startZ), 0.1f, "rock_03", rockSpeed);
        Spatial rock4 = createRock(new Vector3f(0, -6, startZ), 0.1f, "rock_04", rockSpeed);
        Spatial rock5 = createRock(new Vector3f(1, -2, startZ), 0.1f, "rock_05", rockSpeed);
        Spatial rock6= createRock(new Vector3f(2, -6, startZ), 0.1f, "rock_06", rockSpeed);
        Spatial rockFit = createRock(new Vector3f(3, -2, startZ), 0.005f, "rock_fit", rockSpeed);
    
        rootNode.attachChild(rock1);
        rootNode.attachChild(rock2);
        rootNode.attachChild(rock3);
        rootNode.attachChild(rock4);
        rootNode.attachChild(rock5);
        rootNode.attachChild(rock6);
        rootNode.attachChild(rockFit);
    }

    private void setupInput() {
        if (Boolean.parseBoolean(System.getProperty("leap.motion.enabled"))) {
            setupLeapMotion();
        }
     
        KeyboardInput keyboardInput = new KeyboardInput();
        keyboardInput.setupKeyMapping(inputManager, actions);
    }

    private void setupActions() {
        actions = new SpaceShipActions(cam, fighter);
    }

    private void setupLeapMotion() {
        leapMotionInput = new LeapMotionInput(new LeapMotionListener(this));
        leapMotionInput.start();
    }

    private void disableMovableCamera() {
        getFlyByCamera().setEnabled(false);
        //cam.setLocation(new Vector3f(0, 10f, 0));
        //cam.lookAt(fighter.getLocalTranslation(), Vector3f.UNIT_Y);
    }

    @Override
    protected void destroyInput() {
        super.destroyInput();
        if (leapMotionInput != null) {
            leapMotionInput.shutdown();
        }
    }

    public void updateSpaceShipLocation(float x, float y) {
        enqueue(new LocationUpdate(x, y, actions));
    }

    public void handleShipCollision() {
        System.out.println("ship hit!");
    }

    public static void main(String[] args) {
        new GameApplication().start();
    }
}
