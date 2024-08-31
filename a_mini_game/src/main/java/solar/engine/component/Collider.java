package solar.engine.component;

import solar.engine.GameObject;
import solar.engine.Vector2D;

import java.util.*;
import java.util.function.Consumer;

public class Collider extends MonoBehavior {
    private static final List<Collider> COLLIDERS = new ArrayList<>();
    public final Map<Collider, Boolean> tasks = new HashMap<>();
    public Mover mover;
    public MassPoint massPoint;
    public Circle circle;
    public Consumer<GameObject> consumer;

    public Collider(GameObject object) {
        super(object);
        COLLIDERS.add(this);
    }

    @Override
    public void start() {
        mover = (Mover) object.getOrAddComponent(Mover.class);
        massPoint = (MassPoint) object.getOrAddComponent(MassPoint.class);
        circle = (Circle) object.getOrAddComponent(Circle.class);
    }

    @Override
    public void update() {
        for (int i = 0; i < COLLIDERS.size(); i++) {
            Collider collider = COLLIDERS.get(i);
            if (tasks.containsKey(collider) || collider.equals(this) || !collider.enabled) continue;
            if (isCollidedWith(collider)) {
                tasks.put(collider, true);
                collider.tasks.put(this, true);
            }
        }
        Iterator<Collider> iterator = tasks.keySet().iterator();
        while (iterator.hasNext()) {
            Collider collider = iterator.next();
            if (tasks.get(collider)) {
                float mass1 = massPoint.mass;
                float mass2 = collider.massPoint.mass;
                Vector2D speed1 = mover.speed.cloneVector();
                mover.speed.x -= speed1.x * (mass1 - mass2) / (mass1 + mass2);
                mover.speed.y -= speed1.y * (mass1 - mass2) / (mass1 + mass2);
                collider.mover.speed.x += speed1.x * 2 * mass1 / (mass1 + mass2);
                collider.mover.speed.y += speed1.y * 2 * mass1 / (mass1 + mass2);
                if (consumer != null && enabled) consumer.accept(collider.object);
                if (collider.consumer != null && collider.enabled) collider.consumer.accept(object);
                tasks.replace(collider, false);
                collider.tasks.replace(this, false);
            } else if (!isCollidedWith(collider)) {
                iterator.remove();
                collider.tasks.remove(this);
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        COLLIDERS.remove(this);
    }

    private boolean isCollidedWith(Collider collider) {
        Vector2D position1 = object.transform.position.cloneVector();
        Vector2D position2 = collider.object.transform.position.cloneVector();
        float distance = position1.subtract(position2).magnitude();
        float radius1 = circle.radius;
        float radius2 = collider.circle.radius;
        return radius1 + radius2 > distance;
    }
}
