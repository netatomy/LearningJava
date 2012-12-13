package zqc.whatever.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestClient {

    /**
     * @param args
     * @throws IOException
     * @throws UnknownHostException
     */
    public static void main(String[] args) throws UnknownHostException, IOException {

        Socket s = new Socket("localhost", 3200);
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        DataInputStream dis = new DataInputStream(s.getInputStream());
        
        String msg = "There's a fire starting in my heart!";
        byte[] msgBytes = msg.getBytes();
        int n = msgBytes.length;
        
        dos.writeInt(n);
        dos.write(msgBytes);
        dos.flush();
        
        int len = dis.readInt();
        byte[] data = new byte[len];
        dis.read(data);
        String result = new String(data);
        System.out.println(result);
        
        s.close();
    }

}
