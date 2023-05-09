package com.bmc.flow.modules.resources.records.retro;


import com.bmc.flow.modules.database.dto.records.retro.RetrospectiveDto;
import com.bmc.flow.modules.database.entities.records.retro.RetrospectiveEntity;
import com.bmc.flow.modules.resources.base.BasicOpsResource;
import com.bmc.flow.modules.resources.utils.ResponseUtils;
import com.bmc.flow.modules.service.records.retro.RetrospectiveService;
import io.smallrye.mutiny.Uni;

import jakarta.persistence.NoResultException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/v1/retroboard")
@Produces("application/json")
public class RetrospectiveResource extends BasicOpsResource<RetrospectiveDto, RetrospectiveEntity> {

  private final RetrospectiveService retrospectiveService;

  public RetrospectiveResource(final RetrospectiveService retrospectiveService) {
    super(retrospectiveService);
    this.retrospectiveService = retrospectiveService;
  }

  @GET
  @Path("project/{projectId}")
  public Uni<Response> findAllByProjectId(final UUID projectId) {
    return retrospectiveService.findAllByProjectId(projectId)
                               .map(retroBoardDtos -> Response.ok(retroBoardDtos).build())
                               .onFailure(NoResultException.class).recoverWithItem(Response.status(NOT_FOUND)::build)
                               .onFailure().recoverWithItem(ResponseUtils::failToServerError);
  }

  @GET
  @Path("sprintboard/{sprintBoardId}")
  public Uni<Response> findAllBySprintBoardId(final UUID sprintBoardId) {
    return retrospectiveService.findBySprintBoardId(sprintBoardId)
                               .map(retrospectiveDto -> Response.ok(retrospectiveDto).build())
                               .onFailure(NoResultException.class).recoverWithItem(Response.status(NOT_FOUND)::build)
                               .onFailure().recoverWithItem(ResponseUtils::failToServerError);
  }

}
