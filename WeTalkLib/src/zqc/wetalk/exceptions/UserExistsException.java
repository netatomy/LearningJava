package zqc.wetalk.exceptions;

public class UserExistsException extends ApplicationException {

	public UserExistsException() {
	}

	public UserExistsException(String message) {
		super(message);
	}

	public UserExistsException(Throwable cause) {
		super(cause);
	}

	public UserExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserExistsException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStrackTrace) {
		super(message, cause, enableSuppression, writableStrackTrace);
	}
}
