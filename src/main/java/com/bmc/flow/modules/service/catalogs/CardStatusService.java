package com.bmc.flow.modules.service.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.CardStatusDto;
import com.bmc.flow.modules.database.entities.catalogs.StatusEntity;
import com.bmc.flow.modules.database.repositories.catalogs.CardStatusRepository;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import com.bmc.flow.modules.service.utils.CreationUtils;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;

@ApplicationScoped
public class CardStatusService extends BasicPersistenceService<CardStatusDto, StatusEntity> {

  private final CardStatusRepository cardStatusRepo;

  public CardStatusService(final CardStatusRepository cardStatusRepo) {
    super(cardStatusRepo, CardStatusDto.class);
    this.cardStatusRepo = cardStatusRepo;
  }

  @ReactiveTransactional
  @Override
  public Uni<CardStatusDto> create(@Valid final CardStatusDto cardStatusDto) {
    StatusEntity newCardStatus = new StatusEntity();
    CreationUtils.createBaseCatalogEntity(newCardStatus, cardStatusDto);

    return cardStatusRepo.persist(newCardStatus)
                         .replaceWith(findById(newCardStatus.getId()));
  }

  @Override
  protected void updateField(final StatusEntity toUpdate, final String key, final String value) {
    switch (key) {
      case "name" -> toUpdate.setName(value);
      case "description" -> toUpdate.setDescription(value);

      default -> throw new IllegalStateException("Unexpected value: " + key);
    }
  }
}
