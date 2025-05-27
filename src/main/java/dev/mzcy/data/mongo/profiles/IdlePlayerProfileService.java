package dev.mzcy.data.mongo.profiles;

import dev.mzcy.data.mongo.manager.DatabaseManager;
import dev.mzcy.data.mongo.manager.ProfileService;
import dev.mzcy.data.mongo.profiles.model.IdlePlayer;
import dev.mzcy.data.mongo.repository.IdlePlayerRepository;

import java.util.UUID;

public class IdlePlayerProfileService extends ProfileService<IdlePlayer, UUID, IdlePlayerRepository> {

    public IdlePlayerProfileService() {
        super(IdlePlayerRepository.class);
    }

    @Override
    protected IdlePlayer onEntityCreate(UUID entityId) {
        IdlePlayer idlePlayer = new IdlePlayer();
        idlePlayer.setUniqueId(entityId);
        return idlePlayer;
    }

    @Override
    protected IdlePlayer onEntitySave(IdlePlayer entity) {
        return entity;
    }
}
