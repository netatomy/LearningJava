package zqc.wechat.server;

import java.io.IOException;

import javax.swing.SwingUtilities;

import zqc.wechat.server.net.ChatServer;
import zqc.wechat.server.ui.WeChatServerFrame;

public class WeChatServerApp {

	public static void main(String[] args) throws IOException, InterruptedException {

//        ChatServer server = new ChatServer(5600);
//        server.start();
//        System.out.println("Server started");
	    SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {
            
                WeChatServerFrame f = new WeChatServerFrame();
                
                f.setVisible(true);
            }
        });
    }

}
