package zqc.wechat.server.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

public class ChatClient {

    private ChatServer           chatServer;
    private Socket               socket;
    private BufferedInputStream  in;
    private BufferedOutputStream out;
    private static final int     BUFFER_SIZE = 1024 * 1024;

    public ChatClient(Socket s, ChatServer chatServer) {

        this.chatServer = chatServer;
        try {
            socket = s;
            in = new BufferedInputStream(socket.getInputStream());
            out = new BufferedOutputStream(socket.getOutputStream());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] receiveBytes() throws IOException {

        int count = 0;
        byte[] data = new byte[BUFFER_SIZE];
        count = in.read(data);
        data = Arrays.copyOf(data, count);
        return data;
    }

    public String receive() throws IOException {

        return new String(receiveBytes());
    }

    public void sendBytes(byte[] data) throws IOException {

        out.write(data);
        out.flush();
    }

    public void send(String message) throws IOException {

        sendBytes(message.getBytes());
    }

    public void close() throws IOException {

        in.close();
        out.close();
        socket.close();
    }

    public ChatServer getChatServer() {

        return chatServer;
    }

    public void start() {

        Runnable clientTask = new ChatClientTask(this);
        new Thread(clientTask).run();
    }

    public InetAddress getInetAddress() {

        return socket.getInetAddress();
    }

}
