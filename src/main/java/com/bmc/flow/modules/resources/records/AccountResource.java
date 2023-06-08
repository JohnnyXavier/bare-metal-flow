package com.bmc.flow.modules.resources.records;

import com.bmc.flow.modules.database.dto.records.AccountDto;
import com.bmc.flow.modules.database.entities.records.AccountEntity;
import com.bmc.flow.modules.resources.base.BasicOpsResource;
import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.service.records.AccountService;
import io.smallrye.mutiny.Uni;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

/**
 * this class is the rest resource handling account requests.
 */
@Path("/v1/account")
@Produces("application/json")
public class AccountResource extends BasicOpsResource<AccountDto, AccountEntity> {

    private final AccountService accountService;

    public AccountResource(final AccountService accountService) {
        super(accountService);
        this.accountService = accountService;
    }

    @GET
    @Path("createdBy/{userId}")
    public Uni<Response> findAllCreatedByUserId(final UUID userId,
                                                @QueryParam(value = "sortBy") @NotNull final String sortBy,
                                                @QueryParam(value = "sortDir") final String sortDir,
                                                @QueryParam(value = "pageIx") final Integer pageIx,
                                                @QueryParam(value = "pageSize") @NotNull final Integer pageSize) {
        return accountService.findAllByUserIdPaged(userId, new Pageable(sortBy, sortDir, pageIx, pageSize))
                             .map(userDtos -> Response.ok(userDtos).build());
    }
}

