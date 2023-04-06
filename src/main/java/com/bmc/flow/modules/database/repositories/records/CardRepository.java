package com.bmc.flow.modules.database.repositories.records;

import com.bmc.flow.modules.database.dto.records.CardSimpleDto;
import com.bmc.flow.modules.database.entities.records.CardEntity;
import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CardRepository implements PanacheRepositoryBase<CardEntity, UUID> {
  private static final String SELECT_DTO_SIMPLE = " select e.id, e.name, e.description, e.coverImage, e.dueDate, e.createdAt," +
      " e.createdBy.id, e.board.id, e.cardStatus.id, e.cardType.id, e.cardDifficulty.id";
  private static final String FROM_ENTITY       = " from CardEntity e";

  public Uni<List<CardSimpleDto>> findAllCreatedByUserId(final UUID userId) {
    return this.find(SELECT_DTO_SIMPLE + FROM_ENTITY +
            " where e.createdBy.id =?1", userId)
        .project(CardSimpleDto.class)
        .list();
  }

  public PanacheQuery<CardEntity> findAllByBoardId(final UUID boardId, final Sort sort) {
    return this.find(SELECT_DTO_SIMPLE + FROM_ENTITY +
        " where e.board.id =?1", sort, boardId);
  }

}
