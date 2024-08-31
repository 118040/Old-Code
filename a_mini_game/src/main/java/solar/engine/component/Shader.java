package solar.engine.component;

import solar.engine.GameObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Shader extends Component {
    private static final List<Shader> SHADERS = new ArrayList<>();

    public Shader(GameObject object) {
        super(object);
        SHADERS.add(this);
    }

    public static void paintAll() {
        SHADERS.forEach(Shader::paint);
    }

    public static Graphics getGraphics(Transform transform) {
        Graphics2D g2d = (Graphics2D) Camera.getAdaptedGraphics();
        g2d.scale(Camera.scale, Camera.scale);
        g2d.rotate(Math.toRadians(90));
        g2d.translate(transform.position.x * Camera.scale, transform.position.y * Camera.scale);
        return g2d;
    }

    public abstract void paint();

    @Override
    public void destroy() {
        SHADERS.remove(this);
    }
}
