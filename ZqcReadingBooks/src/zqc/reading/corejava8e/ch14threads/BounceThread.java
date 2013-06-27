package zqc.reading.corejava8e.ch14threads;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class BounceThread {

    /**
     * @param args
     */
    public static void main(String[] args) {

        //EventQueue.invokeLater(new Runnable() {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {

                JFrame frame = new BounceFrameWithThread();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });

    }

}

class BounceFrameWithThread extends JFrame {

    public BounceFrameWithThread() {

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setTitle("BounceThread");

        comp = new BallComponent();
        add(comp, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        addButton(buttonPanel, "Start", new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                addBall();
            }
        });
        addButton(buttonPanel, "Close", new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public void addButton(Container c, String title, ActionListener listener){
        JButton button = new JButton(title);
        c.add(button);
        button.addActionListener(listener);
    }
    
    public void addBall(){
            Ball ball = new Ball();
            comp.add(ball);
            Runnable r = new BallRunnable(ball, comp);
            Thread t = new Thread(r);
            t.start();
    }
    
    private BallComponent comp;
    public static final int DEFAULT_WIDTH = 450;
    public static final int DEFAULT_HEIGHT = 350;
    public static final int STEPS = 1000;
    public static final int DELAY = 3;
}
