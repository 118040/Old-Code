package bullet.game;

public class Bullet {
    public BulletType type;
    public int damage;

    public Bullet(BulletType type) {
        this.type = type;
        this.damage = 1;
    }

    public Bullet(BulletType type, int damage) {
        this.type = type;
        this.damage = damage;
    }

    @Override
    public String toString() {
        return "Bullet{" +
                "type=" + type +
                ", damage=" + damage +
                '}';
    }

    public enum BulletType {
        LIVE, BLANK
    }
}
