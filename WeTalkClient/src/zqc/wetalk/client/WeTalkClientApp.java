package zqc.wetalk.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import zqc.wetalk.LogonInfo;
import zqc.wetalk.RegisterInfo;
import zqc.wetalk.UserInfo;
import zqc.wetalk.UserInfoList;
import zqc.wetalk.exceptions.ApplicationException;

public class WeTalkClientApp {

	static Scanner stdIn = new Scanner(System.in);
	static BufferedReader in;
	static PrintWriter out;
	static Socket socket;

	/**
	 * @param args
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException,
			IOException {

		socket = new Socket("localhost", 6600);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);

		try {
			out.println("wetalk");
			String respond = in.readLine();
			if (!"wetalk".equalsIgnoreCase(respond)) {
				System.out.println(respond);
				throw new ApplicationException("Close");
			}

			System.out.println("Connect successfully, please logon firstly.");

			System.out.print("User Name: ");
			String userName = stdIn.nextLine();
			System.out.print("Password: ");
			String password = stdIn.nextLine();

			LogonInfo logonInfo = new LogonInfo(userName, password);
			out.println("LOGON");
			out.println(logonInfo.toMessage());
			respond = in.readLine();
			if (!"OK".equalsIgnoreCase(respond)) {
				String errorMsg = in.readLine();
				// System.out.println(err);
				throw new ApplicationException(in.readLine());
			}
			UserInfo userInfo = UserInfo.parse(in.readLine());
			System.out.println("You have been logged on successfully.");

			out.println("ONLINEUSERS");
			out.println(userInfo.getID());
			respond = in.readLine();
			if (!"OK".equalsIgnoreCase(respond)) {
				// System.out.println("Cannot get online users");
				throw new ApplicationException(in.readLine());
			}
			String onlineUsersMessage = in.readLine();
			UserInfoList onlineUserInfoList = UserInfoList
					.parse(onlineUsersMessage);
			System.out.println("Online users:");
			for (UserInfo each : onlineUserInfoList.getUserInfoList()) {
				System.out.println(each.toMessage());
			}

			out.println("LOGOUT");
			out.println(userInfo.getID());
			respond = in.readLine();
			if (!"OK".equalsIgnoreCase(respond)) {
				// System.out.println(in.readLine());
				throw new ApplicationException(in.readLine());
			}
			System.out.println("You have been logged out.");
		} catch (ApplicationException ex) {
			System.out.println(ex.getMessage());
			
		} finally {
//			socket.getInputStream().close();
//			socket.getOutputStream().close();
			socket.close();
		}
	}

	static void logon() throws IOException {
		LogonInfo logonInfo = new LogonInfo("Jane", "123456");
		out.println("LOGON");
		out.println(logonInfo.toMessage());
		System.out.println(in.readLine());
	}

	static void register() throws IOException {
		RegisterInfo userInfo = new RegisterInfo("Jane", "123456", "123456",
				"Patrick");
		out.println("REGISTER");
		out.println(userInfo.toMessage());

		System.out.println(in.readLine());
	}

}
