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
    
    public void close() throws IOException{
        socket.close();
    }
    
    public void write(String message, String charset) throws UnsupportedEncodingException, IOException{
        socket.getOutputStream().write(message.getBytes(charset));
        socket.getOutputStream().flush();
    }
    
    public void write(String message) throws UnsupportedEncodingException, IOException{
        write(message, "utf-8");
    }
    
    public String read(String charset) throws IOException{
        byte[] data = new byte[1024*1024];
        int count = 0;
        count = socket.getInputStream().read(data);
        if (count < 1024*1024)
            data = Arrays.copyOf(data, count);
        return new String(data, Charset.forName(charset));
    }
    
    public String read() throws IOException{
        return read("utf-8");
    }
    
    public String read2() throws IOException{
        DataInputStream in = new DataInputStream(socket.getInputStream());
        int length = in.readInt();
        byte[] data = new byte[length];
        int count = 0;
        count = in.read(data);
//        if (count < data.length)
//            data = Arrays.copyOf(data, count);
        return new String(data);
    }
    
    public void write2(String message) throws UnsupportedEncodingException, IOException{
        DataOutputStream out= new DataOutputStream(socket.getOutputStream());
        out.writeInt(message.getBytes().length);
        out.write(message.getBytes());
        out.flush();
    }
}