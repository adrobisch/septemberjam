package septemberjam;

import com.jme3.scene.Spatial;

import java.util.concurrent.Callable;

public class SpaceshipLocationUpdate implements Callable<Void> {
    private final Spatial fighterSpatial;
    float x;
    float y;

    public SpaceshipLocationUpdate(final float x, final float y, Spatial fighterSpatial) {
        this.x = x;
        this.y = y;
        this.fighterSpatial = fighterSpatial;
    }

    @Override
    public Void call() throws Exception {
        fighterSpatial.setLocalTranslation(x, y - 8.5f, fighterSpatial.getLocalTranslation().z);
        return null;
    }
}
