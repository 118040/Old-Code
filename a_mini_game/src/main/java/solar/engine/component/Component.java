package solar.engine.component;

import solar.engine.GameObject;

public class Component {
    public final GameObject object;

    public Component(GameObject object) {
        this.object = object;
    }

    public void destroy() {
    }
}
