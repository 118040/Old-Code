package solar.engine.component;

import solar.engine.GameObject;

public class MassPoint extends Component {
    public MassPoint(GameObject object) {
        super(object);
    }

    public float mass = 0.1f;
}
