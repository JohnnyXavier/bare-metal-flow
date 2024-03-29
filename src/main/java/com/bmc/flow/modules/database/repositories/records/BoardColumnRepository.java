package com.bmc.flow.modules.database.repositories.records;

import com.bmc.flow.modules.database.entities.catalogs.BoardColumnEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

/**
 * this class is the data access for board column data.
 */
@ApplicationScoped
public class BoardColumnRepository implements PanacheRepositoryBase<BoardColumnEntity, UUID> {

    private static final String SELECT_DTO  = " select e.id, e.name, e.createdAt, e.createdBy.id, e.board.id, e.status.id, e.status.name";
    private static final String FROM_ENTITY = " from BoardColumnEntity e";

}
