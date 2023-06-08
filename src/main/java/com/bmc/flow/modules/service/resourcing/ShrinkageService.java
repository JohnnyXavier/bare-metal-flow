package com.bmc.flow.modules.service.resourcing;

import com.bmc.flow.modules.database.dto.resourcing.ShrinkageDto;
import com.bmc.flow.modules.database.entities.resourcing.ShrinkageEntity;
import com.bmc.flow.modules.database.repositories.resourcing.ShrinkageRepository;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

import static com.bmc.flow.modules.service.reflection.MethodNames.*;
import static java.lang.Boolean.FALSE;
import static java.util.UUID.randomUUID;

/**
 * this class is a data access service for shrinkage data.
 */
@ApplicationScoped
public class ShrinkageService extends BasicPersistenceService<ShrinkageDto, ShrinkageEntity> {

    private final ShrinkageRepository shrinkageRepo;

    public ShrinkageService(final ShrinkageRepository shrinkageRepo) {
        super(shrinkageRepo, ShrinkageDto.class);
        this.shrinkageRepo = shrinkageRepo;
    }

    @WithTransaction
    public Uni<ShrinkageDto> create(@Valid final ShrinkageDto shrinkageDto) {
        ShrinkageEntity newShrinkage = new ShrinkageEntity();
        newShrinkage.setId(randomUUID());
        newShrinkage.setName(shrinkageDto.getName());
        newShrinkage.setDurationInMin(shrinkageDto.getDurationInMin());
        newShrinkage.setPercentage(shrinkageDto.getPercentage());
        newShrinkage.setIsSystem(FALSE);

        return shrinkageRepo.persist(newShrinkage)
                            .replaceWith(findById(newShrinkage.getId()));
    }

    @Override
    @WithTransaction
    protected Uni<Void> update(final ShrinkageEntity toUpdate, final String key, final String value) {
        return switch (key) {
            case "name" -> updateInPlace(toUpdate, SET_NAME, value);
            case "duration" -> updateInPlace(toUpdate, SET_DURATION_IN_MIN, Short.valueOf(value));
            case "percentage" -> updateInPlace(toUpdate, SET_PERCENTAGE, Short.valueOf(value));

            default -> throw new IllegalStateException("Unexpected value: " + key);
        };
    }
}
