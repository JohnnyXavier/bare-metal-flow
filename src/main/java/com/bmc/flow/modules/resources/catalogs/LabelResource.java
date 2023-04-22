package com.bmc.flow.modules.resources.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.LabelDto;
import com.bmc.flow.modules.database.entities.catalogs.LabelEntity;
import com.bmc.flow.modules.resources.base.BasicCatalogResource;
import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.resources.utils.ResponseUtils;
import com.bmc.flow.modules.service.catalogs.LabelService;
import io.smallrye.mutiny.Uni;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.AbstractMap;
import java.util.Map;
import java.util.UUID;

import static java.util.Map.ofEntries;

@Path("/v1/label")
@Produces("application/json")
public class LabelResource extends BasicCatalogResource<LabelDto, LabelEntity> {

  private final LabelService labelService;

  private final Map<String, String> userSupportedCollections = ofEntries(
      new AbstractMap.SimpleImmutableEntry<>("card", "card")
  );

  public LabelResource(final LabelService labelService) {
    super(labelService);
    this.labelService = labelService;
  }

  @GET
  @Path("card/{id}")
  public Uni<Response> findAllByCollectionId(final UUID id,
                                             @QueryParam(value = "sortBy") @NotNull final String sortBy,
                                             @QueryParam(value = "sortDir") final String sortDir,
                                             @QueryParam(value = "pageIx") final Integer pageIx,
                                             @QueryParam(value = "pageSize") @NotNull final Integer pageSize) {

      return labelService.findAllByCardIdPaged(id, new Pageable(sortBy, sortDir, pageIx, pageSize))
          .map(resultDtos -> Response.ok(resultDtos).build())
          .onFailure().recoverWithItem(ResponseUtils::failToServerError);

  }
  }
