package bullet.game.event;

import com.alibaba.fastjson2.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventSource {
    private final List<EventListener> listeners = new ArrayList<>();

    public void addListener(EventListener listener) {
        listeners.add(listener);
    }

    public void removeListener(EventListener listener) {
        listeners.remove(listener);
    }

    public void trigger(JSONObject source) {
        trigger(new EventObject(source));
    }

    public void trigger(EventObject source) {
        for (EventListener listener : listeners) {
            listener.handle(source);
        }
    }
}
