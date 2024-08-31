package solar.engine.component;

import solar.engine.GameObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class MonoBehavior extends Component {
    private static final List<MonoBehavior> BEHAVIORS = Collections.synchronizedList(new ArrayList<>());
    public boolean enabled = false;

    public MonoBehavior(GameObject object) {
        super(object);
        BEHAVIORS.add(this);
        init();
    }

    public static List<MonoBehavior> getBehaviors() {
        return new ArrayList<>(BEHAVIORS);
    }

    public static void updateAll() {
        List<MonoBehavior> list = getBehaviors();
        for (MonoBehavior behavior : list) {
            if (behavior.enabled) {
                behavior.update();
            }
        }
    }

    public static void fixedUpdateAll() {
        List<MonoBehavior> list = getBehaviors();
        for (MonoBehavior behavior : list) {
            if (behavior.enabled) {
                behavior.fixedUpdate();
            }
        }
    }

    public void onValidate() {
    }

    public void awake() {
    }

    public void onEnable() {
    }

    public void start() {
    }

    public void fixedUpdate() {
    }

    public void update() {
    }

    public void onDisable() {
    }

    public void onDestroy() {
    }

    public final void enable() {
        try {
            onValidate();
            onEnable();
            enabled = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void disable() {
        try {
            onValidate();
            onDisable();
            enabled = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() {
        try {
            awake();
            onEnable();
            start();
            enabled = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        try {
            disable();
            onValidate();
            onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
