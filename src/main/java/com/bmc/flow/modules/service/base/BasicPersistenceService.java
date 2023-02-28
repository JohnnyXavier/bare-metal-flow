package com.bmc.flow.modules.service.base;

import com.bmc.flow.modules.resources.base.Pageable;
import io.quarkus.cache.CacheResult;
import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.quarkus.panache.common.Page;
import io.smallrye.mutiny.Uni;

import javax.validation.Valid;
import java.util.UUID;

public abstract class BasicPersistenceService<D, E> {

  private PanacheRepositoryBase<E, UUID> repository;

  private Class<D> dtoClass;

  /**
   * This no arg ctor required for quarkus CDI. Do not delete
   */
  protected BasicPersistenceService() {
  }

  protected BasicPersistenceService(final PanacheRepositoryBase<E, UUID> repository, final Class<D> dtoClass) {
    this.repository = repository;
    this.dtoClass = dtoClass;
  }

  public Uni<D> findById(final UUID id) {
    return repository.find("id", id)
                     .project(dtoClass)
                     .singleResult();
  }

  //FIXME: create a CotalogPersistence Service so we put findAll there
  // we don want to expose a findAll for every card or every user, being it paged or not
  public Uni<PageResult<D>> findAll(final Pageable pageable) {
    return repository.findAll(pageable.getSort())
                     .project(dtoClass)
                     .page(pageable.getPage())
                     .list()
                     .flatMap(ds -> countAll(dtoClass.getName()).map(count -> new PageResult<>(ds, count, pageable.getPage())));
  }

  /**
   * This method is public so cache can access it.
   *
   * @param name this is so the cacheKey is properly generated and distinct for each service caller
   *
   * @return the count for the given table.
   */
  @CacheResult(cacheName = "count-all", keyGenerator = CountAllCKGen.class)
  public Uni<Long> countAll(final String name) {
    return repository.count();
  }

  public abstract Uni<D> create(@Valid final D fromDto);

  @ReactiveTransactional
  public Uni<Void> update(final UUID id, final String key, final String value) {
    return repository.findById(id)
                     .onItem().ifNull().fail()
                     .invoke(entityToUpdate -> updateField(entityToUpdate, key, value))
                     .replaceWith(Uni.createFrom().voidItem());
  }

  @ReactiveTransactional
  public Uni<Boolean> deleteById(final UUID idToDelete) {
    return repository.deleteById(idToDelete);
  }

  protected abstract void updateField(final E toUpdate, final String key, final String value);

  protected Uni<PageResult<D>> findAllPaged(final PanacheQuery<E> panacheQuery, final Page page) {
    return panacheQuery.project(dtoClass)
                       .page(page)
                       .list()
                       .flatMap(ds -> countAll(dtoClass.getName())
                           .map(count -> new PageResult<>(ds, count, page)));

  }

}
