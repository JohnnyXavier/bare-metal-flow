package com.bmc.flow.modules.resources.statistics;

import com.bmc.flow.modules.resources.utils.ResponseUtils;
import com.bmc.flow.modules.service.UserService;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import com.bmc.flow.modules.service.records.AccountService;
import com.bmc.flow.modules.service.records.CardService;
import com.bmc.flow.modules.service.records.ProjectService;
import io.smallrye.mutiny.Uni;
import io.vertx.pgclient.PgException;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("v1/statistics")
@Produces("application/json")
public class StatisticsResource {

  private final AccountService accountService;

  private final ProjectService projectService;

  private final CardService cardService;

  private final UserService userService;

  private final Map<String, BasicPersistenceService<?, ?>> supportedMetrics = new HashMap<>();

  public StatisticsResource(final AccountService accountService, final ProjectService projectService, final CardService cardService,
                            final UserService userService) {
    this.accountService = accountService;
    this.projectService = projectService;
    this.cardService = cardService;
    this.userService = userService;

    Map<String, BasicPersistenceService<?, ?>> metricServiceMap = new HashMap<>();
    metricServiceMap.put("accounts", accountService);
    metricServiceMap.put("projects", projectService);
    metricServiceMap.put("cards", cardService);
    metricServiceMap.put("users", userService);

    supportedMetrics.putAll(metricServiceMap);
  }

  @GET
  @Path("count/{metric}/{userId}")
  public Uni<Response> getMetricCountCreatedByUser(final String metric, final UUID userId) {
    BasicPersistenceService<?, ?> metricService = supportedMetrics.get(metric);

    if (metricService == null) {
      return Uni.createFrom().item(Response.ok().status(NOT_FOUND).build());
    }

    String cacheKey = "count-" + metric + "-by-user-" + userId;
    return metricService.countAllByUserId(userId, cacheKey)
                        .map(metricCount -> Map.of("name", metric, "value", metricCount))
                        .map(metricMap -> Response.ok(metricMap).build())
                        .onFailure(ConstraintViolationException.class).recoverWithItem(ResponseUtils::violationsToResponse)
                        .onFailure(PgException.class).recoverWithItem(ResponseUtils::processPgException)
                        .onFailure().recoverWithItem(ResponseUtils::failToServerError);
  }

}
