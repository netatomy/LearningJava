package zqc.reading.javatutorials.networking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class URLOpenStreamDemo {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        URL oracle = new URL("http://www.oracle.com/");
        BufferedReader in = new BufferedReader(new InputStreamReader(
                        oracle.openStream()));
        try {
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        }
        finally {
            in.close();
        }
    }

}
