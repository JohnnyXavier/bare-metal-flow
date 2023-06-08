package com.bmc.flow.modules.resources.records;

import com.bmc.flow.modules.database.dto.records.ProjectDto;
import com.bmc.flow.modules.database.entities.records.ProjectEntity;
import com.bmc.flow.modules.resources.base.BasicOpsResource;
import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.service.records.ProjectService;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/v1/project")
@Produces("application/json")
public class ProjectResource extends BasicOpsResource<ProjectDto, ProjectEntity> {

    private final ProjectService projectService;

    public ProjectResource(final ProjectService projectService) {
        super(projectService);
        this.projectService = projectService;
    }

    @GET
    @Path("createdBy/{userId}")
    public Uni<Response> findAllCreatedByUserId(final UUID userId,
                                                @QueryParam(value = "sortBy") @NotNull final String sortBy,
                                                @QueryParam(value = "sortDir") final String sortDir,
                                                @QueryParam(value = "pageIx") final Integer pageIx,
                                                @QueryParam(value = "pageSize") @NotNull final Integer pageSize) {
        return projectService.findAllByUserIdPaged(userId, new Pageable(sortBy, sortDir, pageIx, pageSize))
                             .map(userDtos -> Response.ok(userDtos).build());
    }

    @GET
    @Path("account/{accountId}")
    public Uni<Response> findAllByAccountId(final UUID accountId,
                                            @QueryParam(value = "sortBy") @NotNull final String sortBy,
                                            @QueryParam(value = "sortDir") final String sortDir,
                                            @QueryParam(value = "pageIx") final Integer pageIx,
                                            @QueryParam(value = "pageSize") @NotNull final Integer pageSize) {
        return projectService.findAllByAccountIdPaged(accountId, new Pageable(sortBy, sortDir, pageIx, pageSize))
                             .map(projectDtos -> Response.ok(projectDtos).build());
    }

}
