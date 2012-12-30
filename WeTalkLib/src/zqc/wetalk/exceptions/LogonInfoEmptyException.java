package zqc.wetalk.exceptions;

public class LogonInfoEmptyException extends ApplicationException {

	public LogonInfoEmptyException() {
		super();
	}

	public LogonInfoEmptyException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStrackTrace) {
		super(message, cause, enableSuppression, writableStrackTrace);
	}

	public LogonInfoEmptyException(String message, Throwable cause) {
		super(message, cause);
	}

	public LogonInfoEmptyException(String message) {
		super(message);
	}

	public LogonInfoEmptyException(Throwable cause) {
		super(cause);
	}

}
