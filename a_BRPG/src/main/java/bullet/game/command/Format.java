package bullet.game.command;

import com.alibaba.fastjson2.JSON;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Format {
    protected static final Map<Class<?>, Method> PARSERS = new HashMap<>();

    public static Object parse(String source, Class<?> type) throws InvocationTargetException, IllegalAccessException {
        if (PARSERS.containsKey(type)) {
            return PARSERS.get(type).invoke(null, source);
        } else {
            return JSON.parse(source);
        }
    }

    @Parser(type = byte.class)
    public static byte parseByte(String source) {
        return Byte.parseByte(source);
    }

    @Parser(type = short.class)
    public static short parseShort(String source) {
        return Short.parseShort(source);
    }

    @Parser(type = int.class)
    public static int parseInt(String source) {
        return Integer.parseInt(source);
    }

    @Parser(type = long.class)
    public static long parseLong(String source) {
        return Long.parseLong(source);
    }

    @Parser(type = boolean.class)
    public static boolean parseBoolean(String source) {
        return Boolean.parseBoolean(source);
    }

    @Parser(type = float.class)
    public static float parseFloat(String source) {
        return Float.parseFloat(source);
    }

    @Parser(type = double.class)
    public static double parseDouble(String source) {
        return Double.parseDouble(source);
    }

    @Parser(type = String.class)
    public static String parseString(String source) {
        return source;
    }
}
