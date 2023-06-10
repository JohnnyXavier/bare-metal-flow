package com.bmc.flow.modules.resources.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.StatusDto;
import com.bmc.flow.modules.database.entities.catalogs.StatusEntity;
import com.bmc.flow.modules.resources.base.BasicCatalogResource;
import com.bmc.flow.modules.service.catalogs.CardStatusService;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

/**
 * this class is the rest resource handling card status requests.
 */
@Path("/v1/cardStatus")
@Produces("application/json")
public class CardStatusResource extends BasicCatalogResource<StatusDto, StatusEntity> {

    public CardStatusResource(final CardStatusService cardStatusService) {
        super(cardStatusService);
    }

}
