package zqc.wetalk.server.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import zqc.wetalk.exceptions.ApplicationException;

public class TcpClient {

    private TcpServer                   server;
    private Socket                      socket;
    private Scanner                     scanner;
    private PrintWriter                 writer;

    private List<AcceptMessageObserver> observers = new ArrayList<>();
    private Runnable                    handler   = new Runnable() {

                                                      @Override
                                                      public void run() {

                                                          try {
                                                              while (true) {

                                                                  String head = readLine();
                                                                  String body = readLine();
                                                                  notifyObservers(head, body);

                                                              }
                                                          }
                                                          catch (Exception ex) {
                                                              
                                                          }
                                                      }
                                                  };
    private Thread                      handlerThread;

    public TcpClient(Socket socket, TcpServer server) {

        this.server = server;
        this.socket = socket;

        try {
            scanner = new Scanner(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream(), true);
        }
        catch (IOException ex) {
            throw new ApplicationException(ex);
        }
    }

    private void notifyObservers(String head, String body) {

        for (AcceptMessageObserver observer : observers) {
            observer.accept(head, body);
        }

    }

    public void addObserver(AcceptMessageObserver observer) {

        observers.add(observer);
    }

    public void removeObserver(AcceptMessageObserver observer) {

        observers.remove(observer);
    }

    public void process() {

        handlerThread = new Thread(handler);
        handlerThread.start();
    }

    public Socket getSocket() {

        return socket;
    }

    public TcpServer getServer() {

        return server;
    }

    public String readLine() {

        return scanner.nextLine();
    }

    public void writeLine(String line) {

        writer.println(line);
    }

    public void close() {

        server.aliveClients().remove(this);
        handlerThread.interrupt();
        try {
            scanner.close();
            writer.close();
            socket.close();
        }
        catch (IOException ex) {
            throw new ApplicationException(ex);
        }
    }

    public boolean isAuthenticated(String code, String bye) {

        String token = readLine();
        if (code.equalsIgnoreCase(token)) {
            writeLine(code);
            return true;
        }
        writeLine(bye);
        return false;
    }

}
