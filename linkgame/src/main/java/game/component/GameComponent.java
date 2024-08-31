package game.component;

import game.Main;
import game.gui.Gui;
import game.gui.Menu;

import javax.swing.*;
import java.awt.*;

public class GameComponent extends JComponent {

    public GameComponent() {
        Main.GAME.window.add(this);
    }

    public static class RootComponent extends GameComponent {
        public final Menu menu = new Menu();
        public final Gui gui = new Gui();

        @Override
        public void paint(Graphics g) {
            switch (Main.GAME.state) {
                case MENU -> menu.paint(g);
                case RUNNING -> gui.paint(g);
            }
        }
    }
}
