package dev.mzcy.data.json.manager;

import dev.mzcy.data.json.exception.JsonException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Utility class for saving objects as JSON files.
 * This class provides methods to save an object to a specified file path or File object using the JsonManager.
 */
public class JsonSaver {

    /**
     * Saves the given data object to a JSON file at the specified path.
     *
     * @param data The object to be saved as JSON.
     * @param path The path where the JSON file will be saved.
     * @throws JsonException If an error occurs during saving.
     */
    public static void save(Object data, Path path) throws JsonException {
        try {
            File file = path.toFile();
            file.getParentFile().mkdirs(); // ensure directory exists
            JsonManager.get().writeValue(file, data);
        } catch (IOException e) {
            throw new JsonException("Failed to save JSON file: " + path.toString(), e);
        }
    }

    /**
     * Saves the given data object to a JSON file at the specified File object.
     *
     * @param data The object to be saved as JSON.
     * @param file The File object where the JSON file will be saved.
     * @throws JsonException If an error occurs during saving.
     */
    public static void save(Object data, File file) throws JsonException {
        try {
            file.getParentFile().mkdirs(); // ensure directory exists
            JsonManager.get().writeValue(file, data);
        } catch (IOException e) {
            throw new JsonException("Failed to save JSON file: " + file.getPath(), e);
        }
    }
}