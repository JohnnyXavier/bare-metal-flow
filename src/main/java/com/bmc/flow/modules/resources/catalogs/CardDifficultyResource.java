package com.bmc.flow.modules.resources.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.CardDifficultyDto;
import com.bmc.flow.modules.database.entities.catalogs.CardDifficultyEntity;
import com.bmc.flow.modules.resources.base.BasicCatalogResource;
import com.bmc.flow.modules.service.catalogs.CardDifficultyService;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/v1/cardDifficulty")
@Produces("application/json")
public class CardDifficultyResource extends BasicCatalogResource<CardDifficultyDto, CardDifficultyEntity> {

    private final CardDifficultyService cardDifficultyService;

    public CardDifficultyResource(final CardDifficultyService cardDifficultyService) {
        super(cardDifficultyService);
        this.cardDifficultyService = cardDifficultyService;
    }

}
