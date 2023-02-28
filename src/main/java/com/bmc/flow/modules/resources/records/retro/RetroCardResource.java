package com.bmc.flow.modules.resources.records.retro;


import com.bmc.flow.modules.database.dto.records.retro.RetroCardDto;
import com.bmc.flow.modules.database.entities.records.retro.RetroCardEntity;
import com.bmc.flow.modules.resources.base.BasicOpsResource;
import com.bmc.flow.modules.resources.utils.ResponseUtils;
import com.bmc.flow.modules.service.records.retro.RetroCardService;
import io.smallrye.mutiny.Uni;

import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/v1/retrocard")
@Produces("application/json")
public class RetroCardResource extends BasicOpsResource<RetroCardDto, RetroCardEntity> {

  private final RetroCardService retroCardService;

  public RetroCardResource(final RetroCardService retroCardService) {
    super(retroCardService);
    this.retroCardService = retroCardService;
  }

  @GET
  @Path("retroboard/{retroBoardId}")
  public Uni<Response> findAllByRetroBoardId(final UUID retroBoardId) {
    return retroCardService.findAllByRetroBoardId(retroBoardId)
                           .map(retroCardDtos -> Response.ok(retroCardDtos).build())
                           .onFailure(NoResultException.class).recoverWithItem(Response.status(NOT_FOUND)::build)
                           .onFailure().recoverWithItem(ResponseUtils::failToServerError);
  }

}
