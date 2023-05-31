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

import static com.bmc.flow.modules.service.reflection.MethodNames.SET_DESCRIPTION;
import static com.bmc.flow.modules.service.reflection.MethodNames.SET_NAME;

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
  @WithTransaction
  protected Uni<Void> update(final CardDifficultyEntity toUpdate, final String key, final String value) {
    return switch (key) {
      case "name" -> updateInPlace(toUpdate, SET_NAME, value);
      case "description" -> updateInPlace(toUpdate, SET_DESCRIPTION, value);

      default -> throw new IllegalStateException("Unexpected value: " + key);
    };
  }
}
