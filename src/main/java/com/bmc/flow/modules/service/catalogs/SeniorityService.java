package com.bmc.flow.modules.service.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.SeniorityDto;
import com.bmc.flow.modules.database.entities.catalogs.SeniorityEntity;
import com.bmc.flow.modules.database.repositories.catalogs.SeniorityRepository;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import com.bmc.flow.modules.service.utils.CreationUtils;
import io.quarkus.cache.CacheResult;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

import static com.bmc.flow.modules.service.reflection.MethodNames.*;

/**
 * this class is a data access service for seniority data.
 */
@ApplicationScoped
public class SeniorityService extends BasicPersistenceService<SeniorityDto, SeniorityEntity> {

    private final SeniorityRepository seniorityRepo;

    public SeniorityService(final SeniorityRepository seniorityRepo) {
        super(seniorityRepo, SeniorityDto.class);
        this.seniorityRepo = seniorityRepo;
    }

    @WithTransaction
    public Uni<SeniorityDto> create(@Valid final SeniorityDto seniorityDto) {
        SeniorityEntity newSeniority = new SeniorityEntity();
        CreationUtils.populateBaseCatalogEntity(newSeniority, seniorityDto);

        newSeniority.setLevel(seniorityDto.getLevel());

        return seniorityRepo.persist(newSeniority)
                            .replaceWith(findById(newSeniority.getId()));
    }

    @CacheResult(cacheName = "seniority-by-name")
    public Uni<SeniorityEntity> findEntityByName(final String name) {
        return seniorityRepo.findEntityByName(name);
    }

    @Override
    @WithTransaction
    protected Uni<Void> update(final SeniorityEntity toUpdate, final String key, final String value) {
        return switch (key) {
            case "name" -> updateInPlace(toUpdate, SET_NAME, value);
            case "description" -> updateInPlace(toUpdate, SET_DESCRIPTION, value);
            case "level" -> updateInPlace(toUpdate, SET_LEVEL, Short.parseShort(value));

            default -> throw new IllegalStateException("Unexpected value: " + key);
        };
    }
}
