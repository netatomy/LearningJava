package zqc.reading.javatutorials.networking.udp;

import java.io.*;

public class QuoteServer {

    public static void main(String[] args) throws Exception {

        new QuoteServerThread().start();
    }
}