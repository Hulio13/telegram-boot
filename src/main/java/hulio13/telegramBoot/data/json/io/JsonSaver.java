package hulio13.telegramBoot.data.json.io;

import hulio13.telegramBoot.data.Repository;
import hulio13.telegramBoot.data.json.providers.JsonProvider;
import hulio13.telegramBoot.data.json.serialization.abstractions.JsonSerialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class JsonSaver<T> {
    protected final JsonProvider provider;
    protected final Repository<T> repository;
    protected final JsonSerialization serializationProvider;
    static protected final Logger logger = LoggerFactory.getLogger(JsonSaver.class);

    public JsonSaver(String pathToFolder, Repository<T> jsonRepository,
                     JsonSerialization serializationProvider) {
        this.provider = new JsonProvider(pathToFolder);
        this.repository = jsonRepository;
        this.serializationProvider = serializationProvider;
    }

    public abstract void saveAll();

    public void save(T obj, String fileName) throws IOException {
        try {
            provider.writeJsonWithName(fileName, serializationProvider.serialize(obj));
        } catch (IOException e) {
            logger.warn(String.format("Unable to save to file, maybe don't have permissions? File name: %s",
                    fileName));
            throw new IOException(e);
        }
    }
}
