package com.bmc.flow.modules.resources;

import com.bmc.flow.modules.database.dto.UserDto;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.resources.base.BasicOpsResource;
import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.service.UserService;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpServerRequest;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map;
import java.util.UUID;

import static java.util.Map.ofEntries;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("v1/users")
@Produces("application/json")
public class UserResource extends BasicOpsResource<UserDto, UserEntity> {

  private final UserService userService;

  private final Map<String, String> userSupportedCollections = ofEntries(
      new SimpleImmutableEntry<>("project", "projects"),
      new SimpleImmutableEntry<>("account", "accounts"),
      new SimpleImmutableEntry<>("board", "boards"),
      new SimpleImmutableEntry<>("sprint", "sprints"),
      new SimpleImmutableEntry<>("assignedCard", "assignedCard"),
      new SimpleImmutableEntry<>("watchingCard", "watchingCards"),
      new SimpleImmutableEntry<>("department", "departments"),
      new SimpleImmutableEntry<>("retroBoard", "retroBoards")
  );

  public UserResource(final UserService userService) {
    super(userService);
    this.userService = userService;
  }

  @GET
  @Path("{collection}/{collectionId}")
  public Uni<Response> findAllByCollectionId(final String collection, final UUID collectionId, final HttpServerRequest request,
                                             @QueryParam(value = "sortBy")@NotNull final String sortBy,
                                             @QueryParam(value = "sortDir") final String sortDir,
                                             @QueryParam(value = "pageIx") final Integer pageIx,
                                             @QueryParam(value = "pageSize") @NotNull final Integer pageSize) {
    logRequestURI(request);

    String collections = userSupportedCollections.get(collection);
    if (collections == null) {
      return Uni.createFrom().item(Response.ok().status(NOT_FOUND).build());
    } else {
      return userService.findAllInCollectionId(collections, collectionId, new Pageable(sortBy, sortDir, pageIx, pageSize))
                        .map(userDtos -> Response.ok(userDtos).build());
    }
  }

  // TODO: CREATE A ERR HANDLER ON RESOURCES GENERAL HANDLER

}
