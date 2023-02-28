package com.bmc.flow.modules.database.repositories.records;

import com.bmc.flow.modules.database.dto.records.CardDto;
import com.bmc.flow.modules.database.entities.records.CardEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CardRepository implements PanacheRepositoryBase<CardEntity, UUID> {

  private static final String SELECT_DTO = " select e.id, e.name, e.description, e.coverImage, e.isCompleted, e.dueDate, e.completedDate" +
      "  e.estimatedTime, e.loggedTime, e.createdAt, e.createdBy.id, e.cardStatus.id, e.cardType.id, e.cardDifficulty.id";

  private static final String FROM_ENTITY = " from CardEntity e";

  public Uni<List<CardDto>> findAllCreatedByUserId(final UUID userId) {
    return this.find(SELECT_DTO + FROM_ENTITY +
                         " where e.createdBy.id =?1", userId)
               .project(CardDto.class)
               .list();
  }

  public Uni<List<CardDto>> findAllByBoardId(final UUID boardId) {
    return this.find(SELECT_DTO + FROM_ENTITY +
                         " where e.board.id =?1", boardId)
               .project(CardDto.class)
               .list();
  }

}
