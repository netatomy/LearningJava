package zqc.reading.javatutorials.networking.udp;

public class MulticastServer {

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {

        new MulticastServerThread().start();
    }

}
