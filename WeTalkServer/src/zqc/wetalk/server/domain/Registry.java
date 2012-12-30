package zqc.wetalk.server.domain;

import java.util.HashMap;
import java.util.Map;

import zqc.wetalk.server.data.UserGateway;

public class Registry {
	
	private Map<String, UserGateway> users = new HashMap<>();
	
	private static Registry instance;
	
	private Registry() {
	}
	
	public static Registry getInstance(){
		if (instance == null)
			instance = new Registry();
		
		return instance;
	}

	public void addUser(UserGateway user){
		users.put(user.getUserName(), user);
	}

	public UserGateway getUser(String userName) {
		return users.get(userName);
	}
}
