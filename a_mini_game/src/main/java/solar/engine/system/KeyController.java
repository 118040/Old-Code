package solar.engine.system;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class KeyController {
    private KeyController() {
    }

    private static final Map<Integer, Boolean> KEY_STATES = new HashMap<>();

    public static final KeyListener KEY_LISTENER = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (KEY_STATES.containsKey(keyCode)) {
                KEY_STATES.replace(keyCode, true);
            } else {
                KEY_STATES.put(keyCode, true);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            KEY_STATES.replace(e.getKeyCode(), false);
        }
    };

    public static boolean getKeyStates(int keyCode) {
        if (KEY_STATES.containsKey(keyCode)) {
            return KEY_STATES.get(keyCode);
        } else {
            KEY_STATES.put(keyCode, false);
            return false;
        }
    }
}
