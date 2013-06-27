package zqc.reading.corejava8e.ch14threads;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class BallComponent extends JPanel
{
    public void add(Ball b){
        balls.add(b);
}
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        for (Ball b : balls){
            g2.fill(b.getShape());
        }
    }
    
    private ArrayList<Ball> balls = new ArrayList<>();
}