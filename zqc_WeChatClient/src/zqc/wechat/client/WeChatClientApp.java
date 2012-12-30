package zqc.wechat.client;

import java.io.IOException;

import javax.swing.SwingUtilities;

import zqc.wechat.client.net.ChatClient;
import zqc.wechat.client.ui.LogonFrame;
import zqc.wetalk.UserInfo;
import zqc.wetalk.ui.WeChatFrame;

public class WeChatClientApp {

	public static final String SERVER_NAME = "localhost";
	public static final int SERVER_PORT = 5600;

	public static void main(String[] args) throws InterruptedException,
			IOException {

		// String[] talkList = { "Hello everyone.",
		// "Nice to meet you.",
		// "How are you?",
		// "Are you all right?",
		// "中华人民共和国",
		// "Java 语言程序设计",
		// "Java 核心技术",
		// "Java 编程思想"
		// };
		//
		// ChatClient client = new ChatClient("localhost", 5600);
		//
		// for (int i = 0; i < talkList.length; i++) {
		// client.connect();
		// String message = "TALK\n" + ((int) (Math.random() * 4) + 1) + "\n"
		// + talkList[i] + "\n";
		// System.out.println("sent: \n" + message);
		// client.send(message.getBytes());
		// String response = new String(client.receive());
		// // System.out.println("response: \n" + response);
		// client.close();
		// }
		//
		// System.out.println("聊天记录：");
		// for (int i = 0; i < 4; i++) {
		// String response = client.talk("RECORD\n" + "1\n");
		// System.out.println((i + 1) + " - " + response);
		// }

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				WeChatFrame f = new LogonFrame();
				f.pack();
				f.setScreenCenter();
				f.setVisible(true);
			}
		});
	}

    private static UserInfo onlineUser;

    public static void setOnlineUser(UserInfo userInfo) {
        onlineUser = userInfo;
    }
    
    public static UserInfo getOnlineUser(){
        return onlineUser;
    }

}
