package game;

import java.awt.*;

public class Util {
    public static final String RESOURCES_ROOT_PATH = "src/main/resources/asset/link_game/";

    private Util() {
    }

    public static Rectangle getRectangle(int x, int y, int w, int h) {
        return new Rectangle(x * Window.SCALE, y * Window.SCALE, w * Window.SCALE, h * Window.SCALE);
    }

    public static void drawShadowString(String str, int x, int y, Graphics g) {
        drawShadowString(str, x, y, g.getColor(), Color.BLACK, g);
    }

    public static void drawShadowString(String str, int x, int y, Color strColor, Color shadowColor, Graphics g) {
        g.setColor(shadowColor);
        g.drawString(str, x + 2, y + 2);
        g.setColor(strColor);
        g.drawString(str, x, y);
    }
}
