package hulio13.telegramBoot;

import java.util.HashMap;
import java.util.Map;

public class ServiceRegistry {
    private static final Map<Class<?>, Object> services = new HashMap<>();

    public static <T> void addService(Class<T> type, T serviceImpl) {
        services.put(type, serviceImpl);
    }

    public static <T> T getService(Class<T> type) {
        return type.cast(services.get(type));
    }
}
