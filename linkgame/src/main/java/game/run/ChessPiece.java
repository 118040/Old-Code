package game.run;

import game.Util;
import game.component.GameComponent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class ChessPiece extends GameComponent {
    public static final ArrayList<BufferedImage> chessPieceImages = new ArrayList<>();
    private static final int X = 140 + 100, Y = 80 + 20, SCALE = 2;
    public static int typeNumber;
    private static BufferedImage selectedImage;

    public ChessPiece(int logicX, int logicY, int type) {
        this.logicX = logicX;
        this.logicY = logicY;
        this.type = type;
        updateBounds();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ChessPiece.this.mouseClicked();
            }
        });
    }

    static {
        try {
            selectedImage = ImageIO.read(new File(Util.RESOURCES_ROOT_PATH + "image/chess_piece/selected.png"))
                    .getSubimage(0, 0, 18, 18);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            int orderNum = 0;
            while (true) {
                chessPieceImages.add(orderNum, ImageIO.read(new File(
                        Util.RESOURCES_ROOT_PATH + "image/chess_piece/chess_piece_" + orderNum + ".png")));
                orderNum++;
            }
        } catch (Exception ignored) {
        }
        typeNumber = chessPieceImages.size();
    }

    private final int type;
    private int logicX;
    private int logicY;

    public void eliminate() {
        setVisible(false);
    }

    public int getLogicX() {
        return logicX;
    }

    public void setLogicX(int logicX) {
        this.logicX = logicX;
        updateBounds();
    }

    public int getLogicY() {
        return logicY;
    }

    public void setLogicY(int logicY) {
        this.logicY = logicY;
        updateBounds();
    }

    public int getType() {
        return type;
    }

    @Override
    public void paint(Graphics g) {
        int x = getX(), y = getY();
        g.drawImage(chessPieceImages.get(getType()), x + SCALE, y + SCALE, 16 * SCALE, 16 * SCALE, null);
    }

    public static Rectangle getRect(int logicX, int logicY) {
        return new Rectangle(X + logicX * 18 * SCALE, Y + logicY * 18 * SCALE, 18 * SCALE, 18 * SCALE);
    }

    private void mouseClicked() {
        if (!Timer.getInstance().state.equals(Timer.TimerState.RUNNING)) {
            return;
        }
        if (Checkerboard.selectedChessPiece == null) {
            Checkerboard.selectedChessPiece = this;
        } else {
            Checkerboard.eliminate(Solution.getSolution(this, Checkerboard.selectedChessPiece));
        }
    }

    public void paintIfSelected(Graphics g) {
        int x = getX(), y = getY();
        g.drawImage(selectedImage, x, y, 18 * SCALE, 18 * SCALE, null);
        g.drawImage(chessPieceImages.get(getType()), x + SCALE, y + SCALE, 16 * SCALE, 16 * SCALE, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return logicX == that.logicX && logicY == that.logicY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(logicX, logicY);
    }

    private void updateBounds() {
        setBounds(X + logicX * 18 * SCALE, Y + logicY * 18 * SCALE, 18 * SCALE, 18 * SCALE);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "logicX=" + logicX +
                ", logicY=" + logicY +
                ", type=" + type +
                '}';
    }
}
