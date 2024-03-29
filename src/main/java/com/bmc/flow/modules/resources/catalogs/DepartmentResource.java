package com.bmc.flow.modules.resources.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.DepartmentDto;
import com.bmc.flow.modules.database.entities.catalogs.DepartmentEntity;
import com.bmc.flow.modules.resources.base.BasicCatalogResource;
import com.bmc.flow.modules.service.catalogs.DepartmentService;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

/**
 * this class is the rest resource handling department requests.
 */
@Path("/v1/department")
@Produces("application/json")
public class DepartmentResource extends BasicCatalogResource<DepartmentDto, DepartmentEntity> {

    public DepartmentResource(final DepartmentService departmentService) {
        super(departmentService);
    }

}
