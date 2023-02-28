package com.bmc.flow.modules.resources.base;

import com.bmc.flow.modules.resources.utils.ResponseUtils;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import io.vertx.pgclient.PgException;

import javax.persistence.NoResultException;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.Optional;

import static com.bmc.flow.modules.resources.base.ResourceConstants.CHECK_PAGE;
import static java.lang.Math.max;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

public abstract class BasicCatalogResource<D, E> extends BasicOpsResource<D, E> {

  private final BasicPersistenceService<D, E> service;

  protected BasicCatalogResource(final BasicPersistenceService<D, E> service) {
    super(service);
    this.service = service;
  }

  @GET
  @Path("all")
  public Uni<Response> findAll(@QueryParam(value = "sortBy") @NotNull final String sortBy,
                               @QueryParam(value = "sortDir") final String sortDir,
                               @QueryParam(value = "pageIx") final Integer pageIx,
                               @QueryParam(value = "pageSize") @NotNull final Integer pageSize) {
    Sort.Direction direction = Optional.ofNullable(sortDir)
                                       .filter("desc"::equals)
                                       .map(dir -> Sort.Direction.Descending)
                                       .orElse(Sort.Direction.Ascending);

    Sort sort = Sort.by(sortBy, direction);

    Page page = Optional.ofNullable(pageIx)
                        .map(index -> Page.of(max(index, 0), CHECK_PAGE.applyAsInt(pageSize)))
                        .orElseGet(() -> Page.ofSize(pageSize));

    return service.findAll(sort, page)
                  .map(pageResult -> Response.ok(pageResult).build())
                  .onFailure(NoResultException.class).recoverWithItem(Response.status(NOT_FOUND)::build)
                  .onFailure(PgException.class).recoverWithItem(ResponseUtils::processPgException)
                  .onFailure().recoverWithItem(ResponseUtils::failToServerError);
  }

}
