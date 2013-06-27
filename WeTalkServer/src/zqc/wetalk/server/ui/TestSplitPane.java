package zqc.wetalk.server.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class TestSplitPane extends JFrame {

    public TestSplitPane() {
        super("Test JSplitPane");
        setSize(600, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        

        JPanel p1 = new JPanel();
        p1.add(new JButton("Test1"));
        JPanel p2 = new JPanel();
        p2.add(new JButton("Test2"));
        JPanel p3 = new JPanel();
        p3.add(new JButton("Test3"));
        JTabbedPane tp = new JTabbedPane();
        tp.add("Tab 1", p1);
        tp.add("Tab 2", p2);
        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tp, p3);
        
        add(sp);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        JFrame f = new TestSplitPane()
        ;
        f.setVisible(true);
    }

}
