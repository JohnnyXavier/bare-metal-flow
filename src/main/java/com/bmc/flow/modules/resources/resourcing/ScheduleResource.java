package com.bmc.flow.modules.resources.resourcing;

import com.bmc.flow.modules.database.dto.resourcing.ScheduleDto;
import com.bmc.flow.modules.database.entities.resourcing.ScheduleEntity;
import com.bmc.flow.modules.resources.base.BasicOpsResource;
import com.bmc.flow.modules.resources.utils.ResponseUtils;
import com.bmc.flow.modules.service.resourcing.ScheduleService;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpServerRequest;

import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("v1/schedule")
@Produces("application/json")
public class ScheduleResource extends BasicOpsResource<ScheduleDto, ScheduleEntity> {

  private final ScheduleService scheduleService;

  public ScheduleResource(final ScheduleService scheduleService) {
    super(scheduleService);
    this.scheduleService = scheduleService;
  }

  @GET
  @Path("{userId}")
  public Uni<Response> findByUserId(final UUID userId, final HttpServerRequest request) {
    logRequestURI(request);
    return scheduleService.findByUserId(userId)
                          .map(resultDto -> Response.ok(resultDto).build())
                          .onFailure(NoResultException.class).recoverWithItem(Response.status(NOT_FOUND)::build)
                          .onFailure().recoverWithItem(ResponseUtils::failToServerError);
  }


  @GET
  @Path("full/{userId}")
  public Uni<Response> findFullByUserId(final UUID userId, final HttpServerRequest request) {
    logRequestURI(request);
    return scheduleService.findFullByUserId(userId)
                          .map(resultDto -> Response.ok(resultDto).build())
                          .onFailure(NoResultException.class).recoverWithItem(Response.status(NOT_FOUND)::build)
                          .onFailure().recoverWithItem(ResponseUtils::failToServerError);
  }

}
