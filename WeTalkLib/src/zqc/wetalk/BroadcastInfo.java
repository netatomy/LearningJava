package zqc.wetalk;

public class BroadcastInfo {

	private Integer userID;
	private String content;

	public BroadcastInfo(Integer userId, String content) {
		super();
		this.userID = userId;
		this.content = content;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userId) {
		this.userID = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String toMessage() {
		return String.format("%d|%s", userID, content);
	}

	public static BroadcastInfo parse(String str) {
		String[] parts = str.split("\\|");
		return new BroadcastInfo(Integer.valueOf(parts[0]), parts[1]);
	}

}
