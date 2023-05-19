package com.bmc.flow.modules.service.records.retro;

import com.bmc.flow.modules.database.dto.records.retro.RetroActionDto;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.records.retro.RetroActionEntity;
import com.bmc.flow.modules.database.entities.records.retro.RetrospectiveEntity;
import com.bmc.flow.modules.database.repositories.records.retro.RetroActionRepository;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import static com.bmc.flow.modules.service.reflection.MethodNames.SET_ACTION_TO_TAKE;
import static java.util.UUID.randomUUID;

@ApplicationScoped
public class RetroActionService extends BasicPersistenceService<RetroActionDto, RetroActionEntity> {

  private final RetroActionRepository repository;


  public RetroActionService(final RetroActionRepository repository) {
    super(repository, RetroActionDto.class);
    this.repository = repository;
  }

  public Uni<List<RetroActionDto>> findAllByRetroBoardId(final UUID retroBoardId) {
    return repository.findAllByRetroBoardId(retroBoardId);
  }


  public Uni<List<RetroActionDto>> findAllByProjectId(final UUID projectId) {
    return repository.findAllByProjectId(projectId);
  }

  @WithTransaction
  public Uni<RetroActionDto> create(@Valid final RetroActionDto retroActionDto) {
    UserEntity cardCreator = new UserEntity();
    cardCreator.setId(retroActionDto.getCreatedBy());

    RetrospectiveEntity board = new RetrospectiveEntity();
    board.setId(retroActionDto.getRetroBoardId());

    RetroActionEntity newCard = new RetroActionEntity();
    newCard.setId(randomUUID());
    newCard.setActionToTake(retroActionDto.getActionToTake());
    newCard.setCreatedBy(cardCreator);
    newCard.setRetroBoard(board);

    return repository.persist(newCard)
        .replaceWith(findById(newCard.getId()));
  }

  @Override
  @WithTransaction
  protected Uni<Void> update(final RetroActionEntity toUpdate, final String key, final String value) {
    return switch (key) {
      case "actionToTake" -> updateInplace(toUpdate, SET_ACTION_TO_TAKE, value);

      default -> throw new IllegalStateException("Unexpected value: " + key);
    };
  }

}
