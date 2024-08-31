package solar.engine.component;

import solar.engine.Engine;
import solar.engine.GameObject;
import solar.engine.Vector2D;

public class Mover extends MonoBehavior {
    public Vector2D speed = new Vector2D();
    public static final float RESISTANCE = 0.01f;

    public Mover(GameObject object) {
        super(object);
    }

    @Override
    public void update() {
        object.transform.position.x += speed.x * Engine.UPDATED_DELTA_TIME;
        object.transform.position.y += speed.y * Engine.UPDATED_DELTA_TIME;
        speed.x *= 1 - RESISTANCE * Engine.UPDATED_DELTA_TIME;
        speed.y *= 1 - RESISTANCE * Engine.UPDATED_DELTA_TIME;
    }
}
