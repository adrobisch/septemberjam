package septemberjam;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class GameApplication extends SimpleApplication {
    @Override
    public void simpleInitApp() {

        Spatial fighterSpatial = assetManager.loadModel("Models/fighter.j3o");
        rootNode.attachChild(fighterSpatial);
    }

    public static void main(String[] args) {
        new GameApplication().start();
    }
}
