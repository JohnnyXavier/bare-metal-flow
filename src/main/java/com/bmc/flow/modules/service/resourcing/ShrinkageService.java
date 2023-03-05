package com.bmc.flow.modules.service.resourcing;

import com.bmc.flow.modules.database.dto.resourcing.ShrinkageDto;
import com.bmc.flow.modules.database.entities.resourcing.ShrinkageEntity;
import com.bmc.flow.modules.database.repositories.resourcing.ShrinkageRepository;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;

import static java.lang.Boolean.FALSE;
import static java.util.UUID.randomUUID;

@ApplicationScoped
public class ShrinkageService extends BasicPersistenceService<ShrinkageDto, ShrinkageEntity> {

  private final ShrinkageRepository shrinkageRepo;

  public ShrinkageService(final ShrinkageRepository shrinkageRepo) {
    super(shrinkageRepo, ShrinkageDto.class);
    this.shrinkageRepo = shrinkageRepo;
  }

  @ReactiveTransactional
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
  protected void updateField(final ShrinkageEntity toUpdate, final String key, final String value) {
    switch (key) {
      case "name" -> toUpdate.setName(value);
      case "duration" -> toUpdate.setDurationInMin(Short.valueOf(value));
      case "percentage" -> toUpdate.setPercentage(Short.valueOf(value));

      default -> throw new IllegalStateException("Unexpected value: " + key);
    }
  }
}
