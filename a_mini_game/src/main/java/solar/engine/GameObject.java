package solar.engine;

import solar.engine.component.Component;
import solar.engine.component.Transform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class GameObject {
    private static final List<GameObject> OBJECTS = Collections.synchronizedList(new ArrayList<>());
    public final Transform transform;
    private final List<Component> components = Collections.synchronizedList(new ArrayList<>());

    public GameObject() {
        OBJECTS.add(this);
        transform = (Transform) addComponent(Transform.class);
    }

    public static List<GameObject> getObjects() {
        return new ArrayList<>(OBJECTS);
    }

    public Component addComponent(Class<? extends Component> componentClass) {
        Component component = null;
        try {
            component = componentClass.getConstructor(GameObject.class).newInstance(this);
        } catch (Exception e) {
            e.getCause().printStackTrace();
        }
        components.add(component);
        return component;
    }

    public Component getComponent(Class<? extends Component> componentClass) {
        for (Component component : components) {
            if (componentClass.isInstance(component)) {
                return component;
            }
        }
        return null;
    }

    public Component getOrAddComponent(Class<? extends Component> componentClass) {
        Component component = getComponent(componentClass);
        if (component != null) {
            return component;
        } else {
            return addComponent(componentClass);
        }
    }

    public void destroy() {
        components.forEach(Component::destroy);
    }
}
