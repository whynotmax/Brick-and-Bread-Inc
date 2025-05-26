package dev.mzcy.data.mongo.manager;

import dev.mzcy.bootstrap.IdleEmpireServer;
import eu.koboo.en2do.MongoManager;
import org.bson.codecs.Codec;
import org.reflections.Reflections;

import java.util.Set;

public class DatabaseManager {

    MongoManager mongoManager;

    public DatabaseManager(String connectionString) {
        long startTime = System.nanoTime();
        this.mongoManager = new MongoManager(connectionString);

        IdleEmpireServer.getInstance().getLogger().info("MongoDB connection established @ database \"{}\".", mongoManager.getMongoDatabase().getName());
        IdleEmpireServer.getInstance().getLogger().info("Searching for codecs...");
        Reflections codecReflections = new Reflections("dev.mzcy.data.mongo.codec");
        Set<Class<? extends Codec>> codecClasses = codecReflections.getSubTypesOf(Codec.class);
        if (codecClasses.isEmpty()) {
            IdleEmpireServer.getInstance().getLogger().info("No codecs found.");
            return;
        } else {
            IdleEmpireServer.getInstance().getLogger().info("Found {} codec(s).", codecClasses.size());
            for (Class<? extends Codec<?>> codecClass : ) {
                try {
                    Codec<?> codecInstance = codecClass.getDeclaredConstructor().newInstance();
                    mongoManager = mongoManager.registerCodec(codecInstance);
                    IdleEmpireServer.getInstance().getLogger().info("Registered codec {}", codecClass.getSimpleName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        IdleEmpireServer.getInstance().getLogger().info("MongoDB codecs registered successfully.");

        double timeToStartInSeconds = Math.round((System.nanoTime() - startTime) / 1_000_000_000.0 * 100) / 100.0;
        IdleEmpireServer.getInstance().getLogger().info("MongoDB manager initialized successfully in {}s. Services can now be initialized.", timeToStartInSeconds);
    }

}
