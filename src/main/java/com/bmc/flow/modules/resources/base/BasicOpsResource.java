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

@Produces("application/json")
@JBossLog
@WithSession
public abstract class BasicOpsResource<D, E> {

  private final BasicPersistenceService<D, E> basicPersistenceService;

  protected BasicOpsResource(BasicPersistenceService<D, E> service) {
    this.basicPersistenceService = service;
  }

  @GET
  @Path("{id}")
  public Uni<Response> findById(final UUID id) {
    return basicPersistenceService.findById(id)
                                  .map(resultDto -> Response.ok(resultDto).build())
                                  .onFailure(NoResultException.class).recoverWithItem(Response.status(NOT_FOUND)::build)
                                  .onFailure().recoverWithItem(ResponseUtils::failToServerError);
  }

  @POST
  @Consumes("application/json")
  public Uni<Response> create(final D fromDto) {
    return basicPersistenceService.create(fromDto)
                                  .map(newlyCreatedDto -> Response.ok(newlyCreatedDto).status(CREATED).build())
                                  .onFailure(ConstraintViolationException.class).recoverWithItem(ResponseUtils::violationsToResponse)
                                  .onFailure(PgException.class).recoverWithItem(ResponseUtils::processPgException)
                                  .onFailure().recoverWithItem(ResponseUtils::failToServerError);
  }

  @PUT
  @Path("{idToUpdate}")
  @Consumes("application/x-www-form-urlencoded")
  public Uni<Response> update(@NotNull final UUID idToUpdate, @FormParam("field") final String field, @FormParam("value") final String value) {
    return basicPersistenceService.update(idToUpdate, field, value)
                                  .replaceWith(Response.accepted()::build)
                                  .onFailure(PersistenceException.class).recoverWithItem(ResponseUtils::persistenceExResponse)
                                  .onFailure(NoSuchElementException.class).recoverWithItem(Response.status(NOT_FOUND)::build)
                                  .onFailure(PgException.class).recoverWithItem(ResponseUtils::processPgException)
                                  .onFailure().recoverWithItem(ResponseUtils::failToServerError);
  }

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
