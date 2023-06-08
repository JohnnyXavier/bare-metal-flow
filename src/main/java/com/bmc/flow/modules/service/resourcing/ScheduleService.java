package com.bmc.flow.modules.service.resourcing;

import com.bmc.flow.modules.database.dto.resourcing.FullScheduleDto;
import com.bmc.flow.modules.database.dto.resourcing.ScheduleDto;
import com.bmc.flow.modules.database.dto.resourcing.ShrinkageDto;
import com.bmc.flow.modules.database.entities.resourcing.ScheduleEntity;
import com.bmc.flow.modules.database.repositories.resourcing.ScheduleRepository;
import com.bmc.flow.modules.database.repositories.resourcing.ShrinkageRepository;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import io.quarkus.cache.CacheResult;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.bmc.flow.modules.service.reflection.MethodNames.SET_HOURS_A_DAY;
import static java.util.UUID.randomUUID;

@ApplicationScoped
public class ScheduleService extends BasicPersistenceService<ScheduleDto, ScheduleEntity> {

    private final ScheduleRepository scheduleRepo;

    private final ShrinkageRepository shrinkageRepo;

    public ScheduleService(final ScheduleRepository scheduleRepo, final ShrinkageRepository shrinkageRepo) {
        super(scheduleRepo, ScheduleDto.class);
        this.scheduleRepo  = scheduleRepo;
        this.shrinkageRepo = shrinkageRepo;
    }

    @WithTransaction
    public Uni<ScheduleDto> create(@Valid final ScheduleDto scheduleDto) {
        ScheduleEntity newSchedule = new ScheduleEntity();
        newSchedule.setId(randomUUID());
        newSchedule.setHoursADay(scheduleDto.getHoursADay());

        return scheduleRepo.persist(newSchedule)
                           .replaceWith(findById(newSchedule.getId()));
    }

    @Override
    @WithTransaction
    protected Uni<Void> update(final ScheduleEntity toUpdate, final String key, final String value) {
        return switch (key) {
            case "hoursADay" -> updateInPlace(toUpdate, SET_HOURS_A_DAY, Short.parseShort(value));

            default -> throw new IllegalStateException("Unexpected value: " + key);
        };
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
                                                 .map(entity ->
                                                     new ShrinkageDto(entity.getId(), entity.getName(), "", entity.getDurationInMin(),
                                                         (entity.getPercentage() == null ? 0 : entity.getPercentage()),
                                                         entity.getIsSystem(),
                                                         entity.getCreatedAt(), entity.getCreatedBy().getId())
                                                 )
                                                 .collect(Collectors.toSet());

                               fullScheduleDto.setShrinkages(shrinkageDtos);
                               fullScheduleDto.setTotalShrinkageTime();

                           }).replaceWith(fullScheduleDto);

    }
}
