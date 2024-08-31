package solar.game;

import solar.engine.GameObject;
import solar.engine.component.MonoBehavior;
import solar.engine.component.Mover;

import java.util.ArrayList;
import java.util.List;

public class Structure extends MonoBehavior {
    public Structure(GameObject object) {
        super(object);
    }
    public Mover mover;

    @Override
    public void awake() {
        mover = (Mover) object.getOrAddComponent(Mover.class);
    }

    public final List<AsterComponent> parentAsters = new ArrayList<>();
    public final List<Structure> childStructures = new ArrayList<>();

    @Override
    public void update() {
        parentAsters.forEach(aster -> aster.getAster().mover.speed = mover.speed.cloneVector());
        childStructures.forEach(structure -> structure.mover.speed = mover.speed.cloneVector());
    }

    public float getRadius() {
        return parentAsters.get(0).getAster().circle.radius;
    }
}
