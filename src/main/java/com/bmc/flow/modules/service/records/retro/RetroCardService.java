package com.bmc.flow.modules.service.records.retro;

import com.bmc.flow.modules.database.dto.records.retro.RetroCardDto;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.records.retro.RetrospectiveEntity;
import com.bmc.flow.modules.database.entities.records.retro.RetroCardEntity;
import com.bmc.flow.modules.database.repositories.records.retro.RetroCardRepository;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@ApplicationScoped
public class RetroCardService extends BasicPersistenceService<RetroCardDto, RetroCardEntity> {

  private final RetroCardRepository repository;


  public RetroCardService(final RetroCardRepository repository) {
    super(repository, RetroCardDto.class);
    this.repository = repository;
  }

  public Uni<List<RetroCardDto>> findAllByRetroBoardId(final UUID retroBoardId) {
    return repository.findAllByRetroBoardId(retroBoardId);
  }

  @ReactiveTransactional
  public Uni<RetroCardDto> create(@Valid final RetroCardDto retroCardDto) {
    UserEntity cardCreator = new UserEntity();
    cardCreator.setId(retroCardDto.getCreatedBy());

    RetrospectiveEntity board = new RetrospectiveEntity();
    board.setId(retroCardDto.getRetroBoardId());

    RetroCardEntity newCard = new RetroCardEntity();
    newCard.setId(randomUUID());
    newCard.setComment(retroCardDto.getComment());
    newCard.setCreatedBy(cardCreator);
    newCard.setRetroBoard(board);

    return repository.persist(newCard)
                     .replaceWith(findById(newCard.getId()));
  }

  @Override
  protected void updateField(final RetroCardEntity toUpdate, final String key, final String value) {
    switch (key) {
      case "comment" -> toUpdate.setComment(value);
      case "votes" -> toUpdate.setVotes(Short.parseShort(value));

      default -> throw new IllegalStateException("Unexpected value: " + key);
    }
  }
}