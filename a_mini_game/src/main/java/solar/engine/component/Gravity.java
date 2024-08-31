package solar.engine.component;

import solar.engine.Engine;
import solar.engine.GameObject;
import solar.engine.Vector2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Gravity extends MonoBehavior {
    public static final List<Gravity> MASS_POINTS = Collections.synchronizedList(new ArrayList<>());
    public Mover mover;
    public MassPoint massPoint;
    public Circle circle;
    public static final float g = 0.00000667430f;

    public Gravity(GameObject object) {
        super(object);
        MASS_POINTS.add(this);
    }

    @Override
    public void start() {
        mover = (Mover) object.getOrAddComponent(Mover.class);
        massPoint = (MassPoint) object.getOrAddComponent(MassPoint.class);
        circle = (Circle) object.getOrAddComponent(Circle.class);
    }

    @Override
    public void update() {
        float mass1 = massPoint.mass;
        float radius1 = circle.radius;
        Vector2D position1 = object.transform.position.cloneVector();
        List<Gravity> list = new ArrayList<>(MASS_POINTS);
        for (Gravity gravity : list) {
            if (gravity.equals(this) || !gravity.enabled) continue;
            float mass2 = gravity.massPoint.mass;
            Vector2D position2 = gravity.object.transform.position.cloneVector();
            float radius2 = gravity.circle.radius;
            Vector2D subtract = position2.subtract(position1);
            float distance = subtract.magnitude();
            if (radius1 + radius2 > distance) continue;
            if (distance == 0) continue;
            float force = mass1 * mass2 / distance / distance * g;
            float theta = (float) Math.atan2(subtract.x, subtract.y);
            float acceleration1 = (float) Math.cos(theta) * force / mass1;
            float acceleration2 = (float) Math.sin(theta) * force / mass2;
            mover.speed.x += acceleration1 * Engine.UPDATED_DELTA_TIME;
            mover.speed.y += acceleration2 * Engine.UPDATED_DELTA_TIME;
        }
    }
}
