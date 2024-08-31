package game.gui.display;

import game.Util;
import game.component.GameComponent;
import game.component.GameFont;
import game.run.MainThread;
import game.run.Timer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class TimeDisplay extends GameComponent {
    private static final int SCALE = 3;
    private static BufferedImage image;

    static {
        try {
            image = ImageIO.read(new File(Util.RESOURCES_ROOT_PATH + "image/gui/bar.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setFont(new Font(GameFont.FONT, Font.PLAIN, 24));
        Util.drawShadowString("time", 175, 48, Color.darkGray, Color.WHITE, g);
        g.drawImage(image.getSubimage(0, 0, 182, 5),
                240, 32, 182 * SCALE, 5 * SCALE, null);
        double timeProportion = Math.min((double) Timer.getInstance().getRemainder() / MainThread.GAME_TIME, 1);
        g.drawImage(image.getSubimage(0, 5, (int) (182 * timeProportion), 5),
                240, 32, (int) (timeProportion * 182 * SCALE), 5 * SCALE, null);
    }
}
