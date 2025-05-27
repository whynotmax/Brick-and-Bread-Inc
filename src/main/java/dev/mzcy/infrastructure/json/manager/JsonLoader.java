package dev.mzcy.infrastructure.json.manager;

import dev.mzcy.bootstrap.IdleEmpireServer;
import dev.mzcy.infrastructure.json.exception.JsonException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Utility class for loading JSON files and deserializing them into Java objects.
 * Uses the JsonManager to handle the JSON parsing.
 */
public class JsonLoader {

    /**
     * Loads a JSON file and returns an instance of the specified type.
     *
     * @param file The JSON file to load.
     * @param type The class type to deserialize into.
     * @param <T>  The type of the instance.
     * @return An instance of the specified type.
     * @throws JsonException If there is an error reading the file or deserializing it.
     */
    public static <T> T load(File file, Class<T> type) throws JsonException {
        try {
            return JsonManager.get().readValue(file, type);
        } catch (IOException e) {
            throw new JsonException("Failed to load JSON file: " + file.getPath(), e);
        }
    }

    /**
     * Loads a JSON file from the specified path and returns an instance of the specified type.
     *
     * @param path The path to the JSON file.
     * @param type The class type to deserialize into.
     * @param <T>  The type of the instance.
     * @return An instance of the specified type.
     * @throws JsonException If there is an error reading the file or deserializing it.
     */
    public static <T> T load(Path path, Class<T> type) throws JsonException {
        return load(path.toFile(), type);
    }

    /**
     * Loads a JSON file and returns an instance of the specified type.
     * If the file does not exist, it returns the provided default instance.
     *
     * @param file            The JSON file to load.
     * @param type            The class type to deserialize into.
     * @param defaultInstance The default instance to return if the file does not exist.
     * @param <T>             The type of the instance.
     * @return An instance of the specified type or the default instance if the file does not exist.
     */
    public static <T> T loadOrDefault(File file, Class<T> type, T defaultInstance) {
        if (!file.exists()) return defaultInstance;
        try {
            return JsonManager.get().readValue(file, type);
        } catch (IOException e) {
            IdleEmpireServer.getInstance().getLogger().atError().setCause(e)
                    .log("Failed to load JSON file: {}, using default instance.", file.getPath());
            return defaultInstance;
        }
    }
}