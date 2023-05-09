package com.bmc.flow.modules.database.repositories.records;

import com.bmc.flow.modules.database.entities.records.ProjectEntity;
import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class ProjectRepository implements PanacheRepositoryBase<ProjectEntity, UUID> {

  private static final String SELECT_DTO = " select e.id, e.name, e.description, e.coverImage, e.createdAt, e.createdBy.id, e.account.id," +
      " e.projectLead.id";

  private static final String FROM_ENTITY = " from ProjectEntity e";

  public PanacheQuery<ProjectEntity> findAllCreatedByUserId(final UUID userId, final Sort sort) {
    return this.find(SELECT_DTO + FROM_ENTITY +
                         " where e.createdBy.id =?1", sort, userId);
  }

  public PanacheQuery<ProjectEntity> findAllByAccountId(final UUID userId, final Sort sort) {
    return this.find(SELECT_DTO + FROM_ENTITY +
                         " where e.account.id =?1", sort, userId);
  }


}
