package hulio13.telegramBoot.inputHandlers;

import hulio13.telegramBoot.tgUserProperties.TgUserProperties;

public interface InputHandler {
    String getId();

    default boolean HasNonLocalizableText() {
        return false;
    }

    void processInput(String input, TgUserProperties properties);

    MessageContainer getMessageForUser();
}