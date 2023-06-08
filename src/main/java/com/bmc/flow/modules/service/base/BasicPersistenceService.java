package com.bmc.flow.modules.service.base;

import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.service.reflection.MethodNames;
import io.quarkus.cache.CacheResult;
import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.logging.Log;
import io.quarkus.panache.common.Page;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;
import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.UUID;

@WithSession
public abstract class BasicPersistenceService<D, E> {

    private PanacheRepositoryBase<E, UUID> repository;

    private Class<D> dtoClass;

    /**
     * This no arg ctor is required by quarkus CDI. Do not delete
     */
    protected BasicPersistenceService() {
    }

    protected BasicPersistenceService(final PanacheRepositoryBase<E, UUID> repository, final Class<D> dtoClass) {
        this.repository = repository;
        this.dtoClass   = dtoClass;
    }

    public Uni<D> findById(final UUID id) {
        return repository.find("id", id)
                         .project(dtoClass)
                         .singleResult();
    }

    //FIXME: create a CatalogPersistence Service, so we put findAll there
    // we don't want to expose a findAll for every card or every user, being it paged or not
    public Uni<PageResult<D>> findAll(final Pageable pageable) {
        return this.findAllPaged(repository.findAll(pageable.getSort()), "-find-all", pageable.getPage());
    }

    protected Uni<PageResult<D>> findAllPaged(final PanacheQuery<E> panacheQuery, final String queryName,
                                              final Page page) {
        return panacheQuery.project(dtoClass)
                           .page(page)
                           .list()
                           .flatMap(ds -> countAll(panacheQuery, dtoClass.getSimpleName() + queryName)
                               .map(count -> new PageResult<>(ds, count, page)));
    }

    /**
     * This method is public so cache can access it.
     *
     * @param panacheQuery the actual query
     * @param cacheKey     this is so the cacheKey is properly generated and distinct for each service caller
     *
     * @return the count for the given table.
     */
    @CacheResult(cacheName = "count-all", keyGenerator = StringCKGen.class)
    public Uni<Long> countAll(final PanacheQuery<E> panacheQuery, final String cacheKey) {
        Log.debugf("added entry to [count all] cache: %s", cacheKey);
        return panacheQuery.count();
    }

    public abstract Uni<D> create(@Valid final D fromDto);

    public Uni<Void> update(final UUID id, final String key, final String value) {
        return repository.findById(id)
                         .onItem().ifNull().fail()
                         .chain(entityToUpdate -> update(entityToUpdate, key, value));
    }

    protected abstract Uni<Void> update(final E toUpdate, final String key, final String value);

    /**
     * TODO: Article about reflection
     * this is handled by hibernate, so not only the value is updated but the "updatedAt" field too
     * <p>
     * TODO: article about "update in place":
     * query generated by hbn to update a single field on a card:
     * Hibernate:
     *     update
     *         public.card
     *     set
     *         account_id=$1,
     *         board_id=$2,
     *         board_column_id=$3,
     *         bugs_reported=$4,
     *         card_difficulty_id=$5,
     *         card_status_id=$6,
     *         card_type_id=$7,
     *         completed_date=$8,
     *         cover_image=$9,
     *         created_by_id=$10,
     *         description=$11,
     *         due_date=$12,
     *         estimated_time=$13,
     *         is_completed=$14,
     *         logged_time=$15,
     *         name=$16,
     *         position=$17,
     *         project_id=$18,
     *         remaining_time=$19,
     *         updated_at=$20
     *     where
     *         id=$21
     * <p>
     * manually we would have done:
     *     update
     *        public.card
     *     set
     *        description=?1,
     *        updated_at=?2
     *     where
     *        id=?3
     *
     * @param toUpdate -
     * @param methods  -
     * @param value    -
     *
     * @return -
     */
    @WithTransaction
    @SneakyThrows
    protected Uni<Void> updateInPlace(final E toUpdate, final MethodNames methods, final Object value) {
        Method m = toUpdate.getClass().getMethod(methods.getMethodName(), value.getClass());
        m.invoke(toUpdate, value);

        return Uni.createFrom().voidItem();
    }

    @WithTransaction
    public Uni<Boolean> deleteById(final UUID idToDelete) {
        return repository.deleteById(idToDelete);
    }

    @CacheResult(cacheName = "count-all", keyGenerator = StringCKGen.class)
    public Uni<Long> countAllByUserId(final UUID userId, final String cacheKey) {
        Log.debugf("added entry to [count all] cache: %s", cacheKey);
        return repository.count("createdBy.id", userId);
    }
}
