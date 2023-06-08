package com.bmc.flow.modules.resources.base;

import com.bmc.flow.modules.resources.utils.ResponseUtils;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import io.smallrye.mutiny.Uni;
import io.vertx.pgclient.PgException;
import jakarta.persistence.NoResultException;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

/**
 * this class is the basis for catalog REST resources, it adds a findAll() method to the basic REST operations.
 * <p>
 * this is useful for catalogs as the front end might want to query all possible labels, statuses, etc. to show them to the end user.
 * <p>
 * I might incorporate this to the general ops but a few records they don't make sense to find them all... finding all cards from different
 * projects? finding all comments? There is some refinement to do...
 *
 * @param <D> dto class
 * @param <E> entity class
 */
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
        return service.findAll(new Pageable(sortBy, sortDir, pageIx, pageSize))
                      .map(pageResult -> Response.ok(pageResult).build())
                      .onFailure(NoResultException.class).recoverWithItem(Response.status(NOT_FOUND)::build)
                      .onFailure(PgException.class).recoverWithItem(ResponseUtils::processPgException)
                      .onFailure().recoverWithItem(ResponseUtils::failToServerError);
    }

}
