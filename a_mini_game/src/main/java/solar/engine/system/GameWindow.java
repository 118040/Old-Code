package solar.engine.system;

import solar.engine.component.Shader;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 480;
    public static final GameWindow WINDOW = new GameWindow();
    public Image imageBuffer;

    public GameWindow() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Solar");
        setLayout(null);
        pack();
        addKeyListener(KeyController.KEY_LISTENER);
    }

    @Override
    public void update(Graphics g) {
        if (imageBuffer == null) {
            imageBuffer = createImage(WIDTH, HEIGHT);
        }
        Graphics graphics = imageBuffer.getGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);
        paint(graphics);
        graphics.dispose();
        g.drawImage(imageBuffer, 0, 28, WIDTH, HEIGHT, null);
    }

    @Override
    public void paint(Graphics g) {
        Shader.paintAll();
    }

    @Override
    public void repaint() {
        update(getGraphics());
    }
}
