package hulio13.telegramBoot.tgUserProperties.database.jsonDb;

import hulio13.telegramBoot.data.json.DumpingScheduler;
import hulio13.telegramBoot.data.json.io.JsonRepositoryLoader;
import hulio13.telegramBoot.data.json.io.JsonSaver;
import hulio13.telegramBoot.tgUserProperties.TgUserProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public final class JsonTgUserPropertiesRepositoryInitializer {
    private static Logger logger = LoggerFactory.getLogger(JsonTgUserPropertiesRepositoryInitializer.class);

    public static void initialize(String dbFolder, int delayToSaveInDb, TimeUnit unit){
        List<TgUserProperties> tgUserProperties = new JsonRepositoryLoader<TgUserProperties>().load(dbFolder, new TgUserPropertiesSerialization());

        var repository =
                JsonTgUserPropertiesRepository.getInstance(tgUserProperties);

        JsonSaver<TgUserProperties> saver = new TgUserPropertiesSaver(
                dbFolder,
                repository,
                new TgUserPropertiesSerialization()
        );

        DumpingScheduler.start(saver, delayToSaveInDb, unit);
    }
}
