package com.bmc.flow.modules.resources.records;

import com.bmc.flow.modules.database.dto.records.SprintDto;
import com.bmc.flow.modules.database.entities.records.SprintEntity;
import com.bmc.flow.modules.resources.base.BasicOpsResource;
import com.bmc.flow.modules.resources.utils.ResponseUtils;
import com.bmc.flow.modules.service.records.SprintService;
import io.smallrye.mutiny.Uni;

import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/v1/sprint")
@Produces("application/json")
public class SprintResource extends BasicOpsResource<SprintDto, SprintEntity> {

  private final SprintService sprintService;

  public SprintResource(final SprintService sprintService) {
    super(sprintService);
    this.sprintService = sprintService;
  }

  @GET
  @Path("project/{projectId}")
  public Uni<Response> findAllByProjectId(final UUID projectId) {
    return sprintService.findAllByProjectId(projectId)
                        .map(sprintBoardDtos -> Response.ok(sprintBoardDtos).build())
                        .onFailure(NoResultException.class).recoverWithItem(Response.status(NOT_FOUND)::build)
                        .onFailure().recoverWithItem(ResponseUtils::failToServerError);
  }

  @GET
  @Path("board/{boardId}")
  public Uni<Response> findAllByBoardId(final UUID boardId) {
    return sprintService.findAllByBoardId(boardId)
                        .map(sprintBoardDtos -> Response.ok(sprintBoardDtos).build())
                        .onFailure(NoResultException.class).recoverWithItem(Response.status(NOT_FOUND)::build)
                        .onFailure().recoverWithItem(ResponseUtils::failToServerError);
  }

}
