package com.bmc.flow.modules.database.repositories.resourcing;

import com.bmc.flow.modules.database.entities.resourcing.ScheduleEntryEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class SchedulingRepository implements PanacheRepositoryBase<ScheduleEntryEntity, UUID> {

  private static final String SELECT_DTO = " select e.id, e.from, e.to, e.isUnbounded, e.scheduledHours" +
      " e.createdAt, e.createdBy.id";

  private static final String FROM_ENTITY = " from ScheduleEntryEntity e";

}
