package solar.game;

import solar.engine.GameObject;
import solar.engine.component.*;

public class Aster extends MonoBehavior {
    public Aster(GameObject object) {
        super(object);
    }

    public Mover mover;
    public Circle circle;
    public Gravity gravity;
    public CircleShader shader;
    public Collider collider;
    public MassPoint massPoint;

    public Structure structure;
    public int mass = 1;

    @Override
    public void awake() {
        mover = (Mover) object.getOrAddComponent(Mover.class);
        circle = (Circle) object.getOrAddComponent(Circle.class);
        gravity = (Gravity) object.getOrAddComponent(Gravity.class);
        shader = (CircleShader) object.getOrAddComponent(CircleShader.class);
        collider = (Collider) object.getOrAddComponent(Collider.class);
        massPoint = (MassPoint) object.getOrAddComponent(MassPoint.class);
    }
}
