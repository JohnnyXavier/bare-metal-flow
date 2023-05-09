package com.bmc.flow.modules.database.repositories.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.CardTypeDto;
import com.bmc.flow.modules.database.entities.catalogs.CardTypeEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CardTypeRepository implements PanacheRepositoryBase<CardTypeEntity, UUID> {

  private static final String SELECT_DTO = " select e.id, e.name, e.description, e.isSystem, e.createdAt, e.createdBy.id";

  private static final String FROM_ENTITY = " from CardTypeEntity e";

  public Uni<List<CardTypeDto>> findAllCreatedByUserId(final UUID userId) {
    return this.find(SELECT_DTO + FROM_ENTITY +
                         " where e.createdBy.id =?1", userId)
               .project(CardTypeDto.class)
               .list();
  }

}
