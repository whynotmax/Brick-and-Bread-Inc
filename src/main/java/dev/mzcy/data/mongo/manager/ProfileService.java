package dev.mzcy.data.mongo.manager;

import dev.mzcy.bootstrap.IdleEmpireServer;
import eu.koboo.en2do.repository.Repository;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public abstract class ProfileService<E, ID, R extends Repository<E, ID>> {

    Class<E> entityClass;
    Class<ID> idClass;
    R repository;


    Map<ID, E> cache = new ConcurrentHashMap<ID, E>();

    public ProfileService(final Class<R> repositoryClass) {
        this.repository = IdleEmpireServer.getInstance().getDatabaseManager().getRepository(repositoryClass);
        this.entityClass = this.repository.getEntityClass();
        this.idClass = this.repository.getEntityUniqueIdClass();
    }

    public E loadEntity(ID entityId) {
        E entity = repository.findFirstById(entityId);
        if (entity == null) {
            entity = onEntityCreate(entityId);
            repository.save(entity);
        }
        entity = onEntityLoad(entity);
        return entity;
    }

    public E getEntity(ID entityId) {
        if (cache.containsKey(entityId)) {
            return cache.get(entityId);
        }
        E entity = loadEntity(entityId);
        cache.put(entityId, entity);
        return entity;
    }

    public void modifyEntity(ID entityId, Function<E, E> modifier) {
        E entity = getEntity(entityId);
        if (entity == null) {
            throw new IllegalArgumentException("Entity not found for ID: " + entityId);
        }
        entity = modifier.apply(entity);
        updateCache(entityId, entity);
    }

    private void updateCache(ID entityId, E entity) {
        if (entity != null) {
            cache.put(entityId, entity);
        } else {
            cache.remove(entityId);
        }
    }

    public void saveEntity(E entity) {
        ID entityId = repository.getUniqueId(entity);
        if (entityId == null) {
            throw new IllegalArgumentException("Entity ID cannot be null");
        }
        entity = onEntitySave(entity);
        repository.save(entity);
        cache.put(entityId, entity);
    }

    public void deleteEntity(ID entityId) {
        cache.remove(entityId);
        repository.deleteById(entityId);
    }

    protected abstract E onEntityCreate(ID entityId);

    protected abstract E onEntitySave(E entity);

    protected E onEntityLoad(E entity) {
        // Default implementation does nothing, can be overridden
        return entity;
    }

}
