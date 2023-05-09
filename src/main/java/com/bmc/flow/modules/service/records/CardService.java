package com.bmc.flow.modules.service.records;

import com.bmc.flow.modules.database.dto.records.CardLabelDto;
import com.bmc.flow.modules.database.dto.records.CardSimpleDto;
import com.bmc.flow.modules.database.entities.ChangelogEntity;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.catalogs.BoardColumnEntity;
import com.bmc.flow.modules.database.entities.catalogs.CardDifficultyEntity;
import com.bmc.flow.modules.database.entities.catalogs.ChangeAction;
import com.bmc.flow.modules.database.entities.catalogs.StatusEntity;
import com.bmc.flow.modules.database.entities.records.BoardEntity;
import com.bmc.flow.modules.database.entities.records.CardEntity;
import com.bmc.flow.modules.database.repositories.catalogs.CardLabelRepository;
import com.bmc.flow.modules.database.repositories.catalogs.StatusRepository;
import com.bmc.flow.modules.database.repositories.records.CardRepository;
import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import com.bmc.flow.modules.service.base.PageResult;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.extern.jbosslog.JBossLog;
import org.hibernate.reactive.mutiny.Mutiny;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.bmc.flow.modules.database.entities.catalogs.ChangeAction.CREATED;
import static java.time.LocalDateTime.parse;
import static java.util.UUID.randomUUID;

@ApplicationScoped
@JBossLog
public class CardService extends BasicPersistenceService<CardSimpleDto, CardEntity> {

  private final CardRepository        cardRepo;
  private final CardLabelRepository   cardLabelRepo;
  private final StatusRepository      cardStatusRepo;
  private final Mutiny.Session        session;
  private final Mutiny.SessionFactory sessionFactory;

  public CardService(final CardRepository cardRepo, CardLabelRepository cardLabelRepo, final StatusRepository cardStatusRepo,
                     Mutiny.Session session,
                     Mutiny.SessionFactory sessionFactory) {
    super(cardRepo, CardSimpleDto.class);
    this.cardRepo       = cardRepo;
    this.cardLabelRepo  = cardLabelRepo;
    this.cardStatusRepo = cardStatusRepo;
    this.session        = session;
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Uni<CardSimpleDto> findById(UUID id) {
    return cardRepo.find("id", id)
        .project(CardSimpleDto.class)
        .singleResult().onItem()
        .ifNotNull().call(card -> session.createNamedQuery("CardLabel.findAllByCardId", CardLabelDto.class)
            .setParameter("id", id)
            .getResultList()
            .call(cardLabelDtos -> Uni.createFrom().item(cardLabelDtos
                .stream()
                .map(cardLabelDto -> {
                  if (cardLabelDto.getCardId().equals(card.getId())) {
                    card.getLabels().add(cardLabelDto);
                  }
                  return null;
                }).toList())));
  }

  public Uni<PageResult<CardSimpleDto>> findAllByBoardIdPaged(final UUID boardId, final Pageable pageable) {
    return findAllPaged(cardRepo.findAllByBoardId(boardId, pageable.getSort()), "-all-cards-by-board",
        pageable.getPage())
        .onItem()
        .ifNotNull().call(result -> session.createNamedQuery("CardLabel.findAllByBoardId", CardLabelDto.class)
            .setParameter("boardId", boardId)
            .getResultList()
            .call(cardLabelDtos -> Uni.createFrom().item(cardLabelDtos
                .stream()
                .map(cardLabelDto -> {
                  result.getResultSet().forEach(cardSimpleDto -> {
                    if (cardLabelDto.getCardId().equals(cardSimpleDto.getId())) {cardSimpleDto.getLabels().add(cardLabelDto);}
                  });
                  return null;
                }).toList())));
  }

  @WithTransaction
  public Uni<CardSimpleDto> create(@Valid final CardSimpleDto cardSimpleDto) {

    UserEntity cardCreator = new UserEntity();
    cardCreator.setId(cardSimpleDto.getCreatedBy());

    BoardEntity board = new BoardEntity();
    board.setId(cardSimpleDto.getBoardId());

    BoardColumnEntity boardColumn = new BoardColumnEntity();
    boardColumn.setId(cardSimpleDto.getBoardColumnId());

    StatusEntity status = new StatusEntity();
    status.setId(cardSimpleDto.getCardStatusId());

    CardEntity newCard = new CardEntity();
    newCard.setId(randomUUID());
    newCard.setName(cardSimpleDto.getName());
    newCard.setCreatedBy(cardCreator);
    newCard.setBoard(board);
    newCard.setBoardColumn(boardColumn);
    newCard.setCardStatus(status);

    ChangelogEntity newChangeLog = new ChangelogEntity();
    newChangeLog.setId(randomUUID());
    newChangeLog.setCard(newCard);
    newChangeLog.setChangeAction(CREATED);
    newChangeLog.setField("card");
    newChangeLog.setCreatedBy(cardCreator);

    newCard.getChangelog().add(newChangeLog);

    return cardRepo.persist(newCard)
        .chain(() -> cardStatusRepo.findById(cardSimpleDto.getCardStatusId()).invoke(newCard::setCardStatus))
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
    sessionFactory.withTransaction(txSession ->
            txSession.createNativeQuery("insert into card_users_assigned(card_id, user_id) VALUES " +
                    " (?1, ?2)")
                .setParameter(1, toUpdate.getId())
                .setParameter(2, UUID.fromString(value))
                .executeUpdate()
                .chain(() -> session.createNamedQuery("Changelog.Create")
                    .setParameter(1, value)
                    .setParameter(2, "assignee")
                    .setParameter(3, toUpdate.getCreatedBy().getId())
                    .setParameter(4, toUpdate.getId())
                    .setParameter(5, ChangeAction.REMOVED.ordinal())
                    .executeUpdate())
                .chain(() -> session.createNativeQuery("update card set updated_at = ?1 where id = ?2")
                    .setParameter(1, LocalDateTime.now())
                    .setParameter(2, toUpdate.getId())
                    .executeUpdate()))
        .subscribe()
        .with(log::info, log::error);
  }

  private void addAssignee(final CardEntity toUpdate, final String value) {
    sessionFactory.withTransaction(txSession ->
            txSession.createNativeQuery("delete from card_users_assigned where card_id=?1 and user_id=?2")
                .setParameter(1, toUpdate.getId())
                .setParameter(2, UUID.fromString(value))
                .executeUpdate()
                .chain(() -> session.createNamedQuery("Changelog.Create")
                    .setParameter(1, value)
                    .setParameter(2, "assignee")
                    .setParameter(3, toUpdate.getCreatedBy().getId())
                    .setParameter(4, toUpdate.getId())
                    .setParameter(5, ChangeAction.ADDED.ordinal())
                    .executeUpdate())
                .chain(() -> session.createNativeQuery("update card set updated_at = ?1 where id = ?2")
                    .setParameter(1, LocalDateTime.now())
                    .setParameter(2, toUpdate.getId())
                    .executeUpdate()))
        .subscribe()
        .with(log::info, log::error);
  }

  private void removeWatcher(final CardEntity toUpdate, final String value) {
    sessionFactory.withTransaction(txSession ->
            txSession.createNativeQuery("delete from card_users_watchers where card_id=?1 and user_id=?2")
                .setParameter(1, toUpdate.getId())
                .setParameter(2, UUID.fromString(value))
                .executeUpdate()
                .chain(() -> session.createNamedQuery("Changelog.Create")
                    .setParameter(1, value)
                    .setParameter(2, "watcher")
                    .setParameter(3, toUpdate.getCreatedBy().getId())
                    .setParameter(4, toUpdate.getId())
                    .setParameter(5, ChangeAction.REMOVED.ordinal())
                    .executeUpdate())
                .chain(() -> session.createNativeQuery("update card set updated_at = ?1 where id = ?2")
                    .setParameter(1, LocalDateTime.now())
                    .setParameter(2, toUpdate.getId())
                    .executeUpdate()))
        .subscribe()
        .with(log::info, log::error);
  }

  private void addWatcher(final CardEntity toUpdate, final String value) {
    sessionFactory.withTransaction(txSession ->
            txSession.createNativeQuery("insert into card_users_watchers(card_id, user_id) VALUES " +
                    " (?1, ?2)")
                .setParameter(1, toUpdate.getId())
                .setParameter(2, UUID.fromString(value))
                .executeUpdate()
                .chain(() -> session.createNamedQuery("Changelog.Create")
                    .setParameter(1, value)
                    .setParameter(2, "watcher")
                    .setParameter(3, toUpdate.getCreatedBy().getId())
                    .setParameter(4, toUpdate.getId())
                    .setParameter(5, ChangeAction.ADDED.ordinal())
                    .executeUpdate())
                .chain(() -> session.createNativeQuery("update card set updated_at = ?1 where id = ?2")
                    .setParameter(1, LocalDateTime.now())
                    .setParameter(2, toUpdate.getId())
                    .executeUpdate()))
        .subscribe()
        .with(log::info, log::error);

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
    sessionFactory.withTransaction((session, transaction) ->
            session
                .createNativeQuery("delete from card_label where card_id =?1 and label_id =?2")
                .setParameter(1, toUpdate.getId())
                .setParameter(2, UUID.fromString(value))
                .executeUpdate()
                .chain(() -> session.createNamedQuery("Changelog.Create")
                    .setParameter(1, value)
                    .setParameter(2, "label")
                    .setParameter(3, toUpdate.getCreatedBy().getId())
                    .setParameter(4, toUpdate.getId())
                    .setParameter(5, ChangeAction.REMOVED.ordinal())
                    .executeUpdate())
                .chain(() -> session.createNativeQuery("update card set updated_at = ?1 where id = ?2")
                    .setParameter(1, LocalDateTime.now())
                    .setParameter(2, toUpdate.getId())
                    .executeUpdate()))
        .subscribe()
        .with(log::info, log::error);
  }


  /**
   * Adding a label and creating the corresponding changelog
   * <p>
   * Showcase NOTE:<br>
   * This method serves to showcase full native SQL sequence inside a reactive transaction.<br>
   * Would there be a failure in the first insert, the chained query won't execute, and we'll have a clean db.
   *
   * @param toUpdate the card to update
   * @param value    the label to add
   */
  private void addLabel(final CardEntity toUpdate, final String value) {
    sessionFactory.withTransaction((session, transaction) ->
            session
                .createNativeQuery("insert into card_label(card_id, label_id, board_id, created_by_id, created_at) VALUES" +
                    " (?1, ?2, ?3, ?4, ?5 )")
                .setParameter(1, toUpdate.getId())
                .setParameter(2, value)
                .setParameter(3, toUpdate.getBoard().getId())
                .setParameter(4, toUpdate.getCreatedBy().getId())
                .setParameter(5, LocalDateTime.now())
                .executeUpdate()
                .chain(() -> session.createNamedQuery("Changelog.Create")
                    .setParameter(1, value)
                    .setParameter(2, "label")
                    .setParameter(3, toUpdate.getCreatedBy().getId())
                    .setParameter(4, toUpdate.getId())
                    .setParameter(5, ChangeAction.ADDED.ordinal())
                    .executeUpdate())
                .chain(() -> session.createNativeQuery("update card set updated_at = ?1 where id = ?2")
                    .setParameter(1, LocalDateTime.now())
                    .setParameter(2, toUpdate.getId())
                    .executeUpdate()))
        .subscribe()
        .with(log::info, log::error);
  }
}
