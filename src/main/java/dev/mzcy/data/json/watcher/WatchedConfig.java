package dev.mzcy.data.json.watcher;

import dev.mzcy.data.json.exception.JsonException;
import dev.mzcy.data.json.manager.JsonLoader;

import java.io.File;
import java.util.function.Consumer;

/**
 * WatchedConfig is a utility class that allows you to load a JSON configuration file
 * and automatically reload it when the file changes. It uses a separate thread to watch
 * for changes and invokes a callback when the configuration is reloaded.
 *
 * @param <T> The type of the configuration object.
 */
public class WatchedConfig<T> {

    private final File file;
    private final Class<T> type;
    private final Consumer<T> onReloadCallback;

    private volatile T configInstance;
    private Thread watcherThread;
    private JsonWatcher<T> watcher;

    /**
     * Constructs a WatchedConfig instance.
     *
     * @param file The JSON configuration file to watch.
     * @param type The class type of the configuration object.
     * @param onReloadCallback A callback that is invoked when the configuration is reloaded.
     * @throws JsonException If there is an error loading the JSON file.
     */
    public WatchedConfig(File file, Class<T> type, Consumer<T> onReloadCallback) throws JsonException {
        this.file = file;
        this.type = type;
        this.onReloadCallback = onReloadCallback;

        // Initial load
        this.configInstance = JsonLoader.load(file, type);
    }

    /**
     * Returns the current configuration instance.
     *
     * @return The loaded configuration object.
     */
    public T getConfig() {
        return configInstance;
    }

    /**
     * Starts watching the configuration file for changes.
     * If the watcher is already running, this method does nothing.
     */
    public synchronized void startWatching() {
        if (watcherThread != null && watcherThread.isAlive()) {
            // Already watching
            return;
        }

        watcher = new JsonWatcher<>(file, type, reloadedConfig -> {
            this.configInstance = reloadedConfig;
            onReloadCallback.accept(reloadedConfig);
        });

        watcherThread = new Thread(watcher, "WatchedConfig-Watcher-" + file.getName());
        watcherThread.setDaemon(true); // Don't block JVM shutdown
        watcherThread.start();
    }

    /**
     * Stops watching the configuration file for changes.
     * If the watcher is not running, this method does nothing.
     */
    public synchronized void stopWatching() {
        if (watcher != null) {
            watcher.stop();
        }
        if (watcherThread != null) {
            watcherThread.interrupt();
            try {
                watcherThread.join(1000);
            } catch (InterruptedException ignored) {}
        }
        watcherThread = null;
        watcher = null;
    }
}