package com.bmc.flow.modules.service.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.StatusDto;
import com.bmc.flow.modules.database.entities.catalogs.StatusEntity;
import com.bmc.flow.modules.database.repositories.catalogs.StatusRepository;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import com.bmc.flow.modules.service.utils.CreationUtils;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

import static com.bmc.flow.modules.service.reflection.MethodNames.SET_DESCRIPTION;
import static com.bmc.flow.modules.service.reflection.MethodNames.SET_NAME;

@ApplicationScoped
public class CardStatusService extends BasicPersistenceService<StatusDto, StatusEntity> {

  private final StatusRepository cardStatusRepo;

  public CardStatusService(final StatusRepository cardStatusRepo) {
    super(cardStatusRepo, StatusDto.class);
    this.cardStatusRepo = cardStatusRepo;
  }

  @WithTransaction
  @Override
  public Uni<StatusDto> create(@Valid final StatusDto statusDto) {
    StatusEntity newCardStatus = new StatusEntity();
    CreationUtils.createBaseCatalogEntity(newCardStatus, statusDto);

    return cardStatusRepo.persist(newCardStatus)
        .replaceWith(findById(newCardStatus.getId()));
  }

  @Override
  @WithTransaction
  protected Uni<Void> update(final StatusEntity toUpdate, final String key, final String value) {
    return switch (key) {
      case "name" -> updateInPlace(toUpdate, SET_NAME, value);
      case "description" -> updateInPlace(toUpdate, SET_DESCRIPTION, value);

      default -> throw new IllegalStateException("Unexpected value: " + key);
    };
  }

  public Uni<StatusEntity> findEntityByName(final String name) {
    return cardStatusRepo.findEntityByName(name);
  }
}
