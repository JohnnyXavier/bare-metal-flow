package com.bmc.flow.modules.resources.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.CardTypeDto;
import com.bmc.flow.modules.database.entities.catalogs.CardTypeEntity;
import com.bmc.flow.modules.resources.base.BasicCatalogResource;
import com.bmc.flow.modules.service.catalogs.CardTypeService;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/v1/cardType")
@Produces("application/json")
public class CardTypeResource extends BasicCatalogResource<CardTypeDto, CardTypeEntity> {

    private final CardTypeService cardTypeService;

    public CardTypeResource(final CardTypeService cardTypeService) {
        super(cardTypeService);
        this.cardTypeService = cardTypeService;
    }

}
