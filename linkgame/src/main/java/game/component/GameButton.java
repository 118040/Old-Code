package game.component;

import game.Util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public abstract class GameButton extends GameComponent {
    private static BufferedImage image = null;

    static {
        try {
            image = ImageIO.read(new File(Util.RESOURCES_ROOT_PATH + "image/gui/button.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ButtonState state;
    private String text;

    public GameButton(int x, int y, int length) {
        super();
        setBounds(x, y, Math.max(40, length), 40);
        state = ButtonState.DEFAULT;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pressDown();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                state = ButtonState.ENTERED;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                state = ButtonState.ENTERED;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                state = ButtonState.DEFAULT;
            }
        });
    }

    public GameButton(int x, int y, int length, String text) {
        this(x, y, length);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public abstract void pressDown();

    @Override
    public void paint(Graphics g) {
        BufferedImage subImage = null;
        switch (state) {
            case DEFAULT -> subImage = image.getSubimage(0, 0, 200, 20);
            case ENTERED -> subImage = image.getSubimage(0, 20, 200, 20);
        }
        g.drawImage(subImage.getSubimage(0, 0, 4, 20), getX(), getY(), 8, 40, null);
        for (int length = 8; length < getWidth() - 8; length += 384) {
            g.drawImage(subImage.getSubimage(4, 0, 192, 20),
                    getX() + length, getY(), getWidth() - 8 - length, 40, null);
        }
        g.drawImage(subImage.getSubimage(196, 0, 4, 20),
                getX() + getWidth() - 8, getY(), 8, 40, null);
        g.setFont(new Font(GameFont.FONT, Font.PLAIN, 16));
        Util.drawShadowString(text, getX() + 16, getY() + getHeight() - 12, Color.WHITE, Color.BLACK, g);
    }

    private enum ButtonState {
        DEFAULT, ENTERED
    }
}
