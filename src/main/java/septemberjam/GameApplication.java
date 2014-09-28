package septemberjam;

import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.effect.shapes.EmitterSphereShape;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import septemberjam.baem.WeaponControl;
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

    private ParticleEmitter flame;

    private AudioNode collisionSound;

    @Override
    public void simpleInitApp() {
        setupPhysics();
        setupGui();
        setupRootControls();
        addFighterModel();
        
        setupActions();
		initializeLevelWithSomeRocks();
        createFlame();
        createCollisionSound();
        setupInput();
        disableMovableCamera();
    }

    private void createFlame(){
        flame = new ParticleEmitter("Flame", ParticleMesh.Type.Point, 32);
        flame.setSelectRandomImage(true);
        flame.setStartColor(new ColorRGBA(1f, 0.4f, 0.05f, 1));
        flame.setEndColor(new ColorRGBA(.4f, .22f, .12f, 0f));
        flame.setStartSize(1.3f);
        flame.setEndSize(2f);
        flame.setShape(new EmitterSphereShape(Vector3f.ZERO, 1f));
        flame.setParticlesPerSec(0);
        flame.setLowLife(.6f);
        flame.setHighLife(.6f);
        flame.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 0, 0));
        flame.getParticleInfluencer().setVelocityVariation(1f);
        flame.setImagesX(2);
        flame.setImagesY(2);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/flame.png"));
        mat.setBoolean("PointSprite", true);
        flame.setMaterial(mat);
        renderManager.preloadScene(flame);
        rootNode.attachChild(flame);
    }

    private void createCollisionSound() {
        collisionSound = new AudioNode(assetManager, "Sound/Effects/Gun.wav", true);
        collisionSound.setLooping(false);
        collisionSound.setPositional(true);
        collisionSound.setVolume(3);
        rootNode.attachChild(collisionSound);
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
        
        fighter.addControl(new WeaponControl(this));
        
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
        spatial.setUserData("type", "rock");

        Vector3f shipDirection = fighter.getLocalTranslation().subtract(position).normalize().mult(rockSpeed);

        spatial.addControl(new RigidBodyControl(new CapsuleCollisionShape(0.1f, 0.1f), 0.5f));
        spatial.getControl(RigidBodyControl.class).setPhysicsLocation(position);
        spatial.getControl(RigidBodyControl.class).applyCentralForce(shipDirection);
        spatial.getControl(RigidBodyControl.class).applyTorque(new Vector3f(0.1f, 0.1f, 0.1f));
        getPhysicsSpace().add(spatial);

        rootNode.attachChild(spatial);

        return spatial;
    }

    public void initializeLevelWithSomeRocks() {
        rootNode.addControl(new CreateRockControl(this, 0.7f));

        float startZ = -30f;
        float rockSpeed = 60f;
	    createRock(new Vector3f(-3, 1, startZ), 0.1f, "rock_01", rockSpeed);
        createRock(new Vector3f(-2, 2, startZ), 0.1f, "rock_02", rockSpeed);
        createRock(new Vector3f(-1, -2, startZ), 0.1f, "rock_03", rockSpeed);
        createRock(new Vector3f(0, -6, startZ), 0.1f, "rock_04", rockSpeed);
        createRock(new Vector3f(1, -2, startZ), 0.1f, "rock_05", rockSpeed);
        createRock(new Vector3f(2, -6, startZ), 0.1f, "rock_06", rockSpeed);
        createRock(new Vector3f(3, -2, startZ), 0.005f, "rock_fit", rockSpeed);
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
        // cam.setLocation(new Vector3f(0, 10f, 0));
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

    public void handleShipCollision(Spatial nonShipNode) {
        System.out.println("ship happens!");
        flame.setLocalTranslation(getFighter().getLocalTranslation());
        flame.emitAllParticles();
        collisionSound.play();
        getRootNode().detachChild(nonShipNode);
    }

    public Spatial getFighter() {
        return fighter;
    }

    public ParticleEmitter getFlame() {
        return flame;
    }

    public static void main(String[] args) {
        new GameApplication().start();
    }
}
