package septemberjam;

import com.jme3.scene.Spatial;

import java.util.concurrent.Callable;

public class SpaceshipLocationUpdate implements Callable<Void> {
    private static final float MOVE_SPEED = 0.1f;
    public static final float CORRECT_LEAP_MOTION_HAND_DISTANCE = 8.5f;
    private static final float SPACE_SHIP_IS_NOT_FLYING = 0;
    private final Spatial fighterSpatial;
    float x;
    float y;

    public SpaceshipLocationUpdate(final float x, final float y, Spatial fighterSpatial) {
        this.x = x;
        this.y = y;
        this.fighterSpatial = fighterSpatial;
    }

    public void moveLeft() {
        fighterSpatial.setLocalTranslation(fighterSpatial.getLocalTranslation().x - MOVE_SPEED, fighterSpatial.getLocalTranslation().y, SPACE_SHIP_IS_NOT_FLYING);
    }

    public void moveRight() {
        fighterSpatial.setLocalTranslation(fighterSpatial.getLocalTranslation().x + MOVE_SPEED, fighterSpatial.getLocalTranslation().y, SPACE_SHIP_IS_NOT_FLYING);
    }

    public void moveUp() {
        fighterSpatial.setLocalTranslation(fighterSpatial.getLocalTranslation().x, fighterSpatial.getLocalTranslation().y + MOVE_SPEED, SPACE_SHIP_IS_NOT_FLYING);
    }

    public void moveDown() {
        fighterSpatial.setLocalTranslation(fighterSpatial.getLocalTranslation().x, fighterSpatial.getLocalTranslation().y - MOVE_SPEED, SPACE_SHIP_IS_NOT_FLYING);
    }

    @Override
    public Void call() throws Exception {
        fighterSpatial.setLocalTranslation(x, y - CORRECT_LEAP_MOTION_HAND_DISTANCE, fighterSpatial.getLocalTranslation().z);
        return null;
    }

}
