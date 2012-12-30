package zqc.wetalk.ui;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class WeChatFrame extends JFrame {
    
    protected SwingComponentBuilder swingBuilder = new SwingComponentBuilder();

	public WeChatFrame() throws HeadlessException {
		super();
	}

	public WeChatFrame(GraphicsConfiguration gc) {
		super(gc);
	}

	public WeChatFrame(String title) throws HeadlessException {
		super(title);
	}

	public WeChatFrame(String title, GraphicsConfiguration gc) {
		super(title, gc);
	}

	public void setScreenCenter() {
	    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	    int left = (d.width - this.getWidth()) / 2;
	    int top = (d.height - this.getHeight()) / 2;
	    this.setLocation(left, top);
	}

	protected JPanel createPanel() {
		return new JPanel();
	}

}