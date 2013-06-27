package zqc.whatever.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Arrays;

public class SocketWrapper {

    private Socket socket;

    public SocketWrapper(Socket socket) {

        this.socket = socket;
    }

    public SocketWrapper() {

        this.socket = new Socket();
    }

    public void connect(String hostName, int port) throws IOException {

        socket.connect(new InetSocketAddress(hostName, port));
    }

    public void close() throws IOException {

        socket.close();
    }

    public void writeBytes(byte[] data) throws IOException {

        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeInt(data.length);
        out.write(data);
        out.flush();
    }

    public byte[] readBytes() throws IOException {

        DataInputStream in = new DataInputStream(socket.getInputStream());
        int length = in.readInt();
        byte[] data = new byte[length];
        int count = 0;
        count = in.read(data);
        if (count < length)
            data = Arrays.copyOf(data, count);
        return data;
    }

    public void write(String message, String charset) throws UnsupportedEncodingException, IOException {

        byte[] data = message.getBytes(charset);
        writeBytes(data);
    }

    public void write(String message) throws UnsupportedEncodingException, IOException {

        write(message, "utf-8");
    }

    public String read(String charset) throws IOException {

        byte[] data = readBytes();
        return new String(data, Charset.forName(charset));
    }

    public String read() throws IOException {

        return read("utf-8");
    }

    public String readUTF() throws IOException {

        DataInputStream in = new DataInputStream(socket.getInputStream());
        return in.readUTF();
    }

    public void writeUTF(String message) throws UnsupportedEncodingException, IOException {

        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF(message);
    }
}
