package com.bmc.flow.modules.database.repositories.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.CardStatusDto;
import com.bmc.flow.modules.database.entities.catalogs.StatusEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CardStatusRepository implements PanacheRepositoryBase<StatusEntity, UUID> {
  private static final String SELECT_DTO = " select e.id, e.name, e.description, e.isSystem, e.createdAt, e.createdBy.id";

  private static final String FROM_ENTITY = " from CardStatusEntity e";

  public Uni<List<CardStatusDto>> findAllCreatedByUserId(final UUID userId) {
    return this.find(SELECT_DTO + FROM_ENTITY +
                         " where e.createdBy.id =?1", userId)
               .project(CardStatusDto.class)
               .list();
  }

  public Uni<List<CardStatusDto>> findAllCardStatusByBoardId(final UUID boardId) {
    return this.find(SELECT_DTO + FROM_ENTITY +
                         " where e.board.id =?1", boardId)
               .project(CardStatusDto.class)
               .list();
  }

}
