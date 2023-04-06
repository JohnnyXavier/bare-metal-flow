package com.bmc.flow.modules.service.records;

import com.bmc.flow.modules.database.dto.records.AccountDto;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.records.AccountEntity;
import com.bmc.flow.modules.database.repositories.records.AccountRepository;
import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import com.bmc.flow.modules.service.base.PageResult;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@ApplicationScoped
public class AccountService extends BasicPersistenceService<AccountDto, AccountEntity> {

  private final AccountRepository accountRepo;

  public AccountService(final AccountRepository accountRepo) {
    super(accountRepo, AccountDto.class);
    this.accountRepo = accountRepo;
  }


  @ReactiveTransactional
  @Override
  public Uni<AccountDto> create(@Valid final AccountDto accountDto) {
    UserEntity accountCreator = new UserEntity();
    accountCreator.setId(accountDto.getCreatedBy());

    AccountEntity newAccount = new AccountEntity();
    newAccount.setId(randomUUID());
    newAccount.setName(accountDto.getName());
    newAccount.setDescription(accountDto.getDescription());
    newAccount.setCreatedBy(accountCreator);

    return accountRepo.persist(newAccount)
                      .replaceWith(findById(newAccount.getId()));
  }

  public Uni<PageResult<AccountDto>> findAllByUserIdPaged(final UUID userId, final Pageable pageable) {
    return findAllPaged(accountRepo.findAllCreatedByUserId(userId, pageable.getSort()), "-all-accounts-by-user",
        pageable.getPage());
  }

  protected void updateField(final AccountEntity toUpdate, final String key, final String value) {
    switch (key) {
      case "name" -> toUpdate.setName(value);
      case "description" -> toUpdate.setDescription(value);
      case "coverImage" -> toUpdate.setCoverImage(value);

      default -> throw new IllegalStateException("Unexpected value: " + key);
    }
  }

}
