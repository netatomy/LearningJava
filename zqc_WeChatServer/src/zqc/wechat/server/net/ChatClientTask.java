package zqc.wechat.server.net;

import java.io.IOException;
import java.util.Scanner;

import zqc.wechat.server.data.UserFinder;
import zqc.wechat.server.domain.AccountService;
import zqc.wechat.server.domain.User;
import zqc.wetalk.LogonInfo;
import zqc.wetalk.RegisterInfo;
import zqc.wetalk.UserInfo;
import zqc.wetalk.exceptions.ApplicationException;

public class ChatClientTask implements Runnable {

	private ChatClient client;

	public ChatClientTask(ChatClient cc) {

		client = cc;
	}

	@Override
	public void run() {

		try {
			Scanner scanner = new Scanner(client.receive());
			String head = scanner.nextLine();
			System.out.println(head + ":");
			switch (head) {
			case "REGISTER":
				RegisterInfo regInfo = RegisterInfo.parse(scanner.nextLine());
				System.out.println(regInfo.toMessage());
				String regResponse = null;
				try {
					AccountService.getInstance().registerUser(regInfo);
					regResponse = "SUCCESS\n" + "用户注册成功\n";
				} catch (ApplicationException ex) {
					regResponse = "FAIL\n" + ex.getMessage() + "\n";
				}
				client.send(regResponse);
				break;
			case "LOGON":
				LogonInfo logonInfo = LogonInfo.parse(scanner.next());
				System.out.println(logonInfo.toMessage());
				String logonResponse = null;
				try{
					UserInfo user = AccountService.getInstance().logOn(logonInfo);
					logonResponse = "SUCCESS\n" + user.toMessage() + "\n";
				}catch (ApplicationException ex){
					logonResponse = "FAIL\n" + ex.getMessage() + "\n";
				}
				client.send(logonResponse);
				break;
			case "LOGOUT":
				Integer logoutUserID = Integer.valueOf(scanner.nextLine());
				System.out.println(logoutUserID);
				UserInfo userToLogout = AccountService.getInstance().findOnlineUser(logoutUserID);
				try{
					AccountService.getInstance().logOut(userToLogout);
				}catch (ApplicationException ex){
					
				}
				String logoutResponse = "SUCCESS\n" + logoutUserID + "\n";
				client.send(logoutResponse);
				break;
			case "TALK":
				Integer userID2 = Integer.valueOf(scanner.nextLine());
				UserInfo userWhosChatting = AccountService.getInstance().findOnlineUser(userID2);
				String chatRecord = scanner.nextLine();
				System.out.println(chatRecord);
				AccountService.getInstance().addUserChatRecord(userID2, chatRecord);
				client.send("SUCCESS\n" + userWhosChatting.getID() + "\n");
				break;
			case "RECORD":
				Integer userID3 = Integer.valueOf(scanner.nextLine());
				UserInfo user3 = AccountService.getInstance().findOnlineUser(userID3);
				String chatHistory = AccountService.getInstance().getUserChatRecord(userID3);
				System.out.println(chatHistory);
				client.send("SUCCESS\n" + chatHistory);
				break;
				
			case "ONLINEUSERS":
			    Integer userID4 = Integer.valueOf(scanner.nextLine());
			    UserInfo user4 = AccountService.getInstance().findOnlineUser(userID4);
			    String onlineUsersMessage = AccountService.getInstance().onlineUsersMessage();
			    System.out.println(onlineUsersMessage);
			    client.send("SUCCESS\n" + onlineUsersMessage);
			    break;
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		// finally {
		// try {
		// client.close();
		// }
		// catch (IOException e) {
		// e.printStackTrace();
		// }
		// }

	}

}
