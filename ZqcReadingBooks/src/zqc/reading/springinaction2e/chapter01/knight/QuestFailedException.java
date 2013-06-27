package zqc.reading.springinaction2e.chapter01.knight;


public class QuestFailedException extends RuntimeException {

    public QuestFailedException() {

        super();
    }

    public QuestFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {

        super(message, cause, enableSuppression, writableStackTrace);
    }

    public QuestFailedException(String message, Throwable cause) {

        super(message, cause);
    }

    public QuestFailedException(String message) {

        super(message);
    }

    public QuestFailedException(Throwable cause) {

        super(cause);
    }

}
