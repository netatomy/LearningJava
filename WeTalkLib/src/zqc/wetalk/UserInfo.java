package zqc.wetalk;

public class UserInfo {

	private Integer id;
	private String userName;
	private String nickName;

	public UserInfo(Integer id, String userName, String nickName) {
		this.id = id;
		this.userName = userName;
		this.nickName = nickName;
	}

	public Integer getID() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public String getNickName() {
		return nickName;
	}
	
	public String toMessage(){
		return String.format("%d|%s|%s", id, userName, nickName);
	}
	
	public static UserInfo parse(String s){
		String[] parts = s.split("\\|");
		return new UserInfo(Integer.valueOf(parts[0]), parts[1], parts[2]);
	}
}
