package com.bmc.flow.modules.service.resourcing;

import com.bmc.flow.modules.database.dto.resourcing.FullScheduleDto;
import com.bmc.flow.modules.database.dto.resourcing.ScheduleDto;
import com.bmc.flow.modules.database.dto.resourcing.ShrinkageDto;
import com.bmc.flow.modules.database.entities.resourcing.ScheduleEntity;
import com.bmc.flow.modules.database.repositories.resourcing.ScheduleRepository;
import com.bmc.flow.modules.database.repositories.resourcing.ShrinkageRepository;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import io.quarkus.cache.CacheResult;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.UUID.randomUUID;

@ApplicationScoped
public class ScheduleService extends BasicPersistenceService<ScheduleDto, ScheduleEntity> {

  private final ScheduleRepository scheduleRepo;

  private final ShrinkageRepository shrinkageRepo;

  public ScheduleService(final ScheduleRepository scheduleRepo, final ShrinkageRepository shrinkageRepo) {
    super(scheduleRepo, ScheduleDto.class);
    this.scheduleRepo = scheduleRepo;
    this.shrinkageRepo = shrinkageRepo;
  }

  @ReactiveTransactional
  public Uni<ScheduleDto> create(@Valid final ScheduleDto scheduleDto) {
    ScheduleEntity newSchedule = new ScheduleEntity();
    newSchedule.setId(randomUUID());
    newSchedule.setHoursADay(scheduleDto.getHoursADay());

    return scheduleRepo.persist(newSchedule)
                       .replaceWith(findById(newSchedule.getId()));
  }

  @Override
  protected void updateField(final ScheduleEntity toUpdate, final String key, final String value) {
    if (key.equals("hoursADay")) {
      toUpdate.setHoursADay(Short.parseShort(value));
    } else {
      throw new IllegalStateException("Unexpected value: " + key);
    }
  }

  public Uni<ScheduleDto> findByUserId(final UUID userId) {
    return scheduleRepo.findByUserId(userId);
  }

  @CacheResult(cacheName = "full-schedule-by-user-id")
  public Uni<FullScheduleDto> findFullByUserId(final UUID userId) {
    FullScheduleDto fullScheduleDto = new FullScheduleDto();
    return scheduleRepo.findFullByUserId(userId)
                       .invoke(scheduleEntity -> {
                         fullScheduleDto.setId(scheduleEntity.getId());
                         fullScheduleDto.setHoursADay(scheduleEntity.getHoursADay());
                         fullScheduleDto.setCreatedAt(scheduleEntity.getCreatedAt());
                         fullScheduleDto.setCreatedBy(scheduleEntity.getCreatedBy().getId());

                         Set<ShrinkageDto> shrinkageDtos =
                             scheduleEntity.getShrinkages()
                                           .stream()
                                           .map(entity -> {
                                             ShrinkageDto shrinkageDto = new ShrinkageDto();

                                             shrinkageDto.setId(entity.getId());
                                             shrinkageDto.setName(entity.getName());
                                             shrinkageDto.setPercentage(entity.getPercentage() == null ? 0 : entity.getPercentage());
                                             shrinkageDto.setDurationInMin(entity.getDurationInMin());
                                             shrinkageDto.setIsSystem(entity.getIsSystem());
                                             shrinkageDto.setCreatedAt(entity.getCreatedAt());
                                             shrinkageDto.setCreatedBy(entity.getCreatedBy().getId());
                                             shrinkageDto.setDescription("");

                                             return shrinkageDto;
                                           })
                                           .collect(Collectors.toSet());

                         fullScheduleDto.setShrinkages(shrinkageDtos);
                         fullScheduleDto.setTotalShrinkageTime();

                       }).replaceWith(fullScheduleDto);

  }
}
