package dev.mzcy.infrastructure.mongo.repository;

import dev.mzcy.infrastructure.mongo.profiles.model.IdlePlayer;
import eu.koboo.en2do.repository.Collection;
import eu.koboo.en2do.repository.Repository;

import java.util.UUID;

@Collection("idle_players")
public interface IdlePlayerRepository extends Repository<IdlePlayer, UUID> {
}
