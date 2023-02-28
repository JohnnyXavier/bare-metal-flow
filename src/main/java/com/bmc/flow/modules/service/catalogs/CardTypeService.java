package com.bmc.flow.modules.service.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.CardTypeDto;
import com.bmc.flow.modules.database.entities.catalogs.CardTypeEntity;
import com.bmc.flow.modules.database.repositories.catalogs.CardTypeRepository;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import com.bmc.flow.modules.service.utils.CreationUtils;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;

@ApplicationScoped
public class CardTypeService extends BasicPersistenceService<CardTypeDto, CardTypeEntity> {

  private final CardTypeRepository cardTypeRepo;

  public CardTypeService(final CardTypeRepository cardTypeRepo) {
    super(cardTypeRepo, CardTypeDto.class);
    this.cardTypeRepo = cardTypeRepo;
  }

  @ReactiveTransactional
  public Uni<CardTypeDto> create(@Valid final CardTypeDto cardTypeDto) {
    CardTypeEntity newCardType = new CardTypeEntity();
    CreationUtils.createBaseCatalogEntity(newCardType, cardTypeDto);

    return cardTypeRepo.persist(newCardType)
                       .replaceWith(findById(newCardType.getId()));
  }

  @Override
  protected void updateField(final CardTypeEntity toUpdate, final String key, final String value) {
    switch (key) {
      case "name" -> toUpdate.setName(value);
      case "description" -> toUpdate.setDescription(value);

      default -> throw new IllegalStateException("Unexpected value: " + key);
    }
  }
}
