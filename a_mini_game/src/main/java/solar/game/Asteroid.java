package solar.game;

import solar.engine.Engine;
import solar.engine.GameObject;
import solar.engine.component.Component;
import solar.engine.component.MonoBehavior;
import solar.engine.Vector2D;

import java.awt.*;

public class Asteroid extends MonoBehavior implements AsterComponent {
    public Aster aster;

    public Asteroid(GameObject object) {
        super(object);
    }

    @Override
    public void start() {
        aster = (Aster) object.getOrAddComponent(Aster.class);
        aster.shader.color = Color.CYAN;
        aster.mass = Engine.RANDOM.nextInt(2) + 1;
        aster.collider.consumer = object -> {
            Component component = object.getComponent(Asteroid.class);
            if (component != null) {
                Asteroid asteroid = (Asteroid) component;
                Vector2D speed1 = aster.mover.speed.cloneVector();
                Vector2D speed2 = asteroid.aster.mover.speed.cloneVector();
                float vx = speed2.x - speed1.x;
                float vy = speed2.y - speed1.y;
                float v = (float) Math.sqrt(vx * vx + vy * vy);
                if (v > 0.2f) {
                    if (Player.PLAYER.structure.parentAsters.contains(this)) {
                        aster.mass += asteroid.aster.mass;
                        asteroid.object.destroy();
                    } else {
                        asteroid.aster.mass += aster.mass;
                        object.destroy();
                    }
                }
            }
        };
    }

    @Override
    public void update() {
        aster.massPoint.mass = 0.1f + aster.mass / 2000f;
        aster.circle.radius = 0.015f + aster.mass * 0.0005f;
    }

    @Override
    public Aster getAster() {
        return aster;
    }
}
