package com.bmc.flow.modules.database.repositories.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.CardDifficultyDto;
import com.bmc.flow.modules.database.entities.catalogs.CardDifficultyEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CardDifficultyRepository implements PanacheRepositoryBase<CardDifficultyEntity, UUID> {

  private static final String SELECT_DTO = " select e.id, e.name, e.description, e.level, e.isSystem, e.createdAt, e.createdBy.id";

  private static final String FROM_ENTITY = " from CardDifficultyEntity e";

  public Uni<List<CardDifficultyDto>> findAllCreatedByUserId(final UUID userId) {
    return this.find(SELECT_DTO + FROM_ENTITY +
                         " where e.createdBy.id =?1", userId)
               .project(CardDifficultyDto.class)
               .list();
  }

}
