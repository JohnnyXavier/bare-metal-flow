package com.bmc.flow.modules.database.repositories.records;

import com.bmc.flow.modules.database.entities.records.CommentEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class CommentRepository implements PanacheRepositoryBase<CommentEntity, UUID> {

  private static final String SELECT_DTO = " select e.id, e.comment,e.createdAt, e.createdBy.id, e.card.id";

  private static final String FROM_ENTITY = " from CommentEntity e";

}
