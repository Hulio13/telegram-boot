package hulio13.telegramBoot.exceptions;

public final class NotInitializedException extends RuntimeException {
    public NotInitializedException() {
    }

    public NotInitializedException(String message) {
        super(message);
    }
}
