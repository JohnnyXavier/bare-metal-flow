package com.bmc.flow.modules.database.repositories.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.StatusDto;
import com.bmc.flow.modules.database.entities.catalogs.StatusEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class StatusRepository implements PanacheRepositoryBase<StatusEntity, UUID> {
  private static final String SELECT_DTO = " select e.id, e.name, e.description, e.isSystem, e.createdAt, e.createdBy.id";

  private static final String FROM_ENTITY = " from StatusEntity e";

  public Uni<List<StatusDto>> findAllCreatedByUserId(final UUID userId) {
    return this.find(SELECT_DTO + FROM_ENTITY +
            " where e.createdBy.id =?1", userId)
        .project(StatusDto.class)
        .list();
  }

  public Uni<List<StatusDto>> findAllCardStatusByBoardId(final UUID boardId) {
    return this.find(SELECT_DTO + FROM_ENTITY +
            " where e.board.id =?1", boardId)
        .project(StatusDto.class)
        .list();
  }

  public Uni<StatusEntity> findEntityByName(final String name) {
    return this.find("select e " + FROM_ENTITY +
            " where e.name =?1", name)
        .firstResult();
  }
}
