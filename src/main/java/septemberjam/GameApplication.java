package septemberjam;

import com.jme3.app.SimpleApplication;
import com.jme3.input.CameraInput;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import septemberjam.input.LeapMotionInput;
import septemberjam.input.LeapMotionListener;

public class GameApplication extends SimpleApplication {
    LeapMotionInput leapMotionInput;
    private Spatial fighter;
    
  @Override
  public void simpleInitApp() {
  
    setupInput();
    getFlyByCamera().setEnabled(false);

    fighter = assetManager.loadModel("Models/fighter.j3o");
    fighter.scale(0.7f);
    fighter.rotate(0, degreeAsRadian(180), 0);
    fighter.setLocalTranslation(0, -2.5f, 0);
    rootNode.attachChild(fighter);

    
    Spatial rock1 = createRock(new Vector3f(-3, 0, -10), 0.1f, "rock_01");
    Spatial rock2 = createRock(new Vector3f(-2, 0, -10), 0.1f, "rock_02");
    Spatial rock3 = createRock(new Vector3f(-1, 0, -10), 0.1f, "rock_03");
    Spatial rock4 = createRock(new Vector3f(0, 0, -10), 0.1f, "rock_04");
    Spatial rock5 = createRock(new Vector3f(1, 0, -10), 0.1f, "rock_05");
    Spatial rock6= createRock(new Vector3f(2, 0, -10), 0.1f, "rock_06");
    Spatial rockFit = createRock(new Vector3f(3, 0, -10), 0.005f, "rock_fit");
    
    rootNode.attachChild(rock1);
    rootNode.attachChild(rock2);
    rootNode.attachChild(rock3);
    rootNode.attachChild(rock4);
    rootNode.attachChild(rock5);
    rootNode.attachChild(rock6);
    rootNode.attachChild(rockFit);
  }
  
  public float degreeAsRadian(float degree) {
    return (degree / 180 * FastMath.PI);
  }

    public static void main(String[] args) {
      new GameApplication().start();
    }

    private Spatial createRock(Vector3f position, float scale, String model) {

      Spatial spatial = assetManager.loadModel("Models/" + model + ".j3o");
      Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

      material.setTexture("ColorMap", assetManager.loadTexture("Models/diffuse.tga"));

      spatial.setMaterial(material);

      spatial.scale(scale, scale, scale);
      spatial.move(position.x, position.y, position.z);

      return spatial;
    }

    private void setupInput() {
        //setupLeapMotion();
        setupKeyMapping();
    }

    private void setupLeapMotion() {
        leapMotionInput = new LeapMotionInput(new LeapMotionListener(this));
        leapMotionInput.start();
    }

    private void setupKeyMapping() {
        String strafe_left_mapping = "STRAFE_LEFT";
        final float MOVE_SPEED = 0.1f;

        inputManager.addMapping(strafe_left_mapping, new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("STRAFE_RIGHT", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("STRAFE_UP", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("STRAFE_DOWN", new KeyTrigger(KeyInput.KEY_S));

        inputManager.addListener(new AnalogListener() {
            @Override
            public void onAnalog(String name, float value, float tpf) {
                Vector3f currentTranslation = fighter.getLocalTranslation();
                fighter.setLocalTranslation(currentTranslation.x - MOVE_SPEED, currentTranslation.y, currentTranslation.z);
            }
        }, strafe_left_mapping);
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
}
