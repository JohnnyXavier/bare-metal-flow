package com.bmc.flow.modules.service.records;

import com.bmc.flow.modules.database.dto.records.CardDto;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.catalogs.CardDifficultyEntity;
import com.bmc.flow.modules.database.entities.catalogs.CardStatusEntity;
import com.bmc.flow.modules.database.entities.catalogs.LabelEntity;
import com.bmc.flow.modules.database.entities.records.BoardEntity;
import com.bmc.flow.modules.database.entities.records.CardEntity;
import com.bmc.flow.modules.database.repositories.catalogs.CardStatusRepository;
import com.bmc.flow.modules.database.repositories.records.CardRepository;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.time.LocalDateTime.parse;
import static java.util.UUID.randomUUID;

@ApplicationScoped
public class CardService extends BasicPersistenceService<CardDto, CardEntity> {

  private final CardRepository cardRepo;

  private final CardStatusRepository cardStatusRepo;

  public CardService(final CardRepository cardRepo, final CardStatusRepository cardStatusRepo) {
    super(cardRepo, CardDto.class);
    this.cardRepo = cardRepo;
    this.cardStatusRepo = cardStatusRepo;
  }

  public Uni<List<CardDto>> findAllCardsByBoardId(final UUID boardId) {
    return cardRepo.findAllByBoardId(boardId);
  }

  @ReactiveTransactional
  public Uni<CardDto> create(@Valid final CardDto cardDto) {

    UserEntity cardCreator = new UserEntity();
    cardCreator.setId(cardDto.getCreatedBy());

    BoardEntity board = new BoardEntity();
    board.setId(cardDto.getBoardId());

    CardEntity newCard = new CardEntity();
    newCard.setId(randomUUID());
    newCard.setName(cardDto.getName());
    newCard.setDescription(cardDto.getDescription());
    newCard.setCreatedBy(cardCreator);
    newCard.setDueDate(cardDto.getDueDate());
    newCard.setCompletedDate(cardDto.getCompletedDate());
    newCard.setBoard(board);

    Optional.ofNullable(cardDto.getCardStatusId())
            .ifPresent(statusId -> {
              CardStatusEntity cardStatus = new CardStatusEntity();
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
                               CardStatusEntity cardStatus = new CardStatusEntity();
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
