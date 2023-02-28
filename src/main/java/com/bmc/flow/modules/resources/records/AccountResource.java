package com.bmc.flow.modules.resources.records;

import com.bmc.flow.modules.database.dto.records.AccountDto;
import com.bmc.flow.modules.database.entities.records.AccountEntity;
import com.bmc.flow.modules.resources.base.BasicOpsResource;
import com.bmc.flow.modules.resources.utils.ResponseUtils;
import com.bmc.flow.modules.service.records.AccountService;
import io.smallrye.mutiny.Uni;

import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/v1/account")
@Produces("application/json")
public class AccountResource extends BasicOpsResource<AccountDto, AccountEntity> {

  private final AccountService accountService;

  public AccountResource(final AccountService accountService) {
    super(accountService);
    this.accountService = accountService;
  }

  @GET
  @Path("portfolio/{portfolioId}")
  public Uni<Response> findAccountsByPortfolioId(final UUID portfolioId) {
    return accountService.getAllAccountsByPortfolioId(portfolioId)
                         .map(accountDtos -> Response.ok(accountDtos).build())
                         .onFailure(NoResultException.class).recoverWithItem(Response.status(NOT_FOUND)::build)
                         .onFailure().recoverWithItem(ResponseUtils::failToServerError);
  }

}
