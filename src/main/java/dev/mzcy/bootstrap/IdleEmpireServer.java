package dev.mzcy.bootstrap;

import dev.mzcy.bootstrap.console.JLineConsole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extras.MojangAuth;
import org.fusesource.jansi.AnsiConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdleEmpireServer {

    @Getter
    static IdleEmpireServer instance;
    JLineConsole console;
    Logger logger = LoggerFactory.getLogger(IdleEmpireServer.class);

    public IdleEmpireServer() {
        AnsiConsole.systemInstall();
        long startTime = System.nanoTime();
        instance = this;

        logger.info("Initializing console...");
        console = new JLineConsole();

        logger.info("Initializing server...");
        MinecraftServer minecraftServer = MinecraftServer.init();
        MinecraftServer.getExceptionManager().setExceptionHandler(exception -> logger.error("An unexpected error occurred!", exception));

        String address = "127.0.0.1";
        int port = 25565;

        minecraftServer.start(address, port);
        logger.info("Starting server @ {}:{}", address, port);

        setDefaultSystemProperties();

        MojangAuth.init();
        logger.info("Listening on {}:{}", address, port);

        console.start();
        logStartupTime(startTime);
    }

    private void setDefaultSystemProperties() {
        System.setProperty("minestom.chunk-view-distance", System.getProperty("minestom.chunk-view-distance", "5"));
        System.setProperty("minestom.entity-view-distance", System.getProperty("minestom.entity-view-distance", "8"));
    }

    private void logStartupTime(long startTime) {
        double timeToStartInSeconds = Math.round((System.nanoTime() - startTime) / 1_000_000_000.0 * 100) / 100.0;
        logger.info("Server started in {}s!", timeToStartInSeconds);
        logger.info("Server is ready to accept connections!");
    }

    public void shutdown() {
        logger.info("Shutting down server...");
        MinecraftServer.stopCleanly();
        logger.info("Server has been shut down.");
    }

}