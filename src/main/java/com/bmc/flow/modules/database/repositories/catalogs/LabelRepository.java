package com.bmc.flow.modules.database.repositories.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.LabelDto;
import com.bmc.flow.modules.database.entities.catalogs.LabelEntity;
import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class LabelRepository implements PanacheRepositoryBase<LabelEntity, UUID> {

  private static final String SELECT_DTO  = " select e.id, e.name, e.description, e.colorHex, e.isSystem, e.createdAt, e.createdBy.id";
  private static final String FROM_ENTITY = " from LabelEntity e";

  public Uni<List<LabelDto>> findAllCreatedByUserId(final UUID userId) {
    return this.find(SELECT_DTO + FROM_ENTITY +
            " where e.createdBy.id =?1", userId)
        .project(LabelDto.class)
        .list();
  }

  public PanacheQuery<LabelEntity> findAllByCardId(final UUID cardId, final Sort sort) {
    return this.find(SELECT_DTO + FROM_ENTITY +
        " join CardLabelEntity cl on e.id = cl.id.labelId" +
        " where cl.id.cardId =?1", sort, cardId);
  }


  public Uni<LabelEntity> findEntityByName(final String name) {
    return this.find("select e " + FROM_ENTITY +
            " where e.name =?1", name)
        .firstResult();
  }
}
