package zqc.wetalk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class UserInfoList {
	private List<UserInfo> users;
	
	public UserInfoList(){
		users = new ArrayList<>();
	}
	
	public UserInfoList(List<UserInfo> users){
		this.users = users;
	}
	
	public int count(){
		return users.size();
	}
	
	public List<UserInfo> getUserInfoList(){
		return Collections.unmodifiableList(users);
	}
	
	public void add(UserInfo userInfo){
		users.add(userInfo);
	}

	public String toMessage() {
		
		StringBuilder result  = new StringBuilder();
		for (UserInfo userInfo : users)
			result.append(userInfo.toMessage()).append(",");
		
		result.deleteCharAt(result.length() - 1);
		
		return result.toString();
	}
	
	public static UserInfoList parse(String userListStr){
		UserInfoList result = new UserInfoList();
		
		String[] userInfoStrArray = userListStr.split("\\,");
		for (String userInfoStr : userInfoStrArray){
			result.add(UserInfo.parse(userInfoStr));
		}
		
		return result;
	}
}
