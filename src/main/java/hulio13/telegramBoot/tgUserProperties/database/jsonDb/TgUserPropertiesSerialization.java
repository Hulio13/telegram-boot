package hulio13.telegramBoot.tgUserProperties.database.jsonDb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hulio13.telegramBoot.data.json.serialization.abstractions.JsonSerialization;
import hulio13.telegramBoot.data.json.serialization.exceptions.JsonReadException;
import hulio13.telegramBoot.tgUserProperties.TgUserProperties;

public final class TgUserPropertiesSerialization implements JsonSerialization<TgUserProperties> {
    private final ObjectMapper objectMapper;

    public TgUserPropertiesSerialization() {
        this.objectMapper = new ObjectMapper();
    }

    public String serialize(TgUserProperties info) {
        try {
            String json = objectMapper.writeValueAsString(info);
            return json;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public TgUserProperties deserialize(String json) throws JsonReadException {
        TgUserProperties info = null;
        try {
            info = objectMapper.readValue(json, TgUserProperties.class);
            return info;
        } catch (JsonProcessingException e) {
            throw new JsonReadException(e);
        }
    }
}
