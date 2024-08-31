package solar.engine.component;

import solar.engine.GameObject;

import java.awt.*;

public class CircleShader extends Shader {
    public Circle circle;
    public Color color = Color.white;
    public CircleShader(GameObject object) {
        super(object);
        circle = (Circle) object.getOrAddComponent(Circle.class);
    }

    @Override
    public void paint() {
        Graphics2D g2d = (Graphics2D) Shader.getGraphics(object.transform);
        g2d.setColor(color);
        g2d.scale(1 / Camera.scale, 1 / Camera.scale);
        float radius = Camera.toDistanceOnScreen(circle.radius);
        g2d.fillOval((int) (-radius), (int) (-radius), (int) (radius * 2), (int) (radius * 2));
    }
}
