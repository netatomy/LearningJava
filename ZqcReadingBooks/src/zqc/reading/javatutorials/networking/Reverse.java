package zqc.reading.javatutorials.networking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Reverse {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.out.println("Usage: java Reverse "
                            + "http://<location of your servlet/script>"
                            + " string_to_reverse");
            System.exit(1);
        }

        String stringToReverse = URLEncoder.encode(args[1], "UTF-8");
        URL url = new URL(args[0]);
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);

        OutputStreamWriter out = new OutputStreamWriter(
                        connection.getOutputStream());
        try {
            out.write("string=" + stringToReverse);
        }
        finally {
            out.close();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
        String decodeString = null;
        try {
            while ((decodeString = in.readLine()) != null) {
                System.out.println(decodeString);
            }
        }
        finally {
            in.close();
        }
    }

}
