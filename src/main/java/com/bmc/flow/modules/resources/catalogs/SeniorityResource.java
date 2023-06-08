package com.bmc.flow.modules.resources.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.SeniorityDto;
import com.bmc.flow.modules.database.entities.catalogs.SeniorityEntity;
import com.bmc.flow.modules.resources.base.BasicCatalogResource;
import com.bmc.flow.modules.service.catalogs.SeniorityService;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/v1/seniority")
@Produces("application/json")
public class SeniorityResource extends BasicCatalogResource<SeniorityDto, SeniorityEntity> {

    private final SeniorityService seniorityService;

    public SeniorityResource(final SeniorityService seniorityService) {
        super(seniorityService);
        this.seniorityService = seniorityService;
    }

}
