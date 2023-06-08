package com.bmc.flow.modules.resources.base;

import com.bmc.flow.modules.resources.utils.ResponseUtils;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import io.vertx.pgclient.PgException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.extern.jbosslog.JBossLog;

import java.util.NoSuchElementException;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.*;

/**
 * this class is the basis for all REST resources.
 * <p>
 * by inheriting from this class a new REST resource will have all basic ops taken care for.
 *
 * @param <D> dto class
 * @param <E> entity class
 */
@Produces("application/json")
@JBossLog
@WithSession
public abstract class BasicOpsResource<D, E> {

    private final BasicPersistenceService<D, E> basicPersistenceService;

    protected BasicOpsResource(BasicPersistenceService<D, E> service) {
        this.basicPersistenceService = service;
    }

    /**
     * finds a single record on the db by id.
     *
     * @param id the id of the entity we want to find
     *
     * @return a {@link Response} wrapping the resulting DTO/s or the appropriate failure code
     */
    @GET
    @Path("{id}")
    public Uni<Response> findById(final UUID id) {
        return basicPersistenceService.findById(id)
                                      .map(resultDto -> Response.ok(resultDto).build())
                                      .onFailure(NoResultException.class).recoverWithItem(Response.status(NOT_FOUND)::build)
                                      .onFailure().recoverWithItem(ResponseUtils::failToServerError);
    }

    /**
     * creates a new record on the db from a json.
     *
     * @param fromDto dto with creational data
     *
     * @return a {@link Response} wrapping the resulting newly created record DTO/s or the appropriate failure code
     */
    @POST
    @Consumes("application/json")
    public Uni<Response> create(final D fromDto) {
        return basicPersistenceService.create(fromDto)
                                      .map(newlyCreatedDto -> Response.ok(newlyCreatedDto).status(CREATED).build())
                                      .onFailure(ConstraintViolationException.class).recoverWithItem(ResponseUtils::violationsToResponse)
                                      .onFailure(PgException.class).recoverWithItem(ResponseUtils::processPgException)
                                      .onFailure().recoverWithItem(ResponseUtils::failToServerError);
    }

    /**
     * updates an existing record on the db.
     * <p>
     * this method updates a singe field per request.
     *
     * @param id    the id of the entity we want to update
     * @param field the field to be updated
     * @param value the value to update the field with
     *
     * @return a {@link Response} carrying only a {@link Response.Status#ACCEPTED} or the appropriate failure code
     */
    @PUT
    @Path("{id}")
    @Consumes("application/x-www-form-urlencoded")
    public Uni<Response> update(@NotNull final UUID id, @FormParam("field") final String field,
                                @FormParam("value") final String value) {
        return basicPersistenceService.update(id, field, value)
                                      .replaceWith(Response.accepted()::build)
                                      .onFailure(PersistenceException.class).recoverWithItem(ResponseUtils::persistenceExResponse)
                                      .onFailure(NoSuchElementException.class).recoverWithItem(Response.status(NOT_FOUND)::build)
                                      .onFailure(PgException.class).recoverWithItem(ResponseUtils::processPgException)
                                      .onFailure().recoverWithItem(ResponseUtils::failToServerError);
    }

    /**
     * deletes a single record on the db by id.
     *
     * @param id the id of the entity we want to delete
     *
     * @return a {@link Response} carrying only a {@link Response.Status#OK} if success or {@link Response.Status#NOT_FOUND} if the id is not found
     */
    @DELETE
    @Path("{id}")
    public Uni<Response> deleteById(final UUID id) {
        return basicPersistenceService.deleteById(id)
                                      .map(isDeleted -> {
                                          Response.Status status = isDeleted ? OK : NOT_FOUND;
                                          return Response.ok().status(status).build();
                                      });
    }

}
