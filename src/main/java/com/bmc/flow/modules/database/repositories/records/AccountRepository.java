package com.bmc.flow.modules.database.repositories.records;

import com.bmc.flow.modules.database.dto.records.AccountDto;
import com.bmc.flow.modules.database.entities.records.AccountEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AccountRepository implements PanacheRepositoryBase<AccountEntity, UUID> {

  public static final String SELECT_DTO = " select e.id, e.name, e.description, e.coverImage, e.createdAt, e.createdBy.id, e.portfolio.id";

  private static final String FROM_ENTITY = " from AccountEntity e";

  public Uni<List<AccountDto>> findAllAccountsByPortfolioId(final UUID portfolioId) {
    return this.find(SELECT_DTO + FROM_ENTITY +
                         " where e.portfolio.id =?1", portfolioId)
               .project(AccountDto.class)
               .list();
  }

  public Uni<List<AccountDto>> findAllCreatedByUserId(final UUID userId) {
    return this.find(SELECT_DTO + FROM_ENTITY +
                         " where e.createdBy.id =?1", userId)
               .project(AccountDto.class)
               .list();
  }

}
