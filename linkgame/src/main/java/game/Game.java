package game;

import game.component.GameComponent;
import game.run.MainThread;

public class Game extends Thread {
    public static MainThread LINK_GAME_THREAD = new MainThread();
    public Window window;
    public GameComponent.RootComponent rootComponent;
    public GameState state;

    @Override
    public void run() {
        init();
        window.setVisible(true);
        while (window.isVisible()) window.repaint();
        System.exit(0);
    }

    private void init() {
        window = new Window();
        rootComponent = new GameComponent.RootComponent();
        state = GameState.MENU;
    }

    public enum GameState {
        MENU, RUNNING, WAITING
    }
}
