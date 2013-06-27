package zqc.reading.intro2java.gui;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.beans.Transient;

import javax.swing.JPanel;


public class MessagePanel extends JPanel {

    private String message = "Welcome to Java";
    private int x = 20;
    private int y  = 20;
    private boolean centered;
    private int interval = 10;
    
    public MessagePanel(){
    }

    public MessagePanel(String message){
        this.message = message;
    }

    
    public String getMessage() {
    
        return message;
    }

    
    public void setMessage(String message) {
    
        this.message = message;
        repaint();
    }
    
    public void moveLeft(){
        x -= interval;
        repaint();
    }
    
    public void moveRight(){
        x += interval;
        repaint();
    }
    
    public void moveUp(){
        y -= interval;
        repaint();
    }
    
    public void moveDown(){
        y += interval;
        repaint();
    }
    
//    @Override
//    @Transient
    public Dimension getPreferredSize() {
    
        return new Dimension(200, 30);
    }
    
    public int getX() {
    
        return x;
    }

    
    public void setX(int x) {
    
        this.x = x;
        repaint();
    }

    
    public int getY() {
    
        return y;
    }

    
    public void setY(int y) {
    
        this.y = y;
        repaint();
    }

    
    public boolean isCentered() {
    
        return centered;
    }

    
    public void setCentered(boolean centered) {
    
        this.centered = centered;
        repaint();
    }

    
    public int getInterval() {
    
        return interval;
    }

    
    public void setInterval(int interval) {
    
        this.interval = interval;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (centered){
            FontMetrics fm = g.getFontMetrics();
            int stringWidth = fm.stringWidth(message);
            int stringAscent = fm.getAscent();
            x = getWidth() / 2 - stringWidth / 2;
            y = getHeight() / 2 + stringAscent / 2;
        }
        g.drawString(message, x, y);
    }
}
