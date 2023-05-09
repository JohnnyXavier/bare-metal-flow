package com.bmc.flow.modules.database.repositories.resourcing;

import com.bmc.flow.modules.database.dto.resourcing.ScheduleDto;
import com.bmc.flow.modules.database.entities.resourcing.ScheduleEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class ScheduleRepository implements PanacheRepositoryBase<ScheduleEntity, UUID> {

  private static final String SELECT_DTO = " select e.id, e.hoursADay, e.createdAt, e.createdBy.id";

  private static final String FROM_ENTITY = " from ScheduleEntity e";


  public Uni<ScheduleDto> findByUserId(final UUID userId) {
    return this.find("user_id", userId)
               .project(ScheduleDto.class)
               .singleResult();
  }

  public Uni<ScheduleEntity> findFullByUserId(final UUID userId) {
    return this.find("select e" + FROM_ENTITY +
                         " left join fetch e.shrinkages where e.id=?1"
                   , userId)
               .singleResult();
  }

}
