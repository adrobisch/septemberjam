package septemberjam.control;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 * @author andreas
 */
public class CreateRockControl extends AbstractControl {

    float timeElapsed = 0.0f;

    @Override
    protected void controlUpdate(float tpf) {
        timeElapsed += tpf;
        if (timeElapsed > 1000) {
            System.out.println("create rock");
            timeElapsed = 0;
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
