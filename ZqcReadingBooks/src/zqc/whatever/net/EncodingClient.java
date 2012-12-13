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
        client.connect("localhost", 4444);
        
        for (int i = 1; i <= 5; i++){
            client.write2("你好");
            String response = client.read2();
            System.out.println(response);
            Thread.sleep(2000);
        }
        client.close();
    }

}
