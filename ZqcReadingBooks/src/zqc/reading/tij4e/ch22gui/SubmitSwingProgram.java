package zqc.reading.tij4e.ch22gui;

import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class SubmitSwingProgram extends JFrame {
    JLabel label;

    public SubmitSwingProgram(){
        super("Hello Swing");
        
        label = new JLabel("A label");
        add(label);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 100);
        setVisible(true);
    }
    
    static SubmitSwingProgram ssp;
    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                ssp = new SubmitSwingProgram();
            }
        });
        
        TimeUnit.SECONDS.sleep(5);
        
        SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                ssp.label.setText("Hey! This is Different!");
            }
        });
    }

}
