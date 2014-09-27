package septemberjam.control;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import septemberjam.GameApplication;

/**
 * @author andreas
 */
public class CreateRockControl extends AbstractControl {

    float timeElapsed = 0.0f;
    float createInterval = 2.0f;

    GameApplication gameApplication;

    public CreateRockControl(GameApplication gameApplication, float createInterval) {
        this.gameApplication = gameApplication;
        this.createInterval = createInterval;
    }

    @Override
    protected void controlUpdate(float tpf) {
        timeElapsed += tpf;
        if (timeElapsed > createInterval) {
            createRock();
            timeElapsed = 0;
        }
    }

    private void createRock() {
        gameApplication.createRock(new Vector3f(-3, 1, -30), 0.1f, "rock_04", 100f);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
