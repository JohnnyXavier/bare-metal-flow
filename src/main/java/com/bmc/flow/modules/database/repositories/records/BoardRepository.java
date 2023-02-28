package com.bmc.flow.modules.database.repositories.records;

import com.bmc.flow.modules.database.dto.records.BoardDto;
import com.bmc.flow.modules.database.entities.records.BoardEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class BoardRepository implements PanacheRepositoryBase<BoardEntity, UUID> {

  private static final String SELECT_DTO = " select e.id, e.name, e.description, e.coverImage, e.createdAt, e.createdBy.id, e.project.id," +
      " e.boardType.id";

  private static final String FROM_ENTITY = " from BoardEntity e";

  public Uni<List<BoardDto>> findAllCreatedByUserId(final UUID userId) {
    return this.find(SELECT_DTO + FROM_ENTITY +
                         " where e.createdBy.id =?1", userId)
               .project(BoardDto.class)
               .list();
  }

  public Uni<List<BoardDto>> findAllByProjectId(final UUID projectId) {
    return this.find(SELECT_DTO + FROM_ENTITY +
                         " where e.project.id =?1", projectId)
               .project(BoardDto.class)
               .list();
  }

}
