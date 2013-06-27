package zqc.wetalk.exceptions;

public class PasswordNotMatchException extends ApplicationException {

	public PasswordNotMatchException() {
	}

	public PasswordNotMatchException(String message) {
		super(message);
	}

	public PasswordNotMatchException(Throwable cause) {
		super(cause);
	}

	public PasswordNotMatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public PasswordNotMatchException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStrackTrace) {
		super(message, cause, enableSuppression, writableStrackTrace);
	}

}
