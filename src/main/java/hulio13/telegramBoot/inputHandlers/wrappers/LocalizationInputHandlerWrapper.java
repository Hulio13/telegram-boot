package hulio13.telegramBoot.inputHandlers.wrappers;


import hulio13.telegramBoot.entity.Result;
import hulio13.telegramBoot.exceptions.NotFoundException;
import hulio13.telegramBoot.inputHandlers.InputHandler;
import hulio13.telegramBoot.inputHandlers.MessageContainer;
import hulio13.telegramBoot.inputHandlers.wrappers.abstraction.InputHandlerWrapper;
import hulio13.telegramBoot.localization.LocalizationService;
import hulio13.telegramBoot.localization.VariablesInPhraseInserter;
import hulio13.telegramBoot.tgUserProperties.TgUserProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class LocalizationInputHandlerWrapper extends InputHandlerWrapper {
    private static final Logger logger = LoggerFactory.getLogger(LocalizationInputHandlerWrapper.class);
    private final Map<String, Object> vars;
    private final String languageTag;

    public LocalizationInputHandlerWrapper(InputHandler inputHandler, TgUserProperties properties) {
        super(inputHandler);
        this.vars = properties.getPayload();
        this.languageTag = properties.getLocale();
    }

    @Override
    public void processInput(String input, TgUserProperties properties) {
        Result<String> resultButton = LocalizationService.getButtonByValue(
                properties.getLocale(),
                input
        );

        if (resultButton.isSuccess()) {
            input = resultButton.object();
        }

        super.processInput(input, properties);
    }

    @Override
    public MessageContainer getMessageForUser() {
        MessageContainer messageContainer = super.getMessageForUser();
        String langTag = languageTag;

        Result<String> resultMsg = LocalizationService.getPhraseById(
                langTag,
                messageContainer.getMsg());

        if (!resultMsg.isSuccess() && !getInputHandler().HasNonLocalizableText()) {
            throw new NotFoundException(
                    resultMsg.error() + ": " + resultMsg.message()
            );
        }

        String message;

        if (resultMsg.object() == null &&
                getInputHandler().HasNonLocalizableText()) {
            message = messageContainer.getMsg();
        } else message = resultMsg.object();

        message = VariablesInPhraseInserter.insert(message, vars);

        ArrayList<ArrayList<String>> keyboard = messageContainer.getStringKeyboard();

        for (int i = 0; i < keyboard.size(); i++) {
            List<String> row = keyboard.get(i);

            for (int j = 0; j < row.size(); j++) {
                Result<String> resultBtn = LocalizationService.getButtonById(
                        langTag,
                        row.get(j));

                if (!resultBtn.isSuccess() && !getInputHandler().HasNonLocalizableText()) {
                    throw new NotFoundException(resultBtn.error() + ": "
                            + resultBtn.message());
                }

                if (resultBtn.object() == null &&
                        getInputHandler().HasNonLocalizableText()) {
                    String button = messageContainer
                            .getStringKeyboard()
                            .get(i)
                            .get(j);
                    row.set(j, button);
                } else row.set(j, resultBtn.object());
            }
        }

        MessageContainer localizedMessageContainer = new MessageContainer(
                message,
                keyboard
        );

        return localizedMessageContainer;
    }
}
