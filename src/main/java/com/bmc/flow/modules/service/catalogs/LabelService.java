package com.bmc.flow.modules.service.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.LabelDto;
import com.bmc.flow.modules.database.entities.catalogs.LabelEntity;
import com.bmc.flow.modules.database.repositories.catalogs.LabelRepository;
import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import com.bmc.flow.modules.service.base.PageResult;
import com.bmc.flow.modules.service.utils.CreationUtils;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

import java.util.UUID;

@ApplicationScoped
public class LabelService extends BasicPersistenceService<LabelDto, LabelEntity> {

  private final LabelRepository labelRepo;

  public LabelService(final LabelRepository labelRepo) {
    super(labelRepo, LabelDto.class);
    this.labelRepo = labelRepo;
  }

  @WithTransaction
  public Uni<LabelDto> create(@Valid final LabelDto labelDto) {
    LabelEntity newLabel = new LabelEntity();
    CreationUtils.createBaseCatalogEntity(newLabel, labelDto);
    newLabel.setColorHex(labelDto.getColorHex());

    return labelRepo.persist(newLabel)
        .replaceWith(findById(newLabel.getId()));
  }

  public Uni<PageResult<LabelDto>> findAllByCardIdPaged(final UUID cardId, final Pageable pageable) {
    return findAllPaged(labelRepo.findAllByCardId(cardId, pageable.getSort()), "labels-in-card-" + cardId, pageable.getPage());
  }

  @Override
  protected void updateField(final LabelEntity toUpdate, final String key, final String value) {
    switch (key) {
      case "name" -> toUpdate.setName(value);
      case "description" -> toUpdate.setDescription(value);
      case "colorHex" -> toUpdate.setColorHex(value);

      default -> throw new IllegalStateException("Unexpected value: " + key);
    }
  }

  public Uni<LabelEntity> findEntityByName(final String name) {
    return labelRepo.findEntityByName(name);

  }
}
