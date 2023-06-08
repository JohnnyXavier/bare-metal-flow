package com.bmc.flow.modules.resources;

import com.bmc.flow.modules.database.dto.ChangeLogCardDto;
import com.bmc.flow.modules.database.entities.ChangelogEntity;
import com.bmc.flow.modules.resources.base.BasicOpsResource;
import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.resources.utils.ResponseUtils;
import com.bmc.flow.modules.service.ChangeLogService;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/v1/changelog")
@Produces("application/json")
public class ChangeLogResource extends BasicOpsResource<ChangeLogCardDto, ChangelogEntity> {

    private final ChangeLogService changeLogService;

    public ChangeLogResource(ChangeLogService changeLogService) {
        super(changeLogService);
        this.changeLogService = changeLogService;
    }

    @GET
    @Path("card/{cardId}")
    public Uni<Response> findChangelogByCardId(final UUID cardId,
                                               @QueryParam(value = "sortBy") @NotNull final String sortBy,
                                               @QueryParam(value = "sortDir") final String sortDir,
                                               @QueryParam(value = "pageIx") final Integer pageIx,
                                               @QueryParam(value = "pageSize") @NotNull final Integer pageSize) {
        return changeLogService.findAllByCardId(cardId, new Pageable(sortBy, sortDir, pageIx, pageSize))
                               .map(changeLogDtos -> Response.ok(changeLogDtos).build())
                               .onFailure().recoverWithItem(ResponseUtils::failToServerError);
    }
}
