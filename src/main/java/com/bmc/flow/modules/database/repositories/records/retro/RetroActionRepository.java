package com.bmc.flow.modules.database.repositories.records.retro;

import com.bmc.flow.modules.database.dto.records.retro.RetroActionDto;
import com.bmc.flow.modules.database.entities.records.retro.RetroActionEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class RetroActionRepository implements PanacheRepositoryBase<RetroActionEntity, UUID> {

  private static final String SELECT_DTO = " select e.id, e.actionToTake, e.createdAt, e.createdBy.id, e.retroBoard.id";

  private static final String FROM_ENTITY = " from RetroActionEntity e";

  public Uni<List<RetroActionDto>> findAllByProjectId(final UUID projectId) {
    return this.find(SELECT_DTO + FROM_ENTITY +
                         " where e.project.id =?1", projectId)
               .project(RetroActionDto.class)
               .list();
  }

  public Uni<List<RetroActionDto>> findAllByRetroBoardId(final UUID retroBoardId) {
    return this.find(SELECT_DTO + FROM_ENTITY +
                         " where e.retroBoard.id =?1", retroBoardId)
               .project(RetroActionDto.class)
               .list();
  }

}
