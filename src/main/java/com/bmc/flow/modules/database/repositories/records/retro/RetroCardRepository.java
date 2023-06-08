package com.bmc.flow.modules.database.repositories.records.retro;

import com.bmc.flow.modules.database.dto.records.retro.RetroCardDto;
import com.bmc.flow.modules.database.entities.records.retro.RetroCardEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class RetroCardRepository implements PanacheRepositoryBase<RetroCardEntity, UUID> {

    private static final String SELECT_DTO = " select e.id, e.comment, e.votes, e.createdAt, e.createdBy.id, e.retroBoard.id";

    private static final String FROM_ENTITY = " from RetroCardEntity e";


    public Uni<List<RetroCardDto>> findAllByRetroBoardId(final UUID retroBoardId) {
        return this.find(SELECT_DTO + FROM_ENTITY +
                       " where e.retroBoard.id =?1", retroBoardId)
                   .project(RetroCardDto.class)
                   .list();
    }

}
