package com.bmc.flow.modules.resources.records;

import com.bmc.flow.modules.database.dto.records.ProjectDto;
import com.bmc.flow.modules.database.entities.records.ProjectEntity;
import com.bmc.flow.modules.resources.base.BasicOpsResource;
import com.bmc.flow.modules.resources.utils.ResponseUtils;
import com.bmc.flow.modules.service.records.ProjectService;
import io.smallrye.mutiny.Uni;

import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/v1/project")
@Produces("application/json")
public class ProjectResource extends BasicOpsResource<ProjectDto, ProjectEntity> {


  protected final ProjectService projectService;

  public ProjectResource(final ProjectService projectService) {
    super(projectService);
    this.projectService = projectService;
  }

  @GET
  @Path("account/{accountId}")
  public Uni<Response> findProjectsByAccountId(final UUID accountId) {
    return projectService.getAllProjectsByAccountId(accountId)
                         .map(projectDtos -> Response.ok(projectDtos).build())
                         .onFailure(NoResultException.class).recoverWithItem(Response.status(NOT_FOUND)::build)
                         .onFailure().recoverWithItem(ResponseUtils::failToServerError);
  }

}
