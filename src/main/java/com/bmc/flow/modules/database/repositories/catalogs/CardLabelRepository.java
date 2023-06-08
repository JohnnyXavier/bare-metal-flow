package com.bmc.flow.modules.database.repositories.catalogs;

import com.bmc.flow.modules.database.entities.records.CardLabelEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class CardLabelRepository implements PanacheRepositoryBase<CardLabelEntity, UUID> {

    private static final String SELECT_DTO  = " select e.id.cardId, e.id.labelId, e.board.id e.createdAt, e.createdBy.id";
    private static final String FROM_ENTITY = " from CardLabelEntity e";

}
