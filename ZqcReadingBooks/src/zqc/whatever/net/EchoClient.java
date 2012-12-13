package zqc.whatever.net;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class EchoClient {

    public static final String SEPARATOR  = "\0";
    public static final String CONNECT    = "CONN";
    public static final String DISCONNECT = "DISC";
    public static final String CALCULATE  = "CALC";

    static Socket              socket;
    static Scanner             in         = new Scanner(System.in);
    static boolean             connected  = false;
    private static boolean     running;

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        String c = CONNECT;
        running = true;
        while (running) {
            System.out.print("请输入命令：(CONN,DISC,CALC)");
            c = in.nextLine().toUpperCase();
            switch (c) {
            case CONNECT:
                if (!connected) {
                    connect();
                    connected = true;
                    System.out.println("Connect to server successfully.");
                }
                else {
                    System.out.println("Already connected.");
                }
                break;
            case DISCONNECT:
                if (connected) {
                    disconnect();
                    running = false;
                    System.out.println("Disconnected from server");
                }
                else {
                    System.out.println("Already disconnected.");
                }
                break;
            case CALCULATE:
                String values = getShapeValues();
                sendMessage("CALC", values);
                System.out.println("Message sent to server completely.");
                break;
            }
            String msg = receiveMessage();
            System.out.println("Response: " + msg);
        }

        socket.close();
    }

    private static String getShapeValues() {

        System.out.print("ShapeType=");
        String shapeType = in.nextLine().toLowerCase();
        switch (shapeType){
        case "circle":
            System.out.print("Radius=");
            String radius = in.nextLine();
            return String.format("shapetype=%s\0radius=%s", shapeType, radius);
        case "rectangle":
            System.out.print("a=");
            String a = in.nextLine();
            System.out.print("b=");
            String b = in.nextLine();
            return String.format("shapetype=%s\0a=%s\0b=%s", shapeType, a, b);
        default:
            return "";
        }
        
    }

    private static String receiveMessage() {

        try {
            Scanner in = new Scanner(socket.getInputStream());
            return in.nextLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void disconnect() {

        sendMessage(DISCONNECT);
    }

    static void sendMessage(String command) {

        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] cmdBytes = command.getBytes();
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeInt(cmdBytes.length);
            dos.write(cmdBytes);
            dos.flush();
            byte[] data = os.toByteArray();
            send(data);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void sendMessage(String cmd, String values) {

        // "shapetype=rectangle\0a=10\0b=20"; //"shapetype=circle\0radius=10.0";
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            byte[] cmdBytes = cmd.getBytes();

            DataOutputStream dos = new DataOutputStream(os);
            dos.writeInt(cmdBytes.length);
            dos.write(cmdBytes);

            byte[] valuesBytes = values.getBytes();
            dos.writeInt(valuesBytes.length);
            dos.write(valuesBytes);

            dos.flush();

            byte[] data = os.toByteArray();
            send(data);

        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void connect() {

        try {
            socket = new Socket("localhost", 4321);
            sendMessage(CONNECT);
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseMessage(String filePath) throws IOException {

        FileInputStream fis = new FileInputStream(filePath);
        DataInputStream dis = new DataInputStream(fis);
        int len = dis.readInt();
        System.out.println("Total Size: " + len);

        byte[] data = new byte[len];
        dis.read(data);
        dis.close();
        fis.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        dis = new DataInputStream(new BufferedInputStream(bais));

        int cmdCount = dis.readInt();
        byte[] cmdBytes = new byte[cmdCount];
        dis.read(cmdBytes);
        String cmd = new String(cmdBytes);
        System.out.println(cmd);

        if (CONNECT.equalsIgnoreCase(cmd) || DISCONNECT.equalsIgnoreCase(cmd))
            return;

        int valuesCount = dis.readInt();
        byte[] valuesBytes = new byte[valuesCount];
        dis.read(valuesBytes);
        String[] values = new String(valuesBytes).split(SEPARATOR);
        for (String v : values)
            System.out.print(v + " ");
    }

    private static void send(byte[] data) {

        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(data.length);
            dos.write(data);
            dos.flush();
            // dos.close();
            System.out.println("Data sent completely.");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
