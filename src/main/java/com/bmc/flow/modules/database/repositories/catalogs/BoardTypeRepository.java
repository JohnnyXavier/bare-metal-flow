package com.bmc.flow.modules.database.repositories.catalogs;

import com.bmc.flow.modules.database.entities.catalogs.BoardTypeEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class BoardTypeRepository implements PanacheRepositoryBase<BoardTypeEntity, UUID> {

  private static final String SELECT_DTO = " select e.id, e.name, e.description, e.isSystem, e.createdAt, e.createdBy.id";

  private static final String FROM_ENTITY = " from BoardTypeEntity e";

  public Uni<BoardTypeEntity> findEntityByName(final String name) {
    return this.find("select e " + FROM_ENTITY +
                         " where e.name =?1", name)
               .firstResult();
  }

}
