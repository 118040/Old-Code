package solar.engine.component;

import solar.engine.Engine;
import solar.engine.GameObject;
import solar.engine.system.GameWindow;
import solar.engine.Vector2D;

import java.awt.*;

public class Camera extends Component {
    public static float scale = 16;
    public final Thread THREAD = new Thread(() -> {
        GameWindow.WINDOW.setVisible(true);
        while (true) {
            if (!GameWindow.WINDOW.isVisible()) {
                break;
            }
        }
        Engine.exit();
    });

    public Camera(GameObject object) {
        super(object);
    }

    public static Graphics getDefaultGraphics() {
        return GameWindow.WINDOW.imageBuffer.getGraphics().create();
    }

    public static Graphics getAdaptedGraphics() {
        Graphics2D g = (Graphics2D) getDefaultGraphics().create();
        Vector2D position = toPositionOnScreen(Engine.CAMERA.object.transform.position.cloneVector());
        g.translate(512 - position.x, 240 - position.y);
        g.rotate(Math.toRadians(-90));
        return g;
    }

    public void paint() {
        GameWindow.WINDOW.repaint();
    }

    public static float toDistanceOnScreen(float d) {
        return d * Camera.scale * Camera.scale;
    }

    public static Vector2D toPositionOnScreen(Vector2D position) {
        return new Vector2D(toDistanceOnScreen(position.x), toDistanceOnScreen(position.y));
    }
}
