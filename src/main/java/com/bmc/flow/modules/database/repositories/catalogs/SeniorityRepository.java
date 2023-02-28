package com.bmc.flow.modules.database.repositories.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.SeniorityDto;
import com.bmc.flow.modules.database.entities.catalogs.SeniorityEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class SeniorityRepository implements PanacheRepositoryBase<SeniorityEntity, UUID> {

  private static final String SELECT_DTO = " select e.id, e.name, e.description, e.level, e.createdAt, e.createdBy.id";

  private static final String FROM_ENTITY = " from SeniorityEntity e";

  public Uni<List<SeniorityDto>> findAllCreatedByUserId(final UUID userId) {
    return this.find(SELECT_DTO + FROM_ENTITY +
                         " where e.createdBy.id =?1", userId)
               .project(SeniorityDto.class)
               .list();
  }

}
