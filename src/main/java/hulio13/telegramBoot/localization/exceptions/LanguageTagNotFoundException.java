package hulio13.telegramBoot.localization.exceptions;

public final class LanguageTagNotFoundException extends RuntimeException{
    public LanguageTagNotFoundException() {
    }

    public LanguageTagNotFoundException(String message) {
        super(message);
    }
}
