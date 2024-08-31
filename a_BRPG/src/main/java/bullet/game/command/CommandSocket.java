package bullet.game.command;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class CommandSocket extends Thread {
    public final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;
    private static final Map<SocketAddress, Map<SocketAddress, CommandSocket>> SOCKETS = new HashMap<>();

    public CommandSocket(Socket socket) throws IOException {
        this.socket = socket;
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
        if (SOCKETS.containsKey(socket.getLocalSocketAddress())) {
            SOCKETS.get(socket.getLocalSocketAddress()).put(socket.getRemoteSocketAddress(), this);
        } else {
            SOCKETS.put(socket.getLocalSocketAddress(), new HashMap<>(){{
                put(socket.getRemoteSocketAddress(), CommandSocket.this);
            }});
        }
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                receive(JSON.parseObject(input.readUTF()));
            }
        } catch (SocketException ignored) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receive(JSONObject message) {
        try {
            new Thread(() -> {
                try {
                    message.to(Invoker.class).execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(String name, Object... args) {
        send(JSONObject.from(new Invoker(name, args)));
    }

    private void send(JSONObject message) {
        try {
            if (message == null) return;
            output.writeUTF(JSONObject.toJSONString(message));
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendTo(String command, SocketAddress from, SocketAddress to, Object... args) {
        JSONObject message = JSONObject.from(new Invoker(command, args));
        SOCKETS.get(from).get(to).send(message);
    }
}
