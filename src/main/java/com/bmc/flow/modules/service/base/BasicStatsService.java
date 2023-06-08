package com.bmc.flow.modules.service.base;

import io.quarkus.cache.CacheResult;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

import java.util.UUID;

/**
 * this class is the basis for future statistical operations.
 * <p>
 * this is a placeholder for a proper stats service in a future.
 */
public abstract class BasicStatsService {

    @CacheResult(cacheName = "count-all", keyGenerator = StringCKGen.class)
    public Uni<Long> countSimpleById(final PanacheRepositoryBase<?, UUID> repo, final String countQuery, final UUID id,
                                     final String cacheKey) {
        return repo.count(countQuery, id);
    }

}
