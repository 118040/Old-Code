package game.component;

import game.Util;

import java.awt.*;
import java.io.File;

public class GameFont {
    public static final String FONT = "Unifont";

    static {
        try {
            Font gnuUnifont = Font.createFont(Font.TRUETYPE_FONT, new File(
                    Util.RESOURCES_ROOT_PATH + "font/gnu_unifont.ttf"));
            GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            gEnv.registerFont(gnuUnifont);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
