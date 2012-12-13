package zqc.whatever.net;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EchoServer {

    static List<ClientService> clientServices = new ArrayList<>();

    /**
     * @param args
     */
    public static void main(String[] args) {

        boolean running = true;

        try {
            ServerSocket serverSocket = new ServerSocket(4321);
            while (running) {
                final Socket socket = serverSocket.accept();
                Runnable r = new Runnable() {

                    @Override
                    public void run() {

                        ClientService cs = new ClientService(socket);
                        clientServices.add(cs);
                        cs.start();
                    }
                };
                new Thread(r).start();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}

class ClientService {

    private Socket      _clientSocket;
    private PrintWriter _printWriter;
    private boolean     _running;

    public ClientService(Socket clientSocket) {

        _clientSocket = clientSocket;
        System.out.println("Connect from " + _clientSocket.getInetAddress());
        try {
            _printWriter = new PrintWriter(_clientSocket.getOutputStream(), true);
        }
        catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void start() {

        _running = true;
        while (_running) {
            System.out.println("Receiving data...");
            byte[] data = receiveData();
            Message message = parseMessage(data);
            System.out.println("Processing message...");
            processMessage(message);
        }
        
        try {
            _clientSocket.close();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

    private void processMessage(Message message) {

        MessageProcess messageProcess = MessageProcessFactory.getInstance(this).createMessageProcess(message);
        String responseMessage = messageProcess.response();
        sendMessage(responseMessage);

        System.out.println(message.getCommand());
    }

    private void sendMessage(String msg) {

        System.out.println("Sending " + msg + " to " + _clientSocket.getInetAddress());
        _printWriter.println(msg);
    }

    private Message parseMessage(byte[] data) {

        Message result = new Message();
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            DataInputStream dis = new DataInputStream(new BufferedInputStream(bais));
            int cmdCount = dis.readInt();
            byte[] cmdBytes = new byte[cmdCount];
            dis.read(cmdBytes);
            String cmd = new String(cmdBytes);
            result.setCommand(cmd);

            if (cmd.equalsIgnoreCase("CALC")) {

                int valuesCount = dis.readInt();
                byte[] valuesBytes = new byte[valuesCount];
                dis.read(valuesBytes);
                String[] values = new String(valuesBytes).split("\0");
                for (String v : values) {
                    String[] s = v.split("=");
                    result.addValue(s[0], s[1]);
                }
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        return result;

    }

    private byte[] receiveData() {

        try {
            DataInputStream dis = new DataInputStream(_clientSocket.getInputStream());
            int len = dis.readInt();
            System.out.println("Total Size: " + len);

            byte[] data = new byte[len];
            dis.read(data);
            return data;
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public void stop() {

        if (!_running)
            return;
        _running = false;
    }
}

class MessageProcessFactory {

    private ClientService                _service;

    private static MessageProcessFactory _instance;

    private MessageProcessFactory(ClientService cs) {

        _service = cs;
    }

    public MessageProcess createMessageProcess(Message message) {

        switch (message.getCommand()) {
        case "CONN":
            return new ConnectMessageProcess();
        case "DISC":
            return new DisconnectMessageProcess(_service);
        case "CALC":
            return new CalculateMessageProcess(message);
        default:
            return new NullMessageProcess();
        }
    }

    public static MessageProcessFactory getInstance(ClientService clientService) {

        if (_instance == null)
            _instance = new MessageProcessFactory(clientService);
        return _instance;
    }
}

abstract class MessageProcess {

    abstract String response();
}

class NullMessageProcess extends MessageProcess {

    @Override
    String response() {

        return "";
    }
}

class ConnectMessageProcess extends MessageProcess {

    @Override
    String response() {

        return "OK";
    }
}

class DisconnectMessageProcess extends MessageProcess {

    private ClientService _service;

    public DisconnectMessageProcess(ClientService service) {

        _service = service;
    }

    @Override
    String response() {

        _service.stop();
        return "CLOSE";
    }

}

class CalculateMessageProcess extends MessageProcess {

    private Message _message;

    public CalculateMessageProcess(Message message) {

        _message = message;
    }

    @Override
    String response() {

        return String.valueOf(calculateArea());
    }

    private double calculateArea() {

        double area = 0.0;
        switch (_message.getShapeType().toLowerCase()) {
        case "circle":
            double radius = Double.parseDouble(_message.getValue("radius"));
            area = radius * radius * Math.PI;
            break;
        case "rectangle":
            double a = Double.parseDouble(_message.getValue("a"));
            double b = Double.parseDouble(_message.getValue("b"));
            area = a * b;
            break;
        }
        return area;
    }
}

class Message {

    private String              _command;
    private Map<String, String> _values = new HashMap<>();

    public Message() {

    }

    public String getCommand() {

        return _command;
    }

    public void setCommand(String cmd) {

        _command = cmd;
    }

    public String getShapeType() {

        return (String) _values.get("shapetype");
    }

    public void addValue(String key, String value) {

        _values.put(key.toLowerCase(), value);
    }

    public String getValue(String key) {

        return _values.get(key);
    }
}
