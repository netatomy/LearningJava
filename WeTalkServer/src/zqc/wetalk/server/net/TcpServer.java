package zqc.wetalk.server.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import zqc.wetalk.exceptions.ApplicationException;

public class TcpServer {

    private List<AcceptClientObserver> observers = new ArrayList<>();
    private ServerSocket            serverSocket;
    private int                     port;
    private Thread handlerThread ;
    private List<TcpClient> aliveClients = new ArrayList<>();

    public TcpServer(int port) {

        this.port = port;
        try {
            serverSocket = new ServerSocket(port);
        }
        catch (IOException ex) {
            throw new ApplicationException(ex);
        }
    }

    private Runnable handler = new Runnable() {

                                 @Override
                                 public void run() {

                                     while (true) {
                                         try {
                                             Socket s = serverSocket.accept();
                                             TcpClient client = new TcpClient(s, TcpServer.this);
                                             if (!client.isAuthenticated("wetalk", "bye")){
                                                 client.close();
                                                 continue;
                                             }
                                             aliveClients.add(client);
                                             notifyObserver(client);
                                             
                                         }
                                         catch (IOException ex) {
                                             
                                         }
                                     }

                                 }
                             };
                             

    public void process() {
        handlerThread = new Thread(handler);
        handlerThread.start();
    }
    
    public void close(){
        handlerThread.interrupt();
        try {
            serverSocket.close();
        }
        catch (IOException ex) {
            throw new ApplicationException(ex);
        }
    }
    
    public List<TcpClient> aliveClients(){
        return aliveClients;
    }

    protected void notifyObserver(TcpClient client) {

        for (AcceptClientObserver observer : observers){
            observer.accept(client);
        }

    }

    public void addObserver(AcceptClientObserver observer) {

        observers.add(observer);
    }

    public void removeObserver(AcceptClientObserver observer) {

        observers.remove(observer);
    }

    public int getPort() {

        return port;
    }

    public void setPort(int port) {

        this.port = port;
    }
}
