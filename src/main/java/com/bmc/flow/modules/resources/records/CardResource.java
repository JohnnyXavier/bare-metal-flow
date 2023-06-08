package com.bmc.flow.modules.resources.records;

import com.bmc.flow.modules.database.dto.records.CardSimpleDto;
import com.bmc.flow.modules.database.entities.records.CardEntity;
import com.bmc.flow.modules.resources.base.BasicOpsResource;
import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.resources.utils.ResponseUtils;
import com.bmc.flow.modules.service.records.CardService;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

/**
 * this class is the rest resource handling card requests.
 */
@Path("/v1/cardSimple")
@Produces("application/json")
public class CardResource extends BasicOpsResource<CardSimpleDto, CardEntity> {

    private final CardService cardService;

    public CardResource(final CardService cardService) {
        super(cardService);
        this.cardService = cardService;
    }

    @GET
    @Path("board/{boardId}")
    public Uni<Response> findCardsByBoardId(final UUID boardId,
                                            @QueryParam(value = "sortBy") @NotNull final String sortBy,
                                            @QueryParam(value = "sortDir") final String sortDir,
                                            @QueryParam(value = "pageIx") final Integer pageIx,
                                            @QueryParam(value = "pageSize") @NotNull final Integer pageSize) {
        return cardService.findAllByBoardIdPaged(boardId, new Pageable(sortBy, sortDir, pageIx, pageSize))
                          .map(cardDtos -> Response.ok(cardDtos).build())
                          .onFailure().recoverWithItem(ResponseUtils::failToServerError);
    }

}
