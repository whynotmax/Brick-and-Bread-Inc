package dev.mzcy.infrastructure.mongo.profiles;

import dev.mzcy.infrastructure.mongo.manager.ProfileService;
import dev.mzcy.infrastructure.mongo.profiles.model.IdlePlayer;
import dev.mzcy.infrastructure.mongo.repository.IdlePlayerRepository;

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
