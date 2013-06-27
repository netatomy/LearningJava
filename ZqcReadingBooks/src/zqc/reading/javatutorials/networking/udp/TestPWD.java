package zqc.reading.javatutorials.networking.udp;

import java.io.File;
import java.io.IOException;


public class TestPWD {

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {

        System.out.println(new File(".").getCanonicalPath());
        System.out.println(new File(TestPWD.class.getResource("one-liners.txt").toURI()).length());
    }

}
