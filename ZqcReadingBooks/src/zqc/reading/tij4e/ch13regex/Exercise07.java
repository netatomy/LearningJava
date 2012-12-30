package zqc.reading.tij4e.ch13regex;


public class Exercise07 {

    /**
     * @param args
     */
    public static void main(String[] args) {

        String regex = "^\\p{Upper}.*\\.+$";
        String[] testStrings = {"Hello, World", "hello, world.", "Hello, world."};
        for(String s : testStrings){
            System.out.println(s.matches(regex));
        }
    }
}
