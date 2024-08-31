package game;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 576;
    public static final int SCALE = 2;
    private Image imageBuffer;

    public Window() {
        setPreferredSize(new Dimension(1024, 576));
        setResizable(false);
        setTitle("Link Game");
        setLayout(null);
        pack();
    }

    @Override
    public void paint(Graphics g) {
        try {
            Main.GAME.rootComponent.paint(g);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void update(Graphics g) {
        if (imageBuffer == null) {
            imageBuffer = createImage(getWidth(), getHeight());
        }
        Graphics graphics = imageBuffer.getGraphics();
        graphics.setColor(new Color(224, 224, 224));
        graphics.fillRect(0, 0, getWidth(), getHeight());
        paint(graphics);
        graphics.dispose();
        g.drawImage(imageBuffer, 0, 28, null);
    }

    @Override
    public void repaint() {
        update(getGraphics());
    }
}
