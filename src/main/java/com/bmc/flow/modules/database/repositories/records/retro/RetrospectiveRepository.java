package com.bmc.flow.modules.database.repositories.records.retro;

import com.bmc.flow.modules.database.dto.records.retro.RetrospectiveDto;
import com.bmc.flow.modules.database.entities.records.retro.RetrospectiveEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class RetrospectiveRepository implements PanacheRepositoryBase<RetrospectiveEntity, UUID> {

  private static final String SELECT_DTO = " select e.id, e.createdAt, e.createdBy.id, e.project.id, e.sprintBoard.id";

  private static final String FROM_ENTITY = " from RetrospectiveEntity e";

  public Uni<List<RetrospectiveDto>> findAllByProjectId(final UUID projectId) {
    return this.find(SELECT_DTO + FROM_ENTITY +
                         " where e.project.id =?1", projectId)
               .project(RetrospectiveDto.class)
               .list();
  }


  public Uni<RetrospectiveDto> findBySprintId(final UUID sprintId) {
    return this.find(SELECT_DTO + FROM_ENTITY +
                         " where e.sprint.id =?1", sprintId)
               .project(RetrospectiveDto.class)
               .singleResult();
  }

}
