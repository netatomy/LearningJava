package zqc.reading.tij4e.ch13regex;

import java.util.Arrays;

public class Splitting {

    static String knights = "Then, when you have found the shrubbery, you must "
                                          + "cut down the mightiest tree in the forest... " + "with... a herring!";

    /**
     * @param args
     */
    public static void main(String[] args) {
        split(" ");  // Doesn't have to contain regex chars
        split(", ");
        split("\\W+"); // non-word characters
        split("n\\W+"); // n followed by a non-word characters
    }
    
    static void split(String regex){
        System.out.println(Arrays.toString(knights.split(regex)));
    }

}
