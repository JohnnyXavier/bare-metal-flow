package com.bmc.flow.modules.database.repositories.records;

import com.bmc.flow.modules.database.dto.records.SprintDto;
import com.bmc.flow.modules.database.entities.records.SprintEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class SprintRepository implements PanacheRepositoryBase<SprintEntity, UUID> {

  private static final String SELECT_DTO = " select e.id, e.name, e.goal, e.fromDate, e.toDate, e.startDate, e.closeDate, e.daysCycle," +
      " e.hasStarted, e.isClosed, e.originalPoints, e.finalPoints, e.createdAt, e.createdBy.id, e.board.id, e.project.id, e.retroBoard.id";

  private static final String FROM_ENTITY = " from SprintEntity e";

  public Uni<List<SprintDto>> findAllCreatedByUserId(final UUID userId) {
    return this.find(SELECT_DTO + FROM_ENTITY +
                         " where e.createdBy.id =?1", userId)
               .project(SprintDto.class)
               .list();
  }

  public Uni<List<SprintDto>> findAllByProjectId(final UUID projectId) {
    return this.find(SELECT_DTO + FROM_ENTITY +
                         " where e.project.id =?1", projectId)
               .project(SprintDto.class)
               .list();
  }

  public Uni<List<SprintDto>> findAllByBoardId(final UUID boardId) {
    return this.find(SELECT_DTO + FROM_ENTITY +
                         " where e.board.id =?1", boardId)
               .project(SprintDto.class)
               .list();
  }

}
