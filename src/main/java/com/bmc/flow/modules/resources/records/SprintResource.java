package com.bmc.flow.modules.resources.records;

import com.bmc.flow.modules.database.dto.records.SprintDto;
import com.bmc.flow.modules.database.entities.records.SprintEntity;
import com.bmc.flow.modules.resources.base.BasicOpsResource;
import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.resources.utils.ResponseUtils;
import com.bmc.flow.modules.service.records.SprintService;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpServerRequest;

import javax.persistence.NoResultException;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.AbstractMap;
import java.util.Map;
import java.util.UUID;

import static java.util.Map.ofEntries;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/v1/sprint")
@Produces("application/json")
public class SprintResource extends BasicOpsResource<SprintDto, SprintEntity> {

  private final SprintService sprintService;

  private final Map<String, String> SupportedCollections = ofEntries(
      new AbstractMap.SimpleImmutableEntry<>("project", "project"),
      new AbstractMap.SimpleImmutableEntry<>("board", "board")
  );

  public SprintResource(final SprintService sprintService) {
    super(sprintService);
    this.sprintService = sprintService;
  }

  @GET
  @Path("{collection}/{collectionId}")
  public Uni<Response> findAllByCollectionId(final String collection, final UUID collectionId, final HttpServerRequest request,
                                             @QueryParam(value = "sortBy") @NotNull final String sortBy,
                                             @QueryParam(value = "sortDir") final String sortDir,
                                             @QueryParam(value = "pageIx") final Integer pageIx,
                                             @QueryParam(value = "pageSize") @NotNull final Integer pageSize) {
    logRequestURI(request);

    String collections = SupportedCollections.get(collection);
    if (collections == null) {
      return Uni.createFrom().item(Response.ok().status(NOT_FOUND).build());
    } else {
      return sprintService.findAllInCollectionId(collections, collectionId, new Pageable(sortBy, sortDir, pageIx, pageSize))
                          .map(userDtos -> Response.ok(userDtos).build())
                          .onFailure(NoResultException.class).recoverWithItem(Response.status(NOT_FOUND)::build)
                          .onFailure().recoverWithItem(ResponseUtils::failToServerError);
    }
  }

}
