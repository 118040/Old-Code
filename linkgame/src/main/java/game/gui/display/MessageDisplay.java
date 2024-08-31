package game.gui.display;

import game.Util;
import game.component.GameComponent;
import game.component.GameFont;

import java.awt.*;

public class MessageDisplay extends GameComponent {
    public static String message = "null";
    public static MessageDisplay instance = new MessageDisplay();

    private MessageDisplay() {
    }

    @Override
    public void paint(Graphics g) {
        g.setFont(new Font(GameFont.FONT, Font.PLAIN, 32));
        Util.drawShadowString(message, 400, 200, g);
    }
}
