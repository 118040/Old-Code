package bullet.game;

import java.util.LinkedList;
import java.util.Random;

public class Gun {
    public static final int BLANK_BULLET_COUNT = 8;
    public static final int LIVE_BULLET_COUNT = 6;
    private final LinkedList<Bullet> mag = new LinkedList<>();

    public boolean isEmpty() {
        return mag.isEmpty();
    }

    public void clear() {
        mag.clear();
    }

    public void load(int live, int blank) {
        clear();
        Random random = new Random();
        while (live + blank > 0) {
            Bullet.BulletType next;
            if (live == 0) {
                next = Bullet.BulletType.BLANK;
            } else if (blank == 0) {
                next = Bullet.BulletType.LIVE;
            } else {
                int i = random.nextInt(live + blank);
                next = i < live ? Bullet.BulletType.LIVE : Bullet.BulletType.BLANK;
            }
            mag.add(new Bullet(next));
            if (next.equals(Bullet.BulletType.LIVE)) {
                live--;
            } else {
                blank--;
            }
        }
    }

    public Bullet getCurrentBullet() {
        return mag.peekFirst();
    }

    public Bullet getBullet(int index) {
        return mag.get(index);
    }

    public Bullet shoot() {
        return mag.pollFirst();
    }
}
