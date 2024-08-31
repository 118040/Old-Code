package solar.game;

import solar.engine.GameObject;
import solar.engine.component.Camera;
import solar.engine.component.Shader;

import java.awt.*;

public class PlayerDisplay extends Shader {
    public PlayerDisplay(GameObject object) {
        super(object);
    }

    @Override
    public void paint() {
        Graphics g = Camera.getDefaultGraphics();
        g.setFont(new Font("Unifont", Font.PLAIN, 16));
        g.setColor(Color.WHITE);
        g.drawString("mass: " + Player.PLAYER.structure.parentAsters.get(0).getAster().mass, 16, 20);
    }
}
