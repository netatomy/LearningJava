package zqc.wetalk.server.net;

import java.net.SocketException;

import zqc.wetalk.BroadcastInfo;
import zqc.wetalk.LogonInfo;
import zqc.wetalk.RegisterInfo;
import zqc.wetalk.exceptions.ApplicationException;
import zqc.wetalk.server.data.UserFinder;
import zqc.wetalk.server.data.UserGateway;
import zqc.wetalk.server.domain.AccountService;

public class ClientHandler {

	private Client client;
	private ServerHandler serverHandler;

	public ClientHandler(Client client, ServerHandler serverHandler) {
		super();
		this.client = client;
		this.serverHandler = serverHandler;
	}

	public void start() {
		while (true) {
			String messageType = client.readLine();
			if ("REGISTER".equals(messageType)) {
				String userInfoStr = client.readLine();
				RegisterInfo userInfo = RegisterInfo.parse(userInfoStr);
				AccountService.getInstance().registerUser(userInfo);
				client.writeLine("OK");
			} else if ("LOGON".equals(messageType)) {
				String logonInfoStr = client.readLine();
				LogonInfo logonInfo = LogonInfo.parse(logonInfoStr);
				UserGateway user = AccountService.getInstance()
						.logOn(logonInfo);
				if (user != null) {
					user.setClient(client);
					client.writeLine("OK");
					client.writeLine(user.toUserInfo().toMessage());
				} else {
					client.writeLine("ERROR");
					client.writeLine("User logged on failed.");
				}

			} else if ("ONLINEUSERS".equals(messageType)) {
				Integer userId = Integer.valueOf(client.readLine());
				client.writeLine("OK");
				client.writeLine(AccountService.getInstance()
						.getOnlineUserInfoList().toMessage());
			} else if ("LOGOUT".equals(messageType)) {
				Integer userId = Integer.valueOf(client.readLine());
				AccountService.getInstance().logOut(userId);
				client.writeLine("OK");
				client.writeLine("User logged client.");
			} else if ("BROADCAST".equals(messageType)) {
				String messageStr = client.readLine();
				BroadcastInfo broadcastInfo = BroadcastInfo.parse(messageStr);
				Integer userId = Integer.valueOf(broadcastInfo.getUserID());
				UserGateway user = new UserFinder().find(userId);
				if (!AccountService.getInstance().onlineUsers().contains(user)) {
					client.writeLine("ERROR");
					client.writeLine("Please logon.");
				} else {
					client.writeLine("OK");
					client.writeLine("Ready to broadcast");
					serverHandler.broadCast(broadcastInfo);
				}
			} else {
				client.writeLine("ERROR");
				client.writeLine("Invalid Message.");
			}

			try {
				client.checkConnection();
			} catch (ApplicationException ex) {
				break;
			}
		}
	}
}
