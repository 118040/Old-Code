package solar;

import solar.engine.Engine;
import solar.engine.GameObject;
import solar.game.Asteroid;
import solar.game.Player;
import solar.game.StructureFactory;
import solar.engine.Vector2D;

public class Main {
    public static void main(String[] args) {
        Engine.start();
        Player.PLAYER.structure = StructureFactory.createAster(Asteroid.class);
        for (int i = 0; i < 40; i++) {
            GameObject object = new GameObject();
            object.addComponent(Asteroid.class);
            object.transform.position = new Vector2D((Engine.RANDOM.nextFloat() - 0.5f) * 2, (Engine.RANDOM.nextFloat() - 0.5f) * 2);
        }
    }
}
