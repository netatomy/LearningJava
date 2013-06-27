package zqc.wetalk.server.data;

import java.util.HashMap;
import java.util.Map;


public class Registry {
	
	private Map<Integer, UserGateway> users = new HashMap<>();
	
	private static Registry instance;
	
	private Registry() {
	}
	
	public static Registry getInstance(){
		if (instance == null)
			instance = new Registry();
		
		return instance;
	}

	public void addUser(UserGateway user){
		users.put(user.getID(), user);
	}

	public UserGateway getUser(Integer id) {
		return users.get(id);
	}
}
