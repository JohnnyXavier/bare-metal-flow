package com.bmc.flow.modules.resources.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.LabelDto;
import com.bmc.flow.modules.database.entities.catalogs.LabelEntity;
import com.bmc.flow.modules.resources.base.BasicCatalogResource;
import com.bmc.flow.modules.service.catalogs.LabelService;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/v1/label")
@Produces("application/json")
public class LabelResource extends BasicCatalogResource<LabelDto, LabelEntity> {

  private final LabelService labelService;

  public LabelResource(final LabelService labelService) {
    super(labelService);
    this.labelService = labelService;
  }

}
