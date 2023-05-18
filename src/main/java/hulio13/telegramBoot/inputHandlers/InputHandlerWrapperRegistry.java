package hulio13.telegramBoot.inputHandlers;

import hulio13.telegramBoot.inputHandlers.builders.WrappedInputHandlerBuilder;
import hulio13.telegramBoot.inputHandlers.wrappers.abstraction.InputHandlerWrapper;
import hulio13.telegramBoot.tgUserProperties.TgUserProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class InputHandlerWrapperRegistry {
    private static final Logger logger = LoggerFactory.getLogger(InputHandlerWrapperRegistry.class);

    private static final ArrayList<Constructor<?>> wrapperConstructors = new ArrayList<>();

    /**
     * You can only use it with wrapper with one constructor with one {@link InputHandler}
     * parameter or with two {@link InputHandler} and {@link TgUserProperties} parameters.
     */
    public static <T extends InputHandlerWrapper> void addWrapper(Class<T> clazz) {
        wrapperConstructors.add(getConstructor(clazz));
    }

    private static <T extends InputHandlerWrapper> Constructor<?> getConstructor(Class<T> clazz) {
        Constructor<?>[] constructors = clazz.getConstructors();

        if (constructors.length != 1) {
            throw new RuntimeException();
        }

        Constructor<?> constructor = constructors[0];

        Class<?>[] parameterTypes = constructor.getParameterTypes();

        if (parameterTypes.length == 1) {
            if (parameterTypes[0].equals(InputHandler.class)) {
                return constructor;
            }
        }

        if (parameterTypes.length == 2) {
            if (parameterTypes[0].equals(InputHandler.class) &&
                    parameterTypes[1].equals(TgUserProperties.class)) {
                return constructor;
            }
        }

        logger.error("Invalid constructor of class.");
        throw new RuntimeException("Invalid constructor of class.");
    }

    public static WrappedInputHandlerBuilder wrapWithBuilder(WrappedInputHandlerBuilder builder,
                                                             TgUserProperties properties) {
        for (var constructor : wrapperConstructors) {
            if (constructor.getParameterCount() == 1) {
                builder.wrapWith(() -> {
                    try {
                        InputHandlerWrapper inputHandlerWrapper =
                                (InputHandlerWrapper) constructor.newInstance(builder.getHandler());

                        logWrappedWith(constructor.getName());

                        return inputHandlerWrapper;
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
            } else if (constructor.getParameterCount() == 2) {
                builder.wrapWith(() -> {
                    try {
                        InputHandlerWrapper inputHandlerWrapper =
                                (InputHandlerWrapper) constructor.newInstance(builder.getHandler(), properties);

                        logWrappedWith(constructor.getName());

                        return inputHandlerWrapper;
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }

        return builder;
    }

    public static InputHandler getWrappedInputHandler(InputHandler handler,
                                                      TgUserProperties properties) {
        try {
            for (var constructor : wrapperConstructors) {
                if (constructor.getParameterCount() == 1) {
                    handler = (InputHandlerWrapper) constructor.newInstance(handler);
                    logWrappedWith(constructor.getName());
                } else if (constructor.getParameterCount() == 2) {
                    handler = (InputHandlerWrapper) constructor.newInstance(handler, properties);
                    logWrappedWith(constructor.getName());
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        return handler;
    }

    private static void logWrappedWith(String name) {
        logger.trace("Wrapped with '" + name + "' class");
    }
}
