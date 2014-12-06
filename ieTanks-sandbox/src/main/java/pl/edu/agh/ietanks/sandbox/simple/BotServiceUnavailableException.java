package pl.edu.agh.ietanks.sandbox.simple;

public class BotServiceUnavailableException extends RuntimeException {

    public BotServiceUnavailableException() {
        super();
    }

    public BotServiceUnavailableException(String message) {
        super(message);
    }

    public BotServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public BotServiceUnavailableException(Throwable cause) {
        super(cause);
    }

    protected BotServiceUnavailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
