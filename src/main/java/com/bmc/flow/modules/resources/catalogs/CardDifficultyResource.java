package com.bmc.flow.modules.resources.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.CardDifficultyDto;
import com.bmc.flow.modules.database.entities.catalogs.CardDifficultyEntity;
import com.bmc.flow.modules.resources.base.BasicCatalogResource;
import com.bmc.flow.modules.service.catalogs.CardDifficultyService;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

/**
 * this class is the rest resource handling card difficulty requests.
 * <br><br>
 * showcase / tech guide note:<br>
 * we are not directly using the service at all in this class, as no particular request is needed, but it would seem that because we need it
 * for constructing the extending class we have to have it.<br><br>
 * but do we? <br><br>
 * quarkus (arc) injects the service on the constructor already when instantiating this class, and we pass that to the
 * {@link BasicCatalogResource}.<br>
 * we actually don't need the field at all.<br>
 * this class will keep the service as part of the showcase / guide notes, the rest of the resources however, won't have their
 * corresponding service as a field if they do not explicitly need it.
 */
@Path("/v1/cardDifficulty")
@Produces("application/json")
public class CardDifficultyResource extends BasicCatalogResource<CardDifficultyDto, CardDifficultyEntity> {

    private final CardDifficultyService cardDifficultyService;

    public CardDifficultyResource(final CardDifficultyService cardDifficultyService) {
        super(cardDifficultyService);
        this.cardDifficultyService = cardDifficultyService;
    }

}
