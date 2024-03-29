package com.bmc.flow.modules.resources.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.LabelDto;
import com.bmc.flow.modules.database.entities.catalogs.LabelEntity;
import com.bmc.flow.modules.resources.base.BasicCatalogResource;
import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.resources.utils.ResponseUtils;
import com.bmc.flow.modules.service.catalogs.LabelService;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

/**
 * this class is the rest resource handling Label requests.
 */
@Path("/v1/label")
@Produces("application/json")
public class LabelResource extends BasicCatalogResource<LabelDto, LabelEntity> {

    private final LabelService labelService;

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
