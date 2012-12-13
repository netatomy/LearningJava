package zqc.whatever.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(3200);
        while (true) {
            Socket s = serverSocket.accept();
            System.out.println(s.getInetAddress());
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            int n = dis.readInt();
            byte[] data = new byte[n];
            dis.read(data);
            String msg = new String(data);
            System.out.println(msg);
            
            String returnMsg = "OK";
            dos.writeInt(returnMsg.getBytes().length);
            dos.write(returnMsg.getBytes());
            dos.flush();
            System.out.println(n);
            //dis.close();
            s.close();

        }

    }

}
