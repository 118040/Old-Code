package solar.engine.component;

import solar.engine.GameObject;

public class Circle extends Component {
    public Circle(GameObject object) {
        super(object);
    }

    public float radius = 0.1f;
}
