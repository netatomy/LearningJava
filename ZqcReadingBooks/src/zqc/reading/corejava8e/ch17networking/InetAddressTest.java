package zqc.reading.corejava8e.ch17networking;

import java.net.InetAddress;

public class InetAddressTest {

    /**
     * @param args
     */
    public static void main(String[] args) {

        try {
            if (args.length > 0) {
                String host = args[0];
                InetAddress[] addresses = InetAddress.getAllByName(host);
                for (InetAddress address : addresses)
                    System.out.println(address);
            }
            else {
                InetAddress localHostAddress = InetAddress.getLocalHost();
                System.out.println(localHostAddress);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
