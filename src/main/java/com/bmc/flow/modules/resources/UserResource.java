package com.bmc.flow.modules.resources;

import com.bmc.flow.modules.database.dto.UserDto;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.resources.base.BasicOpsResource;
import com.bmc.flow.modules.service.UserService;
import io.smallrye.mutiny.Uni;
import lombok.extern.jbosslog.JBossLog;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("v1/users")
@Produces("application/json")
@JBossLog
public class UserResource extends BasicOpsResource<UserDto, UserEntity> {

  @Inject
  protected UserService userService;

  public UserResource(final UserService userService) {
    super(userService);
    this.userService = userService;
  }

  @GET
  @Path("project/{projectId}")
  public Uni<Response> findUsersByProjectId(final UUID projectId) {
    return userService.getUsersByProjectId(projectId)
                      .map(userDtos -> Response.ok(userDtos).build());
  }

}
