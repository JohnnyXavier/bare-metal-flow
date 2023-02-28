package com.bmc.flow.modules.database.repositories.records;

import com.bmc.flow.modules.database.dto.records.PortfolioDto;
import com.bmc.flow.modules.database.entities.records.PortfolioEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PortfolioRepository implements PanacheRepositoryBase<PortfolioEntity, UUID> {

  private static final String SELECT_DTO = " select e.id, e.name, e.description, e.coverImage, e.isDefault, e.createdAt, e.createdBy.id";

  private static final String FROM_ENTITY = " from PortfolioEntity e";

  /**
   * <pre>
   * Hibernate:
   *     select
   *         portfolioe0_.id as col_0_0_,
   *         portfolioe0_.name as col_1_0_,
   *         portfolioe0_.description as col_2_0_,
   *         portfolioe0_.cover_image as col_3_0_,
   *         portfolioe0_.created_at as col_4_0_,
   *         portfolioe0_.created_by_id as col_5_0_
   *     from
   *         public.portfolio portfolioe0_
   *     where
   *         portfolioe0_.created_by_id=$1
   * </pre>
   *
   * @param userId user who created the portfolio
   *
   * @return
   */
  public Uni<List<PortfolioDto>> findAllCreatedByUserId(final UUID userId) {
    return this.find(SELECT_DTO + FROM_ENTITY +
                         " where e.createdBy.id =?1", userId)
               .project(PortfolioDto.class)
               .list();
  }

}
