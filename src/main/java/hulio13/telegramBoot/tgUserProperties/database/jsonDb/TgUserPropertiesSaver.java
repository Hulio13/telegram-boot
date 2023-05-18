package hulio13.telegramBoot.tgUserProperties.database.jsonDb;

import hulio13.telegramBoot.data.Repository;
import hulio13.telegramBoot.data.json.io.JsonSaver;
import hulio13.telegramBoot.tgUserProperties.TgUserProperties;

import java.io.IOException;

public final class TgUserPropertiesSaver extends JsonSaver<TgUserProperties> {
    public TgUserPropertiesSaver(String pathToFolder, Repository<TgUserProperties> jsonRepository, TgUserPropertiesSerialization serializationProvider) {
        super(pathToFolder, jsonRepository, serializationProvider);
    }

    @Override
    public void saveAll() {
        repository.forEach(tgUserProperties -> {
            try {
                save(tgUserProperties, tgUserProperties.telegramId);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
