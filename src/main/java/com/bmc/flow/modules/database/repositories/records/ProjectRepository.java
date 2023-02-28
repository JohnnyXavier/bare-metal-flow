package com.bmc.flow.modules.database.repositories.records;

import com.bmc.flow.modules.database.dto.records.ProjectDto;
import com.bmc.flow.modules.database.entities.records.ProjectEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ProjectRepository implements PanacheRepositoryBase<ProjectEntity, UUID> {

  private static final String SELECT_DTO = " select e.id, e.name, e.description, e.coverImage, e.createdAt, e.createdBy.id, e.account.id";

  private static final String FROM_ENTITY = " from ProjectEntity e";

  public Uni<List<ProjectDto>> findAllCreatedByUserId(final UUID userId) {
    return this.find(SELECT_DTO + FROM_ENTITY +
                         " where e.createdBy.id = ?1 ", userId)
               .project(ProjectDto.class)
               .list();
  }

  public Uni<List<ProjectDto>> findAllProjectsByAccountId(final UUID accountId) {
    return this.find(SELECT_DTO + FROM_ENTITY +
                         " where e.account.id = ?1", accountId)
               .project(ProjectDto.class)
               .list();
  }

}
