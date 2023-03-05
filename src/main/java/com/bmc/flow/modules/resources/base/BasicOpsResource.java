package com.bmc.flow.modules.resources.base;

import com.bmc.flow.modules.resources.utils.ResponseUtils;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.pgclient.PgException;
import lombok.extern.jbosslog.JBossLog;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.NoSuchElementException;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.*;

@Produces("application/json")
@JBossLog
public abstract class BasicOpsResource<D, E> {

  private final BasicPersistenceService<D, E> basicPersistenceService;

  protected BasicOpsResource(BasicPersistenceService<D, E> service) {
    this.basicPersistenceService = service;
  }
  protected final void logRequestURI(final HttpServerRequest request) {
    log.debugf(":::[%s] Request was made for [%s]", request.method(), request.uri());
  }

  @GET
  @Path("{id}")
  public Uni<Response> findById(final UUID id, final HttpServerRequest request) {
    logRequestURI(request);
    return basicPersistenceService.findById(id)
                                  .map(resultDto -> Response.ok(resultDto).build())
                                  .onFailure(NoResultException.class).recoverWithItem(Response.status(NOT_FOUND)::build)
                                  .onFailure().recoverWithItem(ResponseUtils::failToServerError);
  }

  @POST
  @Consumes("application/json")
  public Uni<Response> create(final D fromDto, final HttpServerRequest request) {
    logRequestURI(request);
    return basicPersistenceService.create(fromDto)
                                  .map(newlyCreatedDto -> Response.ok(newlyCreatedDto).status(CREATED).build())
                                  .onFailure(ConstraintViolationException.class).recoverWithItem(ResponseUtils::violationsToResponse)
                                  .onFailure(PgException.class).recoverWithItem(ResponseUtils::processPgException)
                                  .onFailure().recoverWithItem(ResponseUtils::failToServerError);
  }

  @PUT
  @Path("{idToUpdate}")
  @Consumes("application/x-www-form-urlencoded")
  public Uni<Response> update(final UUID idToUpdate, @FormParam("field") final String field, @FormParam("value") final String value,
                              final HttpServerRequest request) {
    logRequestURI(request);
    return basicPersistenceService.update(idToUpdate, field, value)
                                  .replaceWith(Response.accepted()::build)
                                  .onFailure(PersistenceException.class).recoverWithItem(ResponseUtils::persistenceExResponse)
                                  .onFailure(NoSuchElementException.class).recoverWithItem(Response.status(NOT_FOUND)::build)
                                  .onFailure(PgException.class).recoverWithItem(ResponseUtils::processPgException)
                                  .onFailure().recoverWithItem(ResponseUtils::failToServerError);
  }

  @DELETE
  @Path("{id}")
  public Uni<Response> deleteById(final UUID id, final HttpServerRequest request) {
    logRequestURI(request);
    return basicPersistenceService.deleteById(id)
                                  .map(isDeleted -> {
                                    Response.Status status = isDeleted ? OK : NOT_FOUND;
                                    return Response.ok().status(status).build();
                                  });
  }

}
