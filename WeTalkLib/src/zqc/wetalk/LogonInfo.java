package zqc.wetalk;

public class LogonInfo {

	private String userName;
	private String password;
	
	public LogonInfo(String userName, String password) {
		this.userName = userName;
		this.password = password;
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

	public static LogonInfo parse(String message) {
		String[] parts = message.split("\\|");
		return new LogonInfo(parts[0], parts[1]);
	}

	public String toMessage() {
		return String.format("%s|%s", userName, password);
	}

}
