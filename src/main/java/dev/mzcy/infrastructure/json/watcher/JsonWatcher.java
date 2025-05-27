package dev.mzcy.infrastructure.json.watcher;

import dev.mzcy.bootstrap.IdleEmpireServer;
import dev.mzcy.infrastructure.json.exception.JsonException;
import dev.mzcy.infrastructure.json.manager.JsonLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.function.Consumer;

/**
 * A utility class that watches a JSON file for changes and reloads it when modified.
 * It uses Java's WatchService to monitor the file system for changes.
 *
 * @param <T> The type of the JSON object to be loaded.
 */
public class JsonWatcher<T> implements Runnable {
    private final File file;
    private final Class<T> type;
    private final Consumer<T> onReload;
    private boolean running = true;

    /**
     * Constructs a JsonWatcher.
     *
     * @param file     The JSON file to watch.
     * @param type     The class type of the JSON object.
     * @param onReload A consumer that will be called with the reloaded object when the file changes.
     */
    public JsonWatcher(File file, Class<T> type, Consumer<T> onReload) {
        this.file = file;
        this.type = type;
        this.onReload = onReload;
    }

    /**
     * Stops this watcher.
     */
    public void stop() {
        this.running = false;
    }

    /**
     * Runs the watcher, blocking until the file is modified.
     */
    @Override
    public void run() {
        Path path = file.toPath();
        Path dir = path.getParent();

        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            dir.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            while (running) {
                WatchKey key = watchService.take(); // Blocking

                for (WatchEvent<?> event : key.pollEvents()) {
                    Path changed = dir.resolve((Path) event.context());

                    if (changed.endsWith(file.getName())) {
                        try {
                            T reloaded = JsonLoader.load(file, type);
                            onReload.accept(reloaded);
                        } catch (JsonException e) {
                            IdleEmpireServer.getInstance().getLogger().atError().setCause(e)
                                            .log("[JsonWatcher] Failed to reload JSON file {}", file.getName());
                            e.printStackTrace();
                        }
                    }
                }

                boolean valid = key.reset();
                if (!valid) break;
            }
        } catch (IOException | InterruptedException e) {
            if (running) {
                IdleEmpireServer.getInstance().getLogger().atError().setCause(e)
                        .log("[JsonWatcher] Error while watching JSON file {}", file.getName());
                e.printStackTrace();
            }
        }
    }
}
