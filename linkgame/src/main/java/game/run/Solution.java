package game.run;

public class Solution {
    private final ChessPiece chessPiece1;
    private final ChessPiece chessPiece2;
    private SolutionType type;
    private int length;

    private Solution(ChessPiece chessPiece1, ChessPiece chessPiece2) {
        this.chessPiece1 = chessPiece1;
        this.chessPiece2 = chessPiece2;
    }

    public static Solution getSolution(ChessPiece chessPiece1, ChessPiece chessPiece2) {
        Solution solution = new Solution(chessPiece1, chessPiece2);
        if (solution.chessPiece1 == null
                || solution.chessPiece2 == null
                || solution.chessPiece1.equals(solution.chessPiece2)
                || solution.chessPiece1.getType() != solution.chessPiece2.getType()) {
            return null;
        }
        int[][] map = new int[Checkerboard.CHECKERBOARD_WIDTH + 2][Checkerboard.CHECKERBOARD_HEIGHT + 2];
        for (int i = 0; i < Checkerboard.CHESS_PIECES.size(); i++) {
            ChessPiece chessPiece = Checkerboard.CHESS_PIECES.get(i);
            map[chessPiece.getLogicX() + 1][chessPiece.getLogicY() + 1] = -1;
        }
        int x1 = chessPiece1.getLogicX() + 1, y1 = chessPiece1.getLogicY() + 1,
                x2 = chessPiece2.getLogicX() + 1, y2 = chessPiece2.getLogicY() + 1;
        map[x1][y1] = 1;
        map[x2][y2] = 1;
        for (int x = 0; x <= Checkerboard.CHECKERBOARD_WIDTH + 1; x++) {
            boolean flag = true;
            for (int i = 0; i < Math.abs(y1 - y2); i++) {
                if (map[x][Math.min(y1, y2) + i] == -1) {
                    flag = false;
                    break;
                }
            }
            for (int i = 0; i < Math.abs(x1 - x) && flag; i++) {
                if (map[Math.min(x1, x) + i][y1] == -1) {
                    flag = false;
                    break;
                }
            }
            for (int i = 0; i < Math.abs(x2 - x) && flag; i++) {
                if (map[Math.min(x2, x) + i][y2] == -1) {
                    flag = false;
                    break;
                }
            }
            if (!flag) {
                continue;
            }
            solution.type = SolutionType.X;
            solution.length = x - 1;
            return solution;
        }
        for (int y = 0; y <= Checkerboard.CHECKERBOARD_HEIGHT + 1; y++) {
            boolean flag = true;
            for (int i = 0; i < Math.abs(x1 - x2); i++) {
                if (map[Math.min(x1, x2) + i][y] == -1) {
                    flag = false;
                    break;
                }
            }
            for (int i = 0; i < Math.abs(y1 - y) && flag; i++) {
                if (map[x1][Math.min(y1, y) + i] == -1) {
                    flag = false;
                    break;
                }
            }
            for (int i = 0; i < Math.abs(y2 - y) && flag; i++) {
                if (map[x2][Math.min(y2, y) + i] == -1) {
                    flag = false;
                    break;
                }
            }
            if (!flag) {
                continue;
            }
            solution.type = SolutionType.Y;
            solution.length = y - 1;
            return solution;
        }
        return null;
    }

    public ChessPiece getChessPiece1() {
        return chessPiece1;
    }

    public ChessPiece getChessPiece2() {
        return chessPiece2;
    }

    public SolutionType getType() {
        return type;
    }

    public int getLength() {
        return length;
    }

    public enum SolutionType {
        X, Y
    }
}
