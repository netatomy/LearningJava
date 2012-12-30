package zqc.reading.tij4e.ch13regex;

import java.util.Scanner;
import java.util.regex.MatchResult;


public class ThreatAnalyzers {

    static String threatData = 
                    "58.27.82.161@02/10/2005\n" +
                    "204.27.82.161@02/11/2005\n" +
                    "58.27.82.161@02/11/2005\n" +
                    "58.27.82.161@02/12/2005\n" +
                    "58.27.82.161@02/12/2005\n" +
                    "[Next log section with different data format]";
    /**
     * @param args
     */
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(threatData);
        String pattern = "(\\d+[.]\\d+[.]\\d+[.]\\d+)@(\\d{2}/\\d{2}/\\d{4})";
        while (scanner.hasNext(pattern)){
            scanner.next(pattern);
            MatchResult match = scanner.match();
            String ip = match.group(1);
            String date = match.group(2);
            System.out.format("Threat on %s from %s%n", date, ip);
        }
        System.out.println("DONE.");
    }

}
