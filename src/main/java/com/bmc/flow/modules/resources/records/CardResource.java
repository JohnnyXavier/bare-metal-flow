package com.bmc.flow.modules.resources.records;

import com.bmc.flow.modules.database.dto.records.CardDto;
import com.bmc.flow.modules.database.entities.records.CardEntity;
import com.bmc.flow.modules.resources.base.BasicOpsResource;
import com.bmc.flow.modules.resources.utils.ResponseUtils;
import com.bmc.flow.modules.service.records.CardService;
import io.smallrye.mutiny.Uni;

import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/v1/card")
@Produces("application/json")
public class CardResource extends BasicOpsResource<CardDto, CardEntity> {

  private final CardService cardService;

  public CardResource(final CardService cardService) {
    super(cardService);
    this.cardService = cardService;
  }

  @GET
  @Path("board/{boardId}")
  public Uni<Response> findCardsByBoardId(final UUID boardId) {
    return cardService.findAllCardsByBoardId(boardId)
                      .map(cardDtos -> Response.ok(cardDtos).build())
                      .onFailure(NoResultException.class).recoverWithItem(Response.status(NOT_FOUND)::build)
                      .onFailure().recoverWithItem(ResponseUtils::failToServerError);
  }

}
