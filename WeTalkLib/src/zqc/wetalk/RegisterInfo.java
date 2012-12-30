package zqc.wetalk;

public class RegisterInfo {
	
	private String userName;
	private String password;
	private String password2;
	private String nickName;
	
	public RegisterInfo() {
	}
	
	public RegisterInfo(String userName, String password, String password2, String nickName){
		this.userName = userName;
		this.password = password;
		this.password2 = password2;
		this.nickName = nickName;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public static RegisterInfo parse(String userInfoStr) {
		String[] parts = userInfoStr.split("\\|");
		return new RegisterInfo(parts[0], parts[1], parts[2], parts[3]);
	}

	public String toMessage() {
		
		return String.format("%s|%s|%s|%s", userName, password, password2, nickName);
	}

}
