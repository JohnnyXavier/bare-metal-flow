package com.bmc.flow.modules.service.records.retro;

import com.bmc.flow.modules.database.dto.records.retro.RetroCardDto;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.records.retro.RetroCardEntity;
import com.bmc.flow.modules.database.entities.records.retro.RetrospectiveEntity;
import com.bmc.flow.modules.database.repositories.records.retro.RetroCardRepository;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import static com.bmc.flow.modules.service.reflection.MethodNames.SET_COMMENT;
import static com.bmc.flow.modules.service.reflection.MethodNames.SET_VOTES;
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

  @WithTransaction
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
  @WithTransaction
  protected Uni<Void> update(final RetroCardEntity toUpdate, final String key, final String value) {
    return switch (key) {
      case "comment" -> updateInplace(toUpdate, SET_COMMENT, value);
      case "votes" -> updateInplace(toUpdate, SET_VOTES, Short.parseShort(value));

      default -> throw new IllegalStateException("Unexpected value: " + key);
    };
  }
}
