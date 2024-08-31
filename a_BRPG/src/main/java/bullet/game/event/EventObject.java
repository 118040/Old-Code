package bullet.game.event;

import com.alibaba.fastjson2.JSONObject;

import java.util.Objects;

public record EventObject(JSONObject source) {
    public EventObject(JSONObject source) {
        this.source = Objects.requireNonNullElseGet(source, JSONObject::new);
    }

    @Override
    public String toString() {
        return "EventObject{" +
                "source=" + source +
                '}';
    }
}
