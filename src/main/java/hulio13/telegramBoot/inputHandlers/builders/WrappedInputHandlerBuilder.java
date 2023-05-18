package hulio13.telegramBoot.inputHandlers.builders;

import hulio13.telegramBoot.inputHandlers.InputHandler;
import hulio13.telegramBoot.inputHandlers.wrappers.LocalizationInputHandlerWrapper;
import hulio13.telegramBoot.inputHandlers.wrappers.LoggerInputHandlerWrapper;
import hulio13.telegramBoot.inputHandlers.wrappers.abstraction.InputHandlerWrapper;
import hulio13.telegramBoot.tgUserProperties.TgUserProperties;

import java.util.function.Supplier;

public class WrappedInputHandlerBuilder {
    private InputHandler handler;
    private final TgUserProperties properties;

    public WrappedInputHandlerBuilder(InputHandler handler, TgUserProperties properties) {
        this.handler = handler;
        this.properties = properties;
    }

    public void wrapWithLocalization() {
        handler = new LocalizationInputHandlerWrapper(handler, properties);
    }

    public void wrapWithLogger() {
        handler = new LoggerInputHandlerWrapper(handler);
    }

    /**
     * Wraps input handler with your own wrapper.
     * Just call {@link WrappedInputHandlerBuilder#getHandler()} when you need it
     * in the
     * {@link InputHandlerWrapper#InputHandlerWrapper(InputHandler)}
     * constructor.
     * Example:
     * <pre>
     *     builder.wrapWithWrapper(() ->
     *                 new InputHandlerWrapper(builder.getHandler())
     *                 );
     * </pre>
     */
    public void wrapWith(Supplier<InputHandlerWrapper> supplier) {
        handler = supplier.get();
    }

    public InputHandler getHandler() {
        return handler;
    }
}
