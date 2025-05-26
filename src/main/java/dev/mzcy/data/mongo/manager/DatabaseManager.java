package dev.mzcy.data.mongo.manager;

import dev.mzcy.bootstrap.IdleEmpireServer;
import dev.mzcy.data.mongo.exception.DatabaseException;
import dev.mzcy.data.mongo.exception.NoSuchRepositoryException;
import eu.koboo.en2do.MongoManager;
import eu.koboo.en2do.repository.Repository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bson.codecs.Codec;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DatabaseManager {

    MongoManager mongoManager;

    Map<Class<? extends Repository<?, ?>>, Repository<?, ?>> repositoryMap = new ConcurrentHashMap<>();

    @SuppressWarnings("rawtypes")
    public DatabaseManager(String connectionString) {
        long startTime = System.nanoTime();
        this.mongoManager = new MongoManager(connectionString);

        IdleEmpireServer.getInstance().getLogger().info("MongoDB connection established @ database \"{}\".", mongoManager.getMongoDatabase().getName());
        IdleEmpireServer.getInstance().getLogger().info("Searching for codecs...");
        Reflections codecReflections = new Reflections("dev.mzcy.data.mongo.codec");
        Set<Class<? extends Codec>> codecClasses = codecReflections.getSubTypesOf(Codec.class);
        if (codecClasses.isEmpty()) {
            IdleEmpireServer.getInstance().getLogger().warn("No codecs found. MongoDB manager will not register any codecs.");
            IdleEmpireServer.getInstance().getLogger().warn("Please ensure that you have created codec classes in the package \"dev.mzcy.data.mongo.codec.*\"");
            return;
        } else {
            IdleEmpireServer.getInstance().getLogger().info("Found {} codec(s).", codecClasses.size());
            for (Class<? extends Codec<?>> codecClass : codecClasses) {
                try {
                    Codec<?> codecInstance = codecClass.getDeclaredConstructor().newInstance();
                    mongoManager.registerCodec(codecInstance);
                    IdleEmpireServer.getInstance().getLogger().info("Registered codec {}", codecClass.getSimpleName());
                } catch (Exception e) {
                    throw new DatabaseException("Failed to register codec " + codecClass.getSimpleName(), e);
                }
            }
        }
        IdleEmpireServer.getInstance().getLogger().info("MongoDB codecs registered successfully.");
        IdleEmpireServer.getInstance().getLogger().info("Searching for repositories...");
        Reflections repositoryReflections = new Reflections("dev.mzcy.data.mongo.repository");
        Set<Class<? extends Repository>> repositoryClasses = repositoryReflections.getSubTypesOf(Repository.class);
        if (repositoryClasses.isEmpty()) {
            IdleEmpireServer.getInstance().getLogger().warn("No repositories found. MongoDB manager will not register any repositories.");
            IdleEmpireServer.getInstance().getLogger().warn("Please ensure that you have created repository classes in the package \"dev.mzcy.data.mongo.repository.*\"");
            return;
        } else {
            IdleEmpireServer.getInstance().getLogger().info("Found {} repository(s).", repositoryClasses.size());
            for (Class<? extends Repository<?, ?>> repositoryClass : repositoryClasses) {
                try {
                    Repository<?, ?> repositoryInstance = mongoManager.create(repositoryClass);
                    repositoryMap.put(repositoryClass, repositoryInstance);
                    IdleEmpireServer.getInstance().getLogger().info("Registered repository {}", repositoryClass.getSimpleName());
                } catch (Exception e) {
                    throw new DatabaseException("Failed to register repository " + repositoryClass.getSimpleName(), e);
                }
            }
        }

        double timeToStartInSeconds = Math.round((System.nanoTime() - startTime) / 1_000_000_000.0 * 100) / 100.0;
        IdleEmpireServer.getInstance().getLogger().info("MongoDB manager initialized successfully in {}s. Services can now be initialized.", timeToStartInSeconds);
    }

    public <T extends Repository<?, ?>> T getRepository(Class<T> repositoryClass) {
        if (!repositoryMap.containsKey(repositoryClass)) {
            throw new NoSuchRepositoryException("Repository " + repositoryClass.getSimpleName() + " is not registered.");
        }
        return (T) repositoryMap.get(repositoryClass);
    }

}
