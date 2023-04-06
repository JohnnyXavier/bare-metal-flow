package com.bmc.flow.modules.service.records;

import com.bmc.flow.modules.database.dto.records.CardSimpleDto;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.catalogs.CardDifficultyEntity;
import com.bmc.flow.modules.database.entities.catalogs.StatusEntity;
import com.bmc.flow.modules.database.entities.catalogs.LabelEntity;
import com.bmc.flow.modules.database.entities.records.BoardEntity;
import com.bmc.flow.modules.database.entities.records.CardEntity;
import com.bmc.flow.modules.database.repositories.catalogs.CardStatusRepository;
import com.bmc.flow.modules.database.repositories.records.CardRepository;
import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import com.bmc.flow.modules.service.base.PageResult;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import static java.time.LocalDateTime.parse;
import static java.util.UUID.randomUUID;

@ApplicationScoped
public class CardService extends BasicPersistenceService<CardSimpleDto, CardEntity> {

  private final CardRepository cardRepo;

  private final CardStatusRepository cardStatusRepo;

  public CardService(final CardRepository cardRepo, final CardStatusRepository cardStatusRepo) {
    super(cardRepo, CardSimpleDto.class);
    this.cardRepo       = cardRepo;
    this.cardStatusRepo = cardStatusRepo;
  }

  public Uni<PageResult<CardSimpleDto>> findAllByBoardIdPaged(final UUID boardId, final Pageable pageable) {
    return findAllPaged(cardRepo.findAllByBoardId(boardId, pageable.getSort()), "-all-cards-by-board",
        pageable.getPage());
  }

  @ReactiveTransactional
  public Uni<CardSimpleDto> create(@Valid final CardSimpleDto cardSimpleDto) {

    UserEntity cardCreator = new UserEntity();
    cardCreator.setId(cardSimpleDto.getCreatedBy());

    BoardEntity board = new BoardEntity();
    board.setId(cardSimpleDto.getBoardId());

    CardEntity newCard = new CardEntity();
    newCard.setId(randomUUID());
    newCard.setName(cardSimpleDto.getName());
    newCard.setDescription(cardSimpleDto.getDescription());
    newCard.setCreatedBy(cardCreator);
    newCard.setDueDate(cardSimpleDto.getDueDate());
    newCard.setBoard(board);

    Optional.ofNullable(cardSimpleDto.getCardStatusId())
        .ifPresent(statusId -> {
          StatusEntity cardStatus = new StatusEntity();
          cardStatus.setId(statusId);
          newCard.setCardStatus(cardStatus);
        });

    return cardRepo.persist(newCard)
        //.invoke(() -> newCard.setBoard(board))
        .replaceWith(findById(newCard.getId()));
  }

  @Override
  protected void updateField(final CardEntity toUpdate, final String key, final String value) {
    switch (key) {
      case "name" -> toUpdate.setName(value);
      case "description" -> toUpdate.setDescription(value);
      case "coverImage" -> toUpdate.setCoverImage(value);
      case "dueDate" -> toUpdate.setDueDate(value == null ? null : parse(value));
      case "completedDate" -> toUpdate.setCompletedDate(value == null ? null : parse(value));
      case "status" -> setStatus(toUpdate, value);
      case "difficulty" -> setDifficulty(toUpdate, value);
      case "addLabel" -> addLabel(toUpdate, value);
      case "removeLabel" -> removeLabel(toUpdate, value);
      case "addWatcher" -> addWatcher(toUpdate, value);
      case "removeWatcher" -> removeWatcher(toUpdate, value);
      case "addAssignee" -> addAssignee(toUpdate, value);
      case "removeAssignee" -> removeAssignee(toUpdate, value);

      default -> throw new IllegalStateException("Unexpected value: " + key);
    }
  }

  private void removeAssignee(final CardEntity toUpdate, final String value) {
    UserEntity user = new UserEntity();
    user.setId(UUID.fromString(value));

    toUpdate.getAssignees().remove(user);
  }

  private void addAssignee(final CardEntity toUpdate, final String value) {
    UserEntity user = new UserEntity();
    user.setId(UUID.fromString(value));

    toUpdate.getAssignees().add(user);
  }

  private void removeWatcher(final CardEntity toUpdate, final String value) {
    UserEntity user = new UserEntity();
    user.setId(UUID.fromString(value));

    toUpdate.getWatchers().remove(user);
  }

  private void addWatcher(final CardEntity toUpdate, final String value) {
    UserEntity user = new UserEntity();
    user.setId(UUID.fromString(value));

    toUpdate.getWatchers().add(user);
  }

  private void setDifficulty(final CardEntity toUpdate, final String value) {
    Optional.ofNullable(value)
        .ifPresentOrElse(difficulty -> {
              CardDifficultyEntity cardDifficulty = new CardDifficultyEntity();
              cardDifficulty.setId(UUID.fromString(difficulty));
              toUpdate.setCardDifficulty(cardDifficulty);
            },
            () -> toUpdate.setCardDifficulty(null));
  }


  private void setStatus(final CardEntity toUpdate, final String value) {
    Optional.ofNullable(value)
        .ifPresentOrElse(status -> {
              StatusEntity cardStatus = new StatusEntity();
              cardStatus.setId(UUID.fromString(status));
              toUpdate.setCardStatus(cardStatus);
            },
            () -> toUpdate.setCardStatus(null));
  }

  private void removeLabel(final CardEntity toUpdate, final String value) {
    LabelEntity label = new LabelEntity();
    label.setId(UUID.fromString(value));

    toUpdate.getLabels().remove(label);
  }

  private void addLabel(final CardEntity toUpdate, final String value) {
    LabelEntity label = new LabelEntity();
    label.setId(UUID.fromString(value));

    toUpdate.getLabels().add(label);
  }

}
