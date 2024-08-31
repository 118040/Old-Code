package bullet.game.command;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Commands {
    protected static final Map<String, Method> COMMANDS = new HashMap<>();

    static {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Enumeration<URL> resources = classLoader.getResources("bullet");
            List<File> dirs = new ArrayList<>();
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                dirs.add(new File(resource.getFile()));
            }
            List<Class<?>> classes = new ArrayList<>();
            for (File dir : dirs) {
                classes.addAll(findClasses(dir, "bullet"));
            }
            for (Class<?> clazz : classes) {
                try {
                    Method[] methods = clazz.getMethods();
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Command.class)) {
                            Command annotation = method.getAnnotation(Command.class);
                            if (COMMANDS.containsKey(annotation.name())) {
                                throw new RuntimeException("Command " + annotation.name() + "has been defined");
                            }
                            COMMANDS.put(annotation.name(), method);
                        } else if (method.isAnnotationPresent(Parser.class)) {
                            if (method.getReturnType().equals(method.getAnnotation(Parser.class).type())
                                    && method.getParameterTypes().length == 1
                                    && method.getParameterTypes()[0].equals(String.class)) {
                                Format.PARSERS.put(method.getAnnotation(Parser.class).type(), method);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Class<?>> findClasses(File dir, String path) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!dir.exists()) {
            return classes;
        }
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    classes.addAll(findClasses(file, path + "." + file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    classes.add(Class.forName(path + '.' + file.getName().substring(0, file.getName().length() - 6)));
                }
            }
        }
        return classes;
    }

    public static void invoke(String name, Object... args) throws InvocationTargetException, IllegalAccessException {
        new Invoker(name, args).execute();
    }

    public static void invokeByString(String command) throws Exception {
        String regex = "^/(?<name>\\S+)(?<args>(?: \\S+)*)";
        Matcher matcher = Pattern.compile(regex).matcher(command);
        if (!matcher.matches()) return;
        String name = matcher.group("name"), args = matcher.group("args");
        String[] argArr = args.length() == 0 ? new String[0] : args.trim().split("\\s");
        new Invoker(name, argArr).execute();
    }
}
