package zqc.reading.tij4e.ch22gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class ComboBoxDemo extends JFrame {

    private String[]   description = { "A", "B", "C", "D", "E", "F", "G", "H" };

    private JTextField t           = new JTextField(15);
    private JComboBox  c           = new JComboBox();
    private JButton    b           = new JButton("Add items");
    private int        count       = 0;

    public ComboBoxDemo() {

        for (int i = 0; i < 4; i++) {
            c.addItem(description[i]);
            count++;
        }
        t.setEditable(false);
        b.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (count < description.length)
                    c.addItem(description[count++]);
            }
        });
        c.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                t.setText("index: " + c.getSelectedIndex() + " "
                                + ((JComboBox) e.getSource()).getSelectedItem());
            }
        });

        setLayout(new FlowLayout());
        add(t);
        add(c);
        add(b);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        SwingConsole.run(new ComboBoxDemo(), 200, 175);

    }

}
