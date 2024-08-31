package game.gui.display;

import game.component.GameComponent;
import game.run.ChessPiece;
import game.run.Solution;

import java.awt.*;

public class SolutionDisplay extends GameComponent {
    private static final long DURATION = 500;
    private final long endTime;
    private final Solution solution;

    public SolutionDisplay(Solution solution) {
        this.solution = solution;
        this.endTime = System.currentTimeMillis() + DURATION;
    }

    @Override
    public void paint(Graphics g) {
        if (endTime < System.currentTimeMillis()) {
            return;
        }
        g.setColor(Color.DARK_GRAY);
        ChessPiece chessPiece1 = solution.getChessPiece1(), chessPiece2 = solution.getChessPiece2();
        Rectangle abstractChessPiece1 = null, abstractChessPiece2 = null;
        chessPiece1.paintIfSelected(g);
        chessPiece2.paintIfSelected(g);
        switch (solution.getType()) {
            case X -> {
                abstractChessPiece1 = ChessPiece.getRect(solution.getLength(), chessPiece1.getLogicY());
                abstractChessPiece2 = ChessPiece.getRect(solution.getLength(), chessPiece2.getLogicY());
            }
            case Y -> {
                abstractChessPiece1 = ChessPiece.getRect(chessPiece1.getLogicX(), solution.getLength());
                abstractChessPiece2 = ChessPiece.getRect(chessPiece2.getLogicX(), solution.getLength());
            }
        }
        g.drawLine((int) chessPiece1.getBounds().getCenterX(), (int) chessPiece1.getBounds().getCenterY(),
                (int) abstractChessPiece1.getBounds().getCenterX(), (int) abstractChessPiece1.getBounds().getCenterY());
        g.drawLine((int) abstractChessPiece1.getBounds().getCenterX(), (int) abstractChessPiece1.getBounds().getCenterY(),
                (int) abstractChessPiece2.getBounds().getCenterX(), (int) abstractChessPiece2.getBounds().getCenterY());
        g.drawLine((int) chessPiece2.getBounds().getCenterX(), (int) chessPiece2.getBounds().getCenterY(),
                (int) abstractChessPiece2.getBounds().getCenterX(), (int) abstractChessPiece2.getBounds().getCenterY());

    }
}
