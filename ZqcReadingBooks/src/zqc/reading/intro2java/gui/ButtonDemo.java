package zqc.reading.intro2java.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class ButtonDemo extends JFrame {

    public ButtonDemo() {
        final MessagePanel messagePanel = new MessagePanel();
        messagePanel.setBorder(new LineBorder(new Color(255, 0, 0)));
        add(messagePanel);

        JButton btn = new JButton("Test");
        add(btn, BorderLayout.SOUTH);
        
        btn.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                messagePanel.moveRight();
            }
        });
        
        setTitle("ButtonDemo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        JFrame f = new ButtonDemo();
        f.setVisible(true);
    }

}
