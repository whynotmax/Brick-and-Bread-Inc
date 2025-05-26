package dev.mzcy.data.mongo.repository;

import dev.mzcy.data.mongo.model.IdlePlayer;
import eu.koboo.en2do.repository.Collection;
import eu.koboo.en2do.repository.Repository;

@Collection("idle_players")
public interface IdlePlayerRepository extends Repository<IdlePlayer, String> {
}
