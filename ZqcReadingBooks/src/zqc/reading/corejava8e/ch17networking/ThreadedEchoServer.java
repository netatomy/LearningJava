package zqc.reading.corejava8e.ch17networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ThreadedEchoServer {

    /**
     * @param args
     */
    public static void main(String[] args) {

        try {
            int i = 1;
            ServerSocket s = new ServerSocket(8188);

            while (true) {
                Socket incoming = s.accept();
                System.out.println("Spawnging " + i);
                Runnable r = new ThreadedEchoHandler(incoming);
                Thread t = new Thread(r);
                t.start();
                i++;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ThreadedEchoHandler implements Runnable {

    
    public ThreadedEchoHandler(Socket socket) {
        incoming = socket;
    }
    @Override
    public void run() {
        try {
            try {
                InputStream inStream = incoming.getInputStream();
                OutputStream outStream = incoming.getOutputStream();
                Scanner in = new Scanner(inStream);
                PrintWriter out = new PrintWriter(outStream, true);//autoFlush
                out.println("Hello! Enter BYE to exit.");
                
                boolean done = false;
                while (!done && in.hasNextLine()){
                    String line = in.nextLine();
                    out.println("Echo: " + line);
                    if (line.trim().equals("BYE"))
                        done = true;
                }
            }
            finally {
                incoming.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private Socket incoming;
}
