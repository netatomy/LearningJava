package zqc.wetalk;

public class RegisterRequest extends Request {
	
	public RegisterRequest(RegisterInfo reg) {
		super("REGISTER");
		setContent(reg.toMessage());
	}
	
	@Override
	public String toString() {
		
		return "";//String.format("%S\n%s\n, getHeader(), ;
	}
}
