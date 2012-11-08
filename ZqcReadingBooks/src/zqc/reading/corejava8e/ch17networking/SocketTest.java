package zqc.reading.corejava8e.ch17networking;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;


public class SocketTest {

    /**
     * @param args
     */
    public static void main(String[] args) {

        try {
            Socket s = new Socket("time-A.timefreq.bldrdoc.gov", 13);
            try{
                InputStream in = s.getInputStream();
                Scanner scanner = new Scanner(in);
                
                while (scanner.hasNextLine()){
                    String line = scanner.nextLine();
                    System.out.println(line);
                }
            } finally{
                s.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
