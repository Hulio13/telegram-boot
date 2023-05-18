package hulio13.telegramBoot.inputHandlers.wrappers;

import hulio13.telegramBoot.inputHandlers.InputHandler;
import hulio13.telegramBoot.inputHandlers.MessageContainer;
import hulio13.telegramBoot.inputHandlers.wrappers.abstraction.InputHandlerWrapper;
import hulio13.telegramBoot.tgUserProperties.TgUserProperties;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LoggerInputHandlerWrapper extends InputHandlerWrapper {
    private static final Logger logger = LoggerFactory.getLogger("InputHandlerLogger");

    public LoggerInputHandlerWrapper(InputHandler inputHandler) {
        super(inputHandler);
    }

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public void processInput(String input, TgUserProperties properties) {
        logger.debug(getInputHandler().getId() + "'s processInput method called by user with " + properties.telegramId
                + " telegram id.");
        try {
            super.processInput(input, properties);
        } catch (Exception e) {
            logger.error("Error msg: " + e.getMessage() + ". ");
            logger.debug("Stacktrace: " + ExceptionUtils.getStackTrace(e));
            throw e;
        }

    }

    @Override
    public MessageContainer getMessageForUser() {
        try {
            return super.getMessageForUser();
        } catch (Exception e) {
            logger.error("Error msg: " + e.getMessage() + ". ");
            logger.debug("Stacktrace: " + ExceptionUtils.getStackTrace(e));
            throw e;
        }
    }
}
