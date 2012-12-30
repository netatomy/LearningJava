package zqc.wechat.server.data;

import java.util.HashMap;
import java.util.Map;

import zqc.wetalk.UserInfo;


public class Registry {
	
	private Map<Integer, UserInfo> users = new HashMap<>();
	
	private static Registry instance;
	
	private Registry() {
	}
	
	public static Registry getInstance(){
		if (instance == null)
			instance = new Registry();
		
		return instance;
	}

	public void addUser(UserInfo user){
		users.put(user.getID(), user);
	}

	public UserInfo getUser(Integer id) {
		return users.get(id);
	}
}
