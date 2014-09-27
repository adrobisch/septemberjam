package septemberjam.baem;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.export.Savable;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import septemberjam.GameApplication;

/**
 *
 * @author nikku
 */
public class WeaponControl extends AbstractControl implements Savable, Cloneable{

    private static final float AUTO_FIRE_COOLDOWN = 0.5f;
    public static final float bulletSize = 0.10f;

    private boolean fire = true;

    private float bulletTimer = 0f;
    
    GameApplication application;
    
    public WeaponControl(GameApplication application) {
       this.application = application;
    }

    public void setFire(boolean fire) {
        this.fire = fire;
    }
    
    @Override
    protected void controlUpdate(float t) {
        
        if (fire) {
            bulletTimer += t;

            if (bulletTimer > AUTO_FIRE_COOLDOWN) {
                fireBullet();
                bulletTimer = 0;
            }
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) { }

    private void fireBullet() {
        Vector3f bbox = new Vector3f(bulletSize, bulletSize, bulletSize);
        
        // Setup Bullet
        Geometry bullet = new Geometry("Box", new Sphere(20,20, bulletSize));
        Material mat_bullet = new Material(application.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat_bullet.setColor("Color", ColorRGBA.Red);
        bullet.setMaterial(mat_bullet);
        
        final Spatial fighter = application.getFighter();
        
        final RigidBodyControl rigidBodyControl = new RigidBodyControl(new BoxCollisionShape(bbox));
        
        bullet.addControl(rigidBodyControl);
        
        rigidBodyControl.setPhysicsLocation(fighter.getLocalTranslation().add(0,0,-3f));
        rigidBodyControl.applyCentralForce(new Vector3f(fighter.getLocalTranslation().x, fighter.getLocalTranslation().y, -400f));

        application.getPhysicsSpace().add(bullet);
        
        bullet.setUserData("type", "bullet");
        
        application.getRootNode().attachChild(bullet);
        bulletTimer = 0f;
    }
}
