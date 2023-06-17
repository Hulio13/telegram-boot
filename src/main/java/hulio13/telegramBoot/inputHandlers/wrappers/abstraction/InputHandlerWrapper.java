package hulio13.telegramBoot.inputHandlers.wrappers.abstraction;


import hulio13.telegramBoot.inputHandlers.InputHandler;
import hulio13.telegramBoot.inputHandlers.MessageContainer;
import hulio13.telegramBoot.tgUserProperties.TgUserProperties;

public abstract class InputHandlerWrapper implements InputHandler {
    private final InputHandler inputHandler;

    public InputHandlerWrapper(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    @Override
    public String getId() {
        return inputHandler.getId();
    }

    @Override
    public void processInput(String input, TgUserProperties properties) {
        inputHandler.processInput(input, properties);
    }

    @Override
    public MessageContainer getMessageForUser(TgUserProperties properties) {
        return inputHandler.getMessageForUser(properties);
    }

    protected InputHandler getInputHandler() {
        return inputHandler;
    }
}
