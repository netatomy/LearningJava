package zqc.reading.javatutorials.networking.tcpsockets;

import java.net.*;
import java.io.*;

public class KnockKnockServer {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4444);
        }
        catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(1);
        }
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        }
        catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(
                        clientSocket.getInputStream()));
        String inputLine, outputLine;
        KnockKnockProtocol kkp = new KnockKnockProtocol();
        outputLine = kkp.processInput(null);
        out.println(outputLine);
        while ((inputLine = in.readLine()) != null) {
            outputLine = kkp.processInput(inputLine);
            out.println(outputLine);
            if (outputLine.equals("Bye."))
                break;
        }
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}

//class KnockKnockServer2 {
//
//    public static void main(String[] args) {
//
//        try {
//
//            ServerSocket serverSocket = new ServerSocket(4444);
//            try {
//                Socket clientSocket = serverSocket.accept();
//                PrintWriter out = new PrintWriter(
//                                clientSocket.getOutputStream(), true);
//                BufferedReader in = new BufferedReader(new InputStreamReader(
//                                clientSocket.getInputStream()));
//                try {
//                    String inputLine = null;
//                    String outputLine = null;
//
//                    // initiate conversation with client
//                    KnockKnockProtocol kkp = new KnockKnockProtocol();
//                    outputLine = kkp.processInput(null);
//                    out.println(outputLine);
//
//                    while ((inputLine = in.readLine()) != null) {
//                        outputLine = kkp.processInput(inputLine);
//                        out.println(outputLine);
//                        if (outputLine.equalsIgnoreCase("bye"))
//                            break;
//                    }
//                }
//                finally {
//                    out.close();
//                    in.close();
//                    clientSocket.close();
//                }
//            }
//            catch (IOException e) {
//                System.err.println("Accept failed: 4444");
//                System.exit(-1);
//            }
//            finally {
//                serverSocket.close();
//            }
//
//        }
//        catch (IOException e) {
//            System.err.println("Could not listen on port: 4444");
//            System.exit(-1);
//        }
//
//    }
//
//}
