package bullet.game.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Invoker {
    private final String name;
    private final Object[] args;

    protected Invoker(String name, Object... args) {
        if (!Commands.COMMANDS.containsKey(name)) throw new RuntimeException("Command does not exist");
        int count = Commands.COMMANDS.get(name).getParameterCount();
        if (args.length != count) throw new RuntimeException("The number of command parameters is incorrect");
        this.name = name;
        this.args = args;
    }

    protected Invoker(String name, String[] strs) throws Exception {
        if (!Commands.COMMANDS.containsKey(name)) throw new RuntimeException("Command does not exist");
        this.name = name;
        Method method = Commands.COMMANDS.get(name);
        Class<?>[] types = method.getParameterTypes();
        if (strs.length != types.length) throw new RuntimeException("The number of command parameters is incorrect");
        Object[] args = new Object[strs.length];
        for (int i = 0; i < args.length; i++) {
            args[i] = Format.parse(strs[i], types[i]);
        }
        this.args = args;
    }

    public void execute() throws InvocationTargetException, IllegalAccessException {
        Commands.COMMANDS.get(name).invoke(null, args);
    }
}
