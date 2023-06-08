package com.bmc.flow.modules.database.repositories.records;

import com.bmc.flow.modules.database.entities.records.CommentEntity;
import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

/**
 * this class is the data access for comment data.
 */
@ApplicationScoped
public class CommentRepository implements PanacheRepositoryBase<CommentEntity, UUID> {

    private static final String SELECT_DTO = " select e.id, e.comment, e.createdAt, e.createdBy.id, e.createdBy.callSign," +
        " e.createdBy.avatar, e.createdBy.isActive, e.card.id";

    private static final String FROM_ENTITY = " from CommentEntity e";

    public PanacheQuery<CommentEntity> findAllByCardId(UUID cardId, Sort sort) {
        return this.find(SELECT_DTO + FROM_ENTITY +
            " where e.card.id =?1", sort, cardId);
    }
}
