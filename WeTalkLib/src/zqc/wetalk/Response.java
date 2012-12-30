package zqc.wetalk;

public class Response {
	
	public static final String SUCCESS = "SUCCESS";
	public static final String FAIL = "FAIL";
	
	private String header;
	private String content;
	
	public Response(String header, String content){
		this.header = header;
		this.content = content;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
