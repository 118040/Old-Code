package game.gui;

import game.Game;
import game.Main;
import game.Util;
import game.Window;
import game.component.GameButton;
import game.component.GameComponent;
import game.component.GameFont;
import game.gui.display.MessageDisplay;
import game.gui.display.TimeDisplay;
import game.run.Checkerboard;
import game.run.MainThread;
import game.run.Timer;

import java.awt.*;

public class Gui extends GameComponent {
    private final TimeDisplay timeDisplay = new TimeDisplay();
    private final Checkerboard checkerboardDisplay = Checkerboard.INSTANCE;
    private final GameComponent scoreDisplay = new GameComponent() {
        @Override
        public void paint(Graphics g) {
            g.setFont(new Font(GameFont.FONT, Font.PLAIN, 24));
            Util.drawShadowString("score: " + MainThread.score, 800, 48, Color.darkGray, Color.WHITE, g);
        }
    };
    private final GameButton suspendButton = new GameButton(15, Window.HEIGHT - 40 - 10 - 32, 80) {
        @Override
        public void pressDown() {
            if (Timer.getInstance().state.equals(Timer.TimerState.RUNNING)) {
                Timer.getInstance().suspend();
                setText("continue");
            } else if (Timer.getInstance().state.equals(Timer.TimerState.SUSPENDING)) {
                Timer.getInstance().resume();
                setText("suspend");
            }
        }
    };

    public Gui() {
        super();
        suspendButton.setText("suspend");
    }

    @Override
    public void paint(Graphics g) {
        if (Main.GAME.state.equals(Game.GameState.WAITING)) {
            MessageDisplay.instance.paint(g);
        } else {
            timeDisplay.paint(g);
            suspendButton.paint(g);
            checkerboardDisplay.paint(g);
            scoreDisplay.paint(g);
            if (Checkerboard.solutionDisplay != null) {
                Checkerboard.solutionDisplay.paint(g);
            }
        }
    }
}
