package com.bmc.flow.modules.resources;

import com.bmc.flow.modules.database.dto.UserDto;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.resources.base.BasicOpsResource;
import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.service.UserService;
import io.smallrye.mutiny.Uni;
import lombok.extern.jbosslog.JBossLog;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
  @Path("portfolio/{portfolioId}")
  public Uni<Response> findAllByPortfolioId(final UUID portfolioId, @QueryParam(value = "sortBy") @NotNull final String sortBy,
                                            @QueryParam(value = "sortDir") final String sortDir,
                                            @QueryParam(value = "pageIx") final Integer pageIx,
                                            @QueryParam(value = "pageSize") @NotNull final Integer pageSize) {
    return userService.findAllByBoardId(portfolioId, new Pageable(sortBy, sortDir, pageIx, pageSize))
                      .map(userDtos -> Response.ok(userDtos).build());
  }

  @GET
  @Path("account/{accountId}")
  public Uni<Response> findAllByAccountId(final UUID accountId, @QueryParam(value = "sortBy") @NotNull final String sortBy,
                                          @QueryParam(value = "sortDir") final String sortDir,
                                          @QueryParam(value = "pageIx") final Integer pageIx,
                                          @QueryParam(value = "pageSize") @NotNull final Integer pageSize) {
    return userService.findAllByBoardId(accountId, new Pageable(sortBy, sortDir, pageIx, pageSize))
                      .map(userDtos -> Response.ok(userDtos).build());
  }

  @GET
  @Path("project/{projectId}")
  public Uni<Response> findAllByProjectId(final UUID projectId, @QueryParam(value = "sortBy") @NotNull final String sortBy,
                                          @QueryParam(value = "sortDir") final String sortDir,
                                          @QueryParam(value = "pageIx") final Integer pageIx,
                                          @QueryParam(value = "pageSize") @NotNull final Integer pageSize) {
    return userService.findAllByProjectId(projectId, new Pageable(sortBy, sortDir, pageIx, pageSize))
                      .map(userDtos -> Response.ok(userDtos).build());
  }

  @GET
  @Path("board/{boardId}")
  public Uni<Response> findAllByBoardId(final UUID boardId, @QueryParam(value = "sortBy") @NotNull final String sortBy,
                                        @QueryParam(value = "sortDir") final String sortDir,
                                        @QueryParam(value = "pageIx") final Integer pageIx,
                                        @QueryParam(value = "pageSize") @NotNull final Integer pageSize) {
    return userService.findAllByBoardId(boardId, new Pageable(sortBy, sortDir, pageIx, pageSize))
                      .map(userDtos -> Response.ok(userDtos).build());
  }

  @GET
  @Path("card/{cardId}")
  public Uni<Response> findAllByCardId(final UUID cardId, @QueryParam(value = "sortBy") @NotNull final String sortBy,
                                       @QueryParam(value = "sortDir") final String sortDir,
                                       @QueryParam(value = "pageIx") final Integer pageIx,
                                       @QueryParam(value = "pageSize") @NotNull final Integer pageSize) {
    return userService.findAllByBoardId(cardId, new Pageable(sortBy, sortDir, pageIx, pageSize))
                      .map(userDtos -> Response.ok(userDtos).build());
  }

  @GET
  @Path("sprint/{sprintId}")
  public Uni<Response> findAllBySprintId(final UUID sprintId, @QueryParam(value = "sortBy") @NotNull final String sortBy,
                                         @QueryParam(value = "sortDir") final String sortDir,
                                         @QueryParam(value = "pageIx") final Integer pageIx,
                                         @QueryParam(value = "pageSize") @NotNull final Integer pageSize) {
    return userService.findAllByBoardId(sprintId, new Pageable(sortBy, sortDir, pageIx, pageSize))
                      .map(userDtos -> Response.ok(userDtos).build());
  }

}
