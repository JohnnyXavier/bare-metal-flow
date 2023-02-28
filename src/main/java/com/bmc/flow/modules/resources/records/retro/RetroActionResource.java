package com.bmc.flow.modules.resources.records.retro;


import com.bmc.flow.modules.database.dto.records.retro.RetroActionDto;
import com.bmc.flow.modules.database.entities.records.retro.RetroActionEntity;
import com.bmc.flow.modules.resources.base.BasicOpsResource;
import com.bmc.flow.modules.resources.utils.ResponseUtils;
import com.bmc.flow.modules.service.records.retro.RetroActionService;
import io.smallrye.mutiny.Uni;

import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/v1/retroaction")
@Produces("application/json")
public class RetroActionResource extends BasicOpsResource<RetroActionDto, RetroActionEntity> {

  private final RetroActionService retroActionService;

  public RetroActionResource(final RetroActionService retroActionService) {
    super(retroActionService);
    this.retroActionService = retroActionService;
  }


  @GET
  @Path("project/{projectId}")
  public Uni<Response> findAllByProjectId(final UUID projectId) {
    return retroActionService.findAllByProjectId(projectId)
                             .map(retroActionDtos -> Response.ok(retroActionDtos).build())
                             .onFailure(NoResultException.class).recoverWithItem(Response.status(NOT_FOUND)::build)
                             .onFailure().recoverWithItem(ResponseUtils::failToServerError);
  }

  @GET
  @Path("retroboard/{retroBoardId}")
  public Uni<Response> findAllByRetroBoardId(final UUID retroBoardId) {
    return retroActionService.findAllByRetroBoardId(retroBoardId)
                             .map(retroActionDtos -> Response.ok(retroActionDtos).build())
                             .onFailure(NoResultException.class).recoverWithItem(Response.status(NOT_FOUND)::build)
                             .onFailure().recoverWithItem(ResponseUtils::failToServerError);
  }

}
