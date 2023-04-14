package com.bmc.flow.modules.service.base;

import io.quarkus.cache.CacheResult;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

import java.util.UUID;

public abstract class BasicStatsService {

  @CacheResult(cacheName = "count-all", keyGenerator = StringCKGen.class)
  public Uni<Long> countSimpleById(final PanacheRepositoryBase<?, UUID> repo, final String countQuery, final UUID id,
                                   final String cacheKey) {
    return repo.count(countQuery, id);
  }

}
