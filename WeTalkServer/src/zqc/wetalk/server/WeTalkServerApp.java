package zqc.wetalk.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import zqc.wetalk.LogonInfo;
import zqc.wetalk.MessageType;
import zqc.wetalk.RegisterInfo;
import zqc.wetalk.server.data.UserGateway;
import zqc.wetalk.server.domain.AccountService;
import zqc.wetalk.server.net.Client;
import zqc.wetalk.server.net.ServerListener;
import zqc.wetalk.server.net.ServerHandler;

public class WeTalkServerApp {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args){

		ServerHandler serverHandler = new ServerHandler();
		serverHandler.start();
		System.out.println("Server started.");
	}

	
	static void test() throws IOException {
		ServerSocket server = new ServerSocket(6600);

		while (true) {
			Socket s = server.accept();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);

			// 1. 验证客户端
			String identity = in.readLine();
			if (!"wetalk".equalsIgnoreCase(identity)) {
				System.out.println("ERROR: Invalid client");
				out.println("ERROR: Invalid client");
				s.close();
				continue;
			}
			// 2. 建立连接
			out.println("wetalk");
			System.out.println("Connect successfully.");
			// 3. 获取客户端请求
			while (true) {
				String messageType = in.readLine();
				if ("REGISTER".equals(messageType)) {
					String userInfoStr = in.readLine();
					RegisterInfo userInfo = RegisterInfo.parse(userInfoStr);
					AccountService.getInstance().registerUser(userInfo);
					out.println("OK");
				} else if ("LOGON".equals(messageType)) {
					String logonInfoStr = in.readLine();
					LogonInfo logonInfo = LogonInfo.parse(logonInfoStr);
					UserGateway user = AccountService.getInstance().logOn(
							logonInfo);
					out.println("OK");
					out.println(user.toUserInfo().toMessage());
				} else if ("ONLINEUSERS".equals(messageType)) {
					Integer userId = Integer.valueOf(in.readLine());
					out.println("OK");
					out.println(AccountService.getInstance()
							.getOnlineUserInfoList().toMessage());
				} else if ("LOGOUT".equals(messageType)) {
					Integer userId = Integer.valueOf(in.readLine());
					AccountService.getInstance().logOut(userId);
					out.println("OK");
					out.println("User logged out.");
				} else {
					out.println("ERROR");
					out.println("Invalid Message.");
				}

				try {
					s.sendUrgentData(0xFF);
				} catch (SocketException ex) {
					break;
				}
			}
			s.close();
		}
	}
}
