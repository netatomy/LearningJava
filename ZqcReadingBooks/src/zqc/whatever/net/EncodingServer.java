package zqc.whatever.net;

import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;

public class EncodingServer {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        String message = "中华人民共和国";
        ServerSocket serverSocket = new ServerSocket(4444);
        while (true) {
            SocketWrapper sw = new SocketWrapper(serverSocket.accept());
            try {
                while (true) {
                    String request = sw.read();
                    System.out.println(request);
                    sw.write(message);
                }
            }
            catch (EOFException ex) {
                sw.close();
            }
        }

    }

}
