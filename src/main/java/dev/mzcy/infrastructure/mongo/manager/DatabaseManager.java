package dev.mzcy.infrastructure.mongo.manager;

import dev.mzcy.bootstrap.IdleEmpireServer;
import dev.mzcy.infrastructure.mongo.exception.DatabaseException;
import dev.mzcy.infrastructure.mongo.exception.NoSuchRepositoryException;
import eu.koboo.en2do.MongoManager;
import eu.koboo.en2do.SettingsBuilder;
import eu.koboo.en2do.repository.Repository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bson.codecs.Codec;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SuppressWarnings({"rawtypes", "unchecked", "CastCanBeRemovedNarrowingVariableType"})
public class DatabaseManager {

    MongoManager mongoManager;
    Map<Class<? extends Repository<?, ?>>, Repository<?, ?>> repositoryMap = new ConcurrentHashMap<>();

    public DatabaseManager(String connectionString) {
        long startTime = System.nanoTime();
        this.mongoManager = initializeMongoManager(connectionString);
        registerCodecs();
        registerRepositories();
        logInitializationTime(startTime);
    }

    private MongoManager initializeMongoManager(String connectionString) {
        MongoManager manager = new MongoManager(connectionString,
                new SettingsBuilder().collectionPrefix("idleempire_").setMongoDBLoggerLevel(Level.OFF));
        logInfo("MongoDB connection established @ database \"{}\".", manager.getMongoDatabase().getName());
        return manager;
    }

    private void registerCodecs() {
        Set<Class<? extends Codec>> codecClasses = findClasses("dev.mzcy.data.mongo.codec", Codec.class);
        if (codecClasses.isEmpty()) {
            logWarn("No codecs found. Ensure codec classes exist in \"dev.mzcy.data.mongo.codec.*\"");
            return;
        }
        logInfo("Found {} codec(s).", codecClasses.size());
        for (Class<? extends Codec> codecClass : codecClasses) {
            try {
                Codec<?> codecInstance = codecClass.getDeclaredConstructor().newInstance();
                mongoManager.registerCodec(codecInstance);
                logInfo("Registered codec {}", codecClass.getSimpleName());
            } catch (Exception e) {
                throw new DatabaseException("Failed to register codec " + codecClass.getSimpleName(), e);
            }
        }
        logInfo("MongoDB codecs registered successfully.");
    }

    private void registerRepositories() {
        Set<Class<? extends Repository>> repositoryClasses = findClasses("dev.mzcy.data.mongo.repository", Repository.class);
        if (repositoryClasses.isEmpty()) {
            logWarn("No repositories found. Ensure repository classes exist in \"dev.mzcy.data.mongo.repository.*\"");
            return;
        }
        logInfo("Found {} repository(s).", repositoryClasses.size());
        for (Class<? extends Repository> repositoryClass : repositoryClasses) {
            try {
                Repository<?, ?> repositoryInstance = mongoManager.create(repositoryClass);
                repositoryMap.put((Class<? extends Repository<?, ?>>) repositoryClass, repositoryInstance);
                logInfo("Registered repository {}", repositoryClass.getSimpleName());
            } catch (Exception e) {
                throw new DatabaseException("Failed to register repository " + repositoryClass.getSimpleName(), e);
            }
        }
    }

    private <T> Set<Class<? extends T>> findClasses(String packageName, Class<T> type) {
        return new Reflections(packageName).getSubTypesOf(type);
    }

    private void logInitializationTime(long startTime) {
        double timeToStartInSeconds = Math.round((System.nanoTime() - startTime) / 1_000_000_000.0 * 100) / 100.0;
        logInfo("MongoDB manager initialized successfully in {}s. Services can now be initialized.", timeToStartInSeconds);
    }

    public <T extends Repository<?, ?>> T getRepository(Class<T> repositoryClass) {
        if (!repositoryMap.containsKey(repositoryClass)) {
            throw new NoSuchRepositoryException("Repository " + repositoryClass.getSimpleName() + " is not registered.");
        }
        return (T) repositoryMap.get(repositoryClass);
    }

    private void logInfo(String message, Object... args) {
        IdleEmpireServer.getInstance().getLogger().info(message, args);
    }

    private void logWarn(String message, Object... args) {
        IdleEmpireServer.getInstance().getLogger().warn(message, args);
    }
}