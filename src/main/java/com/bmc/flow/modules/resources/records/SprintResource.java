package com.bmc.flow.modules.resources.records;

import com.bmc.flow.modules.database.dto.records.SprintDto;
import com.bmc.flow.modules.database.entities.records.SprintEntity;
import com.bmc.flow.modules.resources.base.BasicOpsResource;
import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.resources.utils.ResponseUtils;
import com.bmc.flow.modules.service.records.SprintService;
import io.smallrye.mutiny.Uni;

import jakarta.persistence.NoResultException;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.AbstractMap;
import java.util.Map;
import java.util.UUID;

import static java.util.Map.ofEntries;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

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
  public Uni<Response> findAllByCollectionId(final String collection, final UUID collectionId,
                                             @QueryParam(value = "sortBy") @NotNull final String sortBy,
                                             @QueryParam(value = "sortDir") final String sortDir,
                                             @QueryParam(value = "pageIx") final Integer pageIx,
                                             @QueryParam(value = "pageSize") @NotNull final Integer pageSize) {
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
