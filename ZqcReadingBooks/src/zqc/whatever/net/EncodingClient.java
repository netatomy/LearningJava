package zqc.whatever.net;

import java.io.IOException;


public class EncodingClient {

    /**
     * @param args
     * @throws IOException 
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        SocketWrapper client = new SocketWrapper();
        client.connect("192.168.56.101", 4444);
        
        for (int i = 1; i <= 5; i++){
            client.writeUTF("你好");
            String response = client.readUTF();
            System.out.println(response);
            Thread.sleep(2000);
        }
        client.close();
    }

}
