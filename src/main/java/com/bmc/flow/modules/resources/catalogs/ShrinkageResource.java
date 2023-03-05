package com.bmc.flow.modules.resources.catalogs;

import com.bmc.flow.modules.database.dto.resourcing.ShrinkageDto;
import com.bmc.flow.modules.database.entities.resourcing.ShrinkageEntity;
import com.bmc.flow.modules.resources.base.BasicCatalogResource;
import com.bmc.flow.modules.service.resourcing.ShrinkageService;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/v1/shrinkage")
@Produces("application/json")
public class ShrinkageResource extends BasicCatalogResource<ShrinkageDto, ShrinkageEntity> {

  private final ShrinkageService shrinkageService;

  public ShrinkageResource(final ShrinkageService shrinkageService) {
    super(shrinkageService);
    this.shrinkageService = shrinkageService;
  }

}
