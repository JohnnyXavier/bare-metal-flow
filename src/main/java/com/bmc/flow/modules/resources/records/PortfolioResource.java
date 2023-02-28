package com.bmc.flow.modules.resources.records;

import com.bmc.flow.modules.database.dto.records.PortfolioDto;
import com.bmc.flow.modules.database.entities.records.PortfolioEntity;
import com.bmc.flow.modules.resources.base.BasicOpsResource;
import com.bmc.flow.modules.service.records.PortfolioService;
import io.smallrye.mutiny.Uni;

import javax.persistence.PersistenceException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.NoSuchElementException;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.CONFLICT;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/v1/portfolio")
@Produces("application/json")
public class PortfolioResource extends BasicOpsResource<PortfolioDto, PortfolioEntity> {

  protected final PortfolioService portfolioService;

  public PortfolioResource(final PortfolioService portfolioService) {
    super(portfolioService);
    this.portfolioService = portfolioService;
  }

  @Override
  public Uni<Response> deleteById(final UUID id) {
    return portfolioService.deleteById(id)
                           .replaceWith(Response.ok()::build)
                           .onFailure(NoSuchElementException.class).recoverWithItem(Response.status(NOT_FOUND)::build)
                           .onFailure(PersistenceException.class).recoverWithItem(Response.status(CONFLICT)::build);
  }

}
