package game.gui;

import game.Game;
import game.Main;
import game.Util;
import game.Window;
import game.component.GameButton;
import game.component.GameComponent;
import game.run.MainThread;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Menu extends GameComponent {
    private static BufferedImage background;

    static {
        try {
            background = ImageIO.read(new File(Util.RESOURCES_ROOT_PATH + "image/gui/menu_background.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final GameButton startButton = new GameButton(
            (Window.WIDTH - 400) / 2, Window.HEIGHT / 2 + 32, 400, "Start Game") {
        @Override
        public void pressDown() {
            if (Main.GAME.state.equals(Game.GameState.MENU)) {
                Main.GAME.state = Game.GameState.RUNNING;
                Game.LINK_GAME_THREAD = new MainThread();
                Game.LINK_GAME_THREAD.resetting();
                Game.LINK_GAME_THREAD.start();
                setVisible(false);
            }
        }
    };

    public Menu() {
        super();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, Window.WIDTH, Window.HEIGHT, null);
        startButton.paint(g);
    }

    public void resetting() {
        startButton.setVisible(true);
    }
}
