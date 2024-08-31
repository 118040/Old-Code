package solar.game;

import solar.engine.Engine;
import solar.engine.GameObject;
import solar.engine.component.MonoBehavior;
import solar.engine.system.KeyController;
import solar.engine.Vector2D;

import java.awt.event.KeyEvent;

public class Player extends MonoBehavior {
    public static final Player PLAYER = (Player) new GameObject().addComponent(Player.class);

    public static final float ACCELERATION = 0.1f;
    public Structure structure;

    public Player(GameObject object) {
        super(object);
    }

    @Override
    public void awake() {
        object.getOrAddComponent(PlayerDisplay.class);
    }

    @Override
    public void update() {
        updateMovementSpeed();
        resetCameraParameters();
    }

    private void updateMovementSpeed() {
        if (structure == null) return;
        float f = ACCELERATION * Engine.UPDATED_DELTA_TIME;
        boolean w = KeyController.getKeyStates(KeyEvent.VK_W), a = KeyController.getKeyStates(KeyEvent.VK_A),
                s = KeyController.getKeyStates(KeyEvent.VK_S), d = KeyController.getKeyStates(KeyEvent.VK_D);
        Vector2D speed = structure.mover.speed;
        float x = a ^ d ? f * (d ? 1 : -1) : 0;
        float y = w ^ s ? f * (s ? 1 : -1) : 0;
        speed.x += (speed.x * x >= 0 ? 1 : 2) * x;
        speed.y += (speed.y * y >= 0 ? 1 : 2) * y;
    }

    private void resetCameraParameters() {
        if (structure == null) return;
        Vector2D speed = structure.mover.speed;
        Vector2D position = structure.object.transform.position;
        Engine.CAMERA.object.transform.position = new Vector2D(position.x + speed.x / 2, position.y + speed.y / 2);
    }
}
