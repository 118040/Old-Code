package solar.engine;

import solar.engine.component.Camera;
import solar.engine.component.MonoBehavior;

import java.util.Random;

public class Engine {
    public static final Camera CAMERA = (Camera) new GameObject().addComponent(Camera.class);
    public static final Random RANDOM = new Random(System.currentTimeMillis());
    public static final float UPDATED_DELTA_TIME = 1f / 30;
    public static final float FIXED_DELTA_TIME = 1f / 50;
    public static float time;
    public static float timeScale = 1;
    public static final Thread UPDATER = new Thread(() -> {
        float next = time + UPDATED_DELTA_TIME;
        while (!Thread.currentThread().isInterrupted()) {
            while (true) {
                if (time > next) {
                    break;
                }
            }
            update();
            next = time + UPDATED_DELTA_TIME;
            CAMERA.paint();
        }
    });
    public static final Thread FIXED_UPDATER = new Thread(() -> {
        float next = time + FIXED_DELTA_TIME;
        while (!Thread.currentThread().isInterrupted()) {
            while (true) {
                if (time > next) {
                    break;
                }
            }
            fixedUpdate();
            next = time + FIXED_DELTA_TIME;
        }
    });
    public static final Thread MAIN_THREAD = new Thread(() -> {
        UPDATER.start();
        FIXED_UPDATER.start();
        CAMERA.THREAD.start();
        long last = System.nanoTime();
        while (!Thread.currentThread().isInterrupted()) {
            while (true) {
                if (System.nanoTime() > last + 5000) {
                    break;
                }
            }
            time += (System.nanoTime() - last) / 1000000000f * timeScale;
            last = System.nanoTime();
        }
    });
    private Engine() {
    }

    public static void start() {
        MAIN_THREAD.start();
    }

    public static void exit() {
        MAIN_THREAD.interrupt();
        System.exit(0);
    }

    private static void update() {
        try {
            MonoBehavior.updateAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void fixedUpdate() {
        System.out.print("");
        try {
            MonoBehavior.fixedUpdateAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
