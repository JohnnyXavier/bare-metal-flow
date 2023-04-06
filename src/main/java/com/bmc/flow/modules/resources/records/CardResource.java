package com.bmc.flow.modules.resources.records;

import com.bmc.flow.modules.database.dto.records.CardSimpleDto;
import com.bmc.flow.modules.database.entities.records.CardEntity;
import com.bmc.flow.modules.resources.base.BasicOpsResource;
import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.resources.utils.ResponseUtils;
import com.bmc.flow.modules.service.records.CardService;
import io.smallrye.mutiny.Uni;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.UUID;

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
