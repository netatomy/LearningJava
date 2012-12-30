package zqc.wetalk.exceptions;

public class ApplicationException extends RuntimeException {

	public ApplicationException() {
	}

	public ApplicationException(String message) {
		super(message);
	}

	public ApplicationException(Throwable cause) {
		super(cause);
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStrackTrace) {
		super(message, cause, enableSuppression, writableStrackTrace);
	}

}
