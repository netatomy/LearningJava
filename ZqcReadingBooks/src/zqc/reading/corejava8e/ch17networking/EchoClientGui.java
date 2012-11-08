package zqc.reading.corejava8e.ch17networking;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class EchoClientGui {

    /**
     * @param args
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {
            
                JFrame f = new EchoClientFrame();
                
                f.setVisible(true);
            }
        });
    }

}

class EchoClientFrame extends JFrame{
    public EchoClientFrame(){
        setTitle("Echo Cleint");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JPanel topPanel = new JPanel();
        
        JLabel lbl = new JLabel("Messag:");
        //lbl.setPreferredSize(new Dimension(20, getPreferredSize().height));
        topPanel.add(lbl);
        JTextField msg = new JTextField("Some Message", 20);
        topPanel.add(msg);
        JButton btn = new JButton("Send");
        topPanel.add(btn);
        
        add(topPanel, BorderLayout.NORTH);
        
        //JPanel echoPanel = new JPanel();
        JTextArea echoResult = new JTextArea();
        add(new JScrollPane(echoResult));
        
        //add(echoPanel, BorderLayout.CENTER);
    }
}
