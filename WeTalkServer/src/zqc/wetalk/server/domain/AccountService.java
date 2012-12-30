package zqc.wetalk.server.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zqc.wetalk.LogonInfo;
import zqc.wetalk.RegisterInfo;
import zqc.wetalk.UserInfo;
import zqc.wetalk.UserInfoList;
import zqc.wetalk.exceptions.ApplicationException;
import zqc.wetalk.server.data.UserFinder;
import zqc.wetalk.server.data.UserGateway;

public class AccountService {

	private final Map<Integer, UserGateway> onlineUsers = new HashMap<>();

	private AccountService() {
	}

	private static AccountService instance;

	public static AccountService getInstance() {
		if (instance == null)
			instance = new AccountService();
		return instance;
	}

	public void registerUser(RegisterInfo registerInfo) {

		if (!registerInfo.getPassword().equals(registerInfo.getPassword2()))
			throw new ApplicationException("两次密码不匹配");

		if (userNameExists(registerInfo.getUserName()))
			throw new ApplicationException("用户登录名已存在。");

		new UserGateway(registerInfo).insert();
	}

	private boolean userNameExists(String userName) {

		UserFinder finder = new UserFinder();
		UserGateway user = finder.findByUserName(userName);
		return (user != null);
	}

	public UserGateway logOn(LogonInfo logonInfo) {
		if (logonInfo.getUserName().trim().length() == 0
				|| logonInfo.getPassword().trim().length() == 0) {
			throw new ApplicationException("用户名或密码为空");
		}
		UserFinder finder = new UserFinder();
		UserGateway user = finder.findByUserName(logonInfo.getUserName());
		
		if (user != null && !logonInfo.getPassword().equals(user.getPassword())) {
			throw new ApplicationException("密码错误");
		}

		onlineUsers.put(user.getID(), user);

		return user;
	}

	public void logOut(Integer id) {
		UserGateway user = onlineUsers.get(id);
		if (user == null)
			throw new ApplicationException("User hasn't logged on yet.");
		onlineUsers.remove(user);
	}
	
	public List<UserGateway> onlineUsers(){
		List<UserGateway> result = new ArrayList<>(onlineUsers.values());
		return Collections.unmodifiableList(result);
	}

	public UserInfoList getOnlineUserInfoList() {

		UserInfoList result = new UserInfoList();
		for (UserGateway user : onlineUsers.values()) {
			result.add(new UserInfo(user.getID(), user.getUserName(), user
					.getNickName()));
		}

		return result;
	}
}
