package com.bmc.flow.modules.database.repositories.records;

import com.bmc.flow.modules.database.dto.records.TaskDto;
import com.bmc.flow.modules.database.entities.records.TaskEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TaskRepository implements PanacheRepositoryBase<TaskEntity, UUID> {

  private static final String SELECT_DTO = " select e.id, e.name, e.dueDate, e.completeDate, e.isCompleted, e.createdAt," +
      " e.createdBy.id, e.card.id";

  private static final String FROM_ENTITY = " from TaskEntity e ";

  public Uni<List<TaskDto>> findAllCreatedByUserId(final UUID userId) {
    return this.find(SELECT_DTO + FROM_ENTITY +
                         " where e.createdBy.id =?1", userId)
               .project(TaskDto.class)
               .list();
  }

}
