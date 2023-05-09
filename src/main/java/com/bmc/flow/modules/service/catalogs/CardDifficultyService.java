package com.bmc.flow.modules.service.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.CardDifficultyDto;
import com.bmc.flow.modules.database.entities.catalogs.CardDifficultyEntity;
import com.bmc.flow.modules.database.repositories.catalogs.CardDifficultyRepository;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import com.bmc.flow.modules.service.utils.CreationUtils;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

@ApplicationScoped
public class CardDifficultyService extends BasicPersistenceService<CardDifficultyDto, CardDifficultyEntity> {

  private final CardDifficultyRepository cardDifficultyRepo;

  public CardDifficultyService(final CardDifficultyRepository cardDifficultyRepo) {
    super(cardDifficultyRepo, CardDifficultyDto.class);
    this.cardDifficultyRepo = cardDifficultyRepo;
  }

  @WithTransaction
  @Override
  public Uni<CardDifficultyDto> create(@Valid final CardDifficultyDto cardDifficultyDto) {
    CardDifficultyEntity newCardDifficulty = new CardDifficultyEntity();
    CreationUtils.createBaseCatalogEntity(newCardDifficulty, cardDifficultyDto);

    newCardDifficulty.setLevel(cardDifficultyDto.getLevel());

    return cardDifficultyRepo.persist(newCardDifficulty)
                             .replaceWith(findById(newCardDifficulty.getId()));
  }

  @Override
  protected void updateField(final CardDifficultyEntity toUpdate, final String key, final String value) {
    switch (key) {
      case "name" -> toUpdate.setName(value);
      case "description" -> toUpdate.setDescription(value);

      default -> throw new IllegalStateException("Unexpected value: " + key);
    }
  }
}
