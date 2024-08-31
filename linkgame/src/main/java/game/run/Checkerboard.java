package game.run;

import game.Game;
import game.component.GameComponent;
import game.gui.display.SolutionDisplay;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Checkerboard extends GameComponent {
    public static final int CHECKERBOARD_WIDTH = 14;
    public static final int CHECKERBOARD_HEIGHT = 10;
    public static final Checkerboard INSTANCE = new Checkerboard();
    public static final ArrayList<ChessPiece> CHESS_PIECES = new ArrayList<>();
    public static ChessPiece selectedChessPiece;
    public static SolutionDisplay solutionDisplay;
    public static Solution referenceSolution;

    private Checkerboard() {
    }

    public static void resetting() {
        for (int i = 0; i < CHESS_PIECES.size(); i++) {
            CHESS_PIECES.get(i).eliminate();
        }
        CHESS_PIECES.clear();
        selectedChessPiece = null;
        int x = 0, y = 0;
        double type = 0;
        while (y < CHECKERBOARD_HEIGHT) {
            ChessPiece chessPiece = new ChessPiece(x, y, (int) type);
            CHESS_PIECES.add(chessPiece);
            type += 0.5;
            if (type >= ChessPiece.typeNumber) {
                type = 0;
            }
            x++;
            if (x >= CHECKERBOARD_WIDTH) {
                x = 0;
                y++;
            }
        }
        rearrange();
    }

    public static void rearrange() {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < CHESS_PIECES.size() * 3; i++) {
            ChessPiece chessPiece1 = CHESS_PIECES.get(Math.abs(random.nextInt() % CHESS_PIECES.size()));
            ChessPiece chessPiece2 = CHESS_PIECES.get(Math.abs(random.nextInt() % CHESS_PIECES.size()));
            int x = chessPiece1.getLogicX(), y = chessPiece1.getLogicY();
            chessPiece1.setLogicX(chessPiece2.getLogicX());
            chessPiece1.setLogicY(chessPiece2.getLogicY());
            chessPiece2.setLogicX(x);
            chessPiece2.setLogicY(y);
        }
    }

    public static void eliminate(Solution solution) {
        if (solution == null) {
            selectedChessPiece = null;
            return;
        }
        CHESS_PIECES.remove(solution.getChessPiece1());
        CHESS_PIECES.remove(solution.getChessPiece2());
        solution.getChessPiece1().eliminate();
        solution.getChessPiece2().eliminate();
        if (selectedChessPiece.equals(solution.getChessPiece1()) || selectedChessPiece.equals(solution.getChessPiece2())) {
            selectedChessPiece = null;
        }
        solutionDisplay = new SolutionDisplay(solution);
        Timer.getInstance().extend(1000);
        MainThread.score += 10;
        Solution reference = checkIsAbleToContinue();
        if (reference != null) {
            referenceSolution = reference;
        } else {
            rearrange();
        }
        if (CHESS_PIECES.size() == 0) {
            Game.LINK_GAME_THREAD.gameOver();
        }
    }

    @Override
    public void paint(Graphics g) {
        for (int i = 0; i < CHESS_PIECES.size(); i++) {
            CHESS_PIECES.get(i).paint(g);
        }
        if (selectedChessPiece != null) {
            selectedChessPiece.paintIfSelected(g);
        }
    }

    private static Solution checkIsAbleToContinue() {
        for (int i = 0; i < CHESS_PIECES.size(); i++) {
            for (int j = i + 1; j < CHESS_PIECES.size(); j++) {
                Solution solution = Solution.getSolution(CHESS_PIECES.get(i), CHESS_PIECES.get(j));
                if (solution != null) {
                    return solution;
                }
            }
        }
        return null;
    }
}
