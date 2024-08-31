package game.run;

import game.Game;
import game.Main;

public class MainThread extends Thread {
    public static final Timer TIMER = Timer.getInstance();
    public static final int GAME_TIME = 240 * 1000;
    public static int score = 0;

    @Override
    public void run() {
        TIMER.run(GAME_TIME, (o) -> gameOver());
        while (true) {
            if (isInterrupted()) {
                return;
            }
        }
    }

    public void gameOver() {
        interrupt();
        Main.GAME.state = Game.GameState.MENU;
        Main.GAME.rootComponent.menu.resetting();
    }

    public void resetting() {
        Checkerboard.resetting();
        score = 0;
    }
}
