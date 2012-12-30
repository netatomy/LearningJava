package zqc.wechat.server.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zqc.wechat.server.domain.User;

public class ChatServer {

    private ServerSocket       serverSocket;
    private Map<Integer, User> onlineUsers = new HashMap<>();
    protected boolean          running     = false;

    public ChatServer(int port) {

        try {
            serverSocket = new ServerSocket(port);

            onlineUsers.put(1, new User(1, "Obama"));
            onlineUsers.put(2, new User(2, "Jobs"));
            onlineUsers.put(3, new User(3, "Gates"));
            onlineUsers.put(4, new User(4, "Ballmer"));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void start() throws IOException {

        Runnable r = new Runnable() {

            @Override
            public void run() {

                while (running) {
                    try {
                        ChatClient cc = new ChatClient(serverSocket.accept(), ChatServer.this);
                        System.out.println("received a client: " + cc.getInetAddress());
                        cc.start();
                    }
                    catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        };
        running = true;
        new Thread(r).start();

    }

    public void stop() {

        running = false;
    }

    public synchronized void addUser(User user) {

        onlineUsers.put(user.getID(), user);
    }

    public synchronized void removeUser(User user) {

        onlineUsers.remove(user.getID());
    }

    

    public void addTalkMessage(String talkMessage) {

        for (User each : onlineUsers.values()) {
            each.addTalkRecord(talkMessage);
        }
    }

}
