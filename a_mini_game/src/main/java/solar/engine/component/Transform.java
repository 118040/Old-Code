package solar.engine.component;

import solar.engine.GameObject;
import solar.engine.Vector2D;

public final class Transform extends Component {
    public Vector2D position = new Vector2D();
    public Vector2D rotation = new Vector2D();
    public Vector2D scale = new Vector2D(1, 1);

    public Transform(GameObject object) {
        super(object);
    }

    @Override
    public void destroy() {
    }

    @Override
    public String toString() {
        return "Transform{" +
                "position=" + position +
                ", rotation=" + rotation +
                ", scale=" + scale +
                '}';
    }
}
