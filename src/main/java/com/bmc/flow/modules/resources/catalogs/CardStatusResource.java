package com.bmc.flow.modules.resources.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.StatusDto;
import com.bmc.flow.modules.database.entities.catalogs.StatusEntity;
import com.bmc.flow.modules.resources.base.BasicCatalogResource;
import com.bmc.flow.modules.service.catalogs.CardStatusService;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/v1/cardStatus")
@Produces("application/json")
public class CardStatusResource extends BasicCatalogResource<StatusDto, StatusEntity> {

    private final CardStatusService cardStatusService;

    public CardStatusResource(final CardStatusService cardStatusService) {
        super(cardStatusService);
        this.cardStatusService = cardStatusService;
    }

}
