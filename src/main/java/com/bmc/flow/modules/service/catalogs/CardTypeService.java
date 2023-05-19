package com.bmc.flow.modules.service.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.CardTypeDto;
import com.bmc.flow.modules.database.entities.catalogs.CardTypeEntity;
import com.bmc.flow.modules.database.repositories.catalogs.CardTypeRepository;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import com.bmc.flow.modules.service.utils.CreationUtils;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

import static com.bmc.flow.modules.service.reflection.MethodNames.SET_DESCRIPTION;
import static com.bmc.flow.modules.service.reflection.MethodNames.SET_NAME;

@ApplicationScoped
public class CardTypeService extends BasicPersistenceService<CardTypeDto, CardTypeEntity> {

  private final CardTypeRepository cardTypeRepo;

  public CardTypeService(final CardTypeRepository cardTypeRepo) {
    super(cardTypeRepo, CardTypeDto.class);
    this.cardTypeRepo = cardTypeRepo;
  }

  @WithTransaction
  public Uni<CardTypeDto> create(@Valid final CardTypeDto cardTypeDto) {
    CardTypeEntity newCardType = new CardTypeEntity();
    CreationUtils.createBaseCatalogEntity(newCardType, cardTypeDto);

    return cardTypeRepo.persist(newCardType)
        .replaceWith(findById(newCardType.getId()));
  }

  @Override
  @WithTransaction
  protected Uni<Void> update(final CardTypeEntity toUpdate, final String key, final String value) {
    return switch (key) {
      case "name" -> updateInplace(toUpdate, SET_NAME, value);
      case "description" -> updateInplace(toUpdate, SET_DESCRIPTION, value);


      default -> throw new IllegalStateException("Unexpected value: " + key);
    };
  }
}
