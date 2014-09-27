package septemberjam;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.leapmotion.leap.Controller;
import septemberjam.input.LeapMotionInput;
import septemberjam.input.LeapMotionListener;

/**
 * @author andreas
 */
public class GameApplication extends SimpleApplication {
    LeapMotionInput leapMotionInput;

    @Override
    public void simpleInitApp() {
        setupInput();

        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        rootNode.attachChild(geom);
    }

    private void setupInput() {
        leapMotionInput = new LeapMotionInput(new LeapMotionListener());
        leapMotionInput.start();
    }

    @Override
    protected void destroyInput() {
        super.destroyInput();
        leapMotionInput.shutdown();
    }

    public void updateSpaceShipLocation(float x, float y) {

    }

    public static void main(String[] args) {
        GameApplication app = new GameApplication();
        app.start();
    }
}
