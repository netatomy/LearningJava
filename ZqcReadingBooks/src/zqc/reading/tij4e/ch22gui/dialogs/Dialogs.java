package zqc.reading.tij4e.ch22gui.dialogs;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;


public class Dialogs extends JFrame {
    private JButton b1 = new JButton("Dialog Box");
    private MyDialog dlg = new MyDialog(null);
    
    public Dialogs() {
        super("Dialogs");
        
        setSize(125, 75);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        b1.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
            
                dlg.setVisible(true);
            }
        });
        add(b1);
    }
    /**
     * @param args
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                JFrame f = new Dialogs();
                f.setVisible(true);
            }
        });

    }
    
}

class MyDialog extends JDialog{
    
    public MyDialog(JFrame parent) {
        super(parent, "My dialog", true);
        setLayout(new FlowLayout());
        add(new JLabel("Here is my dialog"));
        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
            
                dispose();
            }
        });
        add(ok);
        
        setSize(150, 125);
        setResizable(false);
        setLocationRelativeTo(parent);
    }
}