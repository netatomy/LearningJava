package zqc.wetalk.server.domain;

import java.sql.SQLException;

import zqc.wetalk.UserInfo;
import zqc.wetalk.exceptions.ApplicationException;
import zqc.wetalk.server.data.DataAccess;

public class User {

	private int id;
	private String userName;
	private String password;
	private String nickName;
	
	public User() {
	}
	
//	public User(String userName, String password, String nickName){
//		this(-1, userName, password, nickName);
//	}
//	
//	public User(int id, String userName, String password, String nickName){
//		this.id = id;
//		this.userName = userName;
//		this.password = password;
//		this.nickName = nickName;
//	}
//
//	public User(UserInfo userInfo) {
//		this(-1, userInfo.getUserName(), userInfo.getPassword(), userInfo.getNickName());
//	}
//
//	public int getID() {
//		return id;
//	}
//
//	public void setID(int id) {
//		this.id = id;
//	}
//
//	public String getUserName() {
//		return userName;
//	}
//
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public String getNickName() {
//		return nickName;
//	}
//
//	public void setNickName(String nickName) {
//		this.nickName = nickName;
//	}
//	
//	public void insert(){
//		
//		DataAccess db = DataAccess.getInstance();
//		try{
//			this.id = db.insertUser(this);
//			Registry.getInstance().addUser(this);
//		} catch (SQLException ex){
//			throw new ApplicationException("用户添加失败", ex);
//		} 
//	}

//	public UserInfo toUserInfo() {
//		return new UserInfo(userName, password, password, nickName);
//	}
}
