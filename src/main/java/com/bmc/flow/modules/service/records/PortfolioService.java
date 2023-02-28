package com.bmc.flow.modules.service.records;

import com.bmc.flow.modules.database.dto.records.PortfolioDto;
import com.bmc.flow.modules.database.entities.records.PortfolioEntity;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.repositories.records.PortfolioRepository;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;
import javax.validation.Valid;
import java.util.NoSuchElementException;
import java.util.UUID;

import static java.lang.Boolean.FALSE;
import static java.util.UUID.randomUUID;

@ApplicationScoped
public class PortfolioService extends BasicPersistenceService<PortfolioDto, PortfolioEntity> {


  private final PortfolioRepository portfolioRepo;

  public PortfolioService(final PortfolioRepository portfolioRepo) {
    super(portfolioRepo, PortfolioDto.class);
    this.portfolioRepo = portfolioRepo;
  }

  @ReactiveTransactional
  public Uni<PortfolioDto> create(@Valid final PortfolioDto portfolioDto) {
    PortfolioEntity newPortfolio = new PortfolioEntity();

    newPortfolio.setId(randomUUID());
    newPortfolio.setName(portfolioDto.getName());
    newPortfolio.setDescription(portfolioDto.getDescription());
    newPortfolio.setIsDefault(FALSE);

    UserEntity portfolioCreator = new UserEntity();
    portfolioCreator.setId(portfolioDto.getCreatedBy());

    newPortfolio.setCreatedBy(portfolioCreator);

    return portfolioRepo.persist(newPortfolio)
                        .replaceWith(findById(newPortfolio.getId()));
  }


  @Override
  protected void updateField(final PortfolioEntity toUpdate, final String key, final String value) {
    switch (key) {
      case "name" -> toUpdate.setName(value);
      case "description" -> toUpdate.setDescription(value);
      case "coverImage" -> toUpdate.setCoverImage(value);

      default -> throw new IllegalStateException("Unexpected value: " + key);
    }

  }

  @ReactiveTransactional
  @Override
  public Uni<Boolean> deleteById(final UUID portfolioId) throws PersistenceException {
    return portfolioRepo.findById(portfolioId)
                        .onItem().ifNull().failWith(NoSuchElementException::new)
                        .onItem().ifNotNull().transform(PortfolioEntity::getIsDefault)
                        .call(isDefaultPortfolio -> !isDefaultPortfolio
                            ? portfolioRepo.deleteById(portfolioId)
                            : Uni.createFrom().failure(() -> new PersistenceException("{\"error\": \"cannot delete default portfolio\"}")));
  }

}