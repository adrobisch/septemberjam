package septemberjam.input;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import septemberjam.SpaceShipActions;

public class KeyboardInput {

    public void setupKeyMapping(InputManager inputManager, final SpaceShipActions spaceShipActions) {
        
        String strafe_left_mapping = "STRAFE_LEFT";
        String strafe_right_mapping = "STRAFE_RIGHT";
        String strafe_up_mapping = "STRAFE_UP";
        String strafe_down_mapping = "STRAFE_DOWN";
        String shoot_mapping = "SHOOT";

        inputManager.addMapping(strafe_left_mapping, new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping(strafe_right_mapping, new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping(strafe_up_mapping, new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping(strafe_down_mapping, new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping(shoot_mapping, new KeyTrigger(KeyInput.KEY_SPACE));

        AnalogListener moveLeftListener = new AnalogListener() {
            @Override
            public void onAnalog(String name, float value, float tpf) {
                spaceShipActions.moveLeft();
            }
        };

        AnalogListener moveRightListener = new AnalogListener() {
            @Override
            public void onAnalog(String name, float value, float tpf) {
                spaceShipActions.moveRight();
            }
        };

        AnalogListener moveUpListener = new AnalogListener() {
            @Override
            public void onAnalog(String name, float value, float tpf) {
                spaceShipActions.moveUp();
            }
        };

        AnalogListener moveDownListener = new AnalogListener() {
            @Override
            public void onAnalog(String name, float value, float tpf) {
                spaceShipActions.moveDown();
            }
        };

        AnalogListener shootListener = new AnalogListener() {
            @Override
            public void onAnalog(String name, float value, float tpf) {
                spaceShipActions.shoot();
            }
        };

        inputManager.addListener(moveLeftListener, strafe_left_mapping);
        inputManager.addListener(moveRightListener, strafe_right_mapping);
        inputManager.addListener(moveUpListener, strafe_up_mapping);
        inputManager.addListener(moveDownListener, strafe_down_mapping);
        inputManager.addListener(shootListener, shoot_mapping);
    }
}
