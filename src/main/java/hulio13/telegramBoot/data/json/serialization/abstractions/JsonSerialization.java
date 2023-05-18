package hulio13.telegramBoot.data.json.serialization.abstractions;

import hulio13.telegramBoot.data.json.serialization.exceptions.JsonReadException;

public interface JsonSerialization<T> {
    String serialize(T object);

    T deserialize(String json) throws JsonReadException;
}
