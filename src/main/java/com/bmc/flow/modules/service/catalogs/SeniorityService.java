package com.bmc.flow.modules.service.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.SeniorityDto;
import com.bmc.flow.modules.database.entities.catalogs.SeniorityEntity;
import com.bmc.flow.modules.database.repositories.catalogs.SeniorityRepository;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import com.bmc.flow.modules.service.utils.CreationUtils;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;

@ApplicationScoped
public class SeniorityService extends BasicPersistenceService<SeniorityDto, SeniorityEntity> {

  private final SeniorityRepository seniorityRepo;

  public SeniorityService(final SeniorityRepository seniorityRepo) {
    super(seniorityRepo, SeniorityDto.class);
    this.seniorityRepo = seniorityRepo;
  }

  @ReactiveTransactional
  public Uni<SeniorityDto> create(@Valid final SeniorityDto seniorityDto) {
    SeniorityEntity newSeniority = new SeniorityEntity();
    CreationUtils.createBaseCatalogEntity(newSeniority, seniorityDto);

    newSeniority.setLevel(seniorityDto.getLevel());

    return seniorityRepo.persist(newSeniority)
                        .replaceWith(findById(newSeniority.getId()));
  }

  @Override
  protected void updateField(final SeniorityEntity toUpdate, final String key, final String value) {
    switch (key) {
      case "name" -> toUpdate.setName(value);
      case "description" -> toUpdate.setDescription(value);
      case "level" -> toUpdate.setLevel(Short.parseShort(value));

      default -> throw new IllegalStateException("Unexpected value: " + key);
    }
  }
}
