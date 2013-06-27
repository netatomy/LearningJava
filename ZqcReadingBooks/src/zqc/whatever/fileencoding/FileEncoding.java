package zqc.whatever.fileencoding;

import java.io.UnsupportedEncodingException;
import java.util.Map.Entry;
import java.util.Properties;


public class FileEncoding {

    /**
     * @param args
     * @throws UnsupportedEncodingException 
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
       
        byte[] data = "显示系统属性：".getBytes(args[0]);
        System.out.println(new String(data, args[0]));

        Properties props = System.getProperties();
        
        for (Entry<?, ?> entry : props.entrySet()){
            if (entry.getKey().toString().contains("file"))
                System.out.print("||||");
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }

    }

}
