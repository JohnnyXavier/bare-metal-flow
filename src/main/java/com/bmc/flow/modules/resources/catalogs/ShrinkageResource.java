package com.bmc.flow.modules.resources.catalogs;

import com.bmc.flow.modules.database.dto.resourcing.ShrinkageDto;
import com.bmc.flow.modules.database.entities.resourcing.ShrinkageEntity;
import com.bmc.flow.modules.resources.base.BasicCatalogResource;
import com.bmc.flow.modules.service.resourcing.ShrinkageService;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

/**
 * this class is the rest resource handling shrinkage requests.
 */
@Path("/v1/shrinkage")
@Produces("application/json")
public class ShrinkageResource extends BasicCatalogResource<ShrinkageDto, ShrinkageEntity> {

    public ShrinkageResource(final ShrinkageService shrinkageService) {
        super(shrinkageService);
    }

}
