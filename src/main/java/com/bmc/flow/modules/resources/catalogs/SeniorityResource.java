package com.bmc.flow.modules.resources.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.SeniorityDto;
import com.bmc.flow.modules.database.entities.catalogs.SeniorityEntity;
import com.bmc.flow.modules.resources.base.BasicCatalogResource;
import com.bmc.flow.modules.service.catalogs.SeniorityService;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

/**
 * this class is the rest resource handling seniority requests.
 */
@Path("/v1/seniority")
@Produces("application/json")
public class SeniorityResource extends BasicCatalogResource<SeniorityDto, SeniorityEntity> {

    public SeniorityResource(final SeniorityService seniorityService) {
        super(seniorityService);
    }

}
