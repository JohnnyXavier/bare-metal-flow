package com.bmc.flow.modules.database.repositories.resourcing;

import com.bmc.flow.modules.database.entities.resourcing.ShrinkageEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

/**
 * this class is the data access for shrinkage data.
 */
@ApplicationScoped
public class ShrinkageRepository implements PanacheRepositoryBase<ShrinkageEntity, UUID> {

    private static final String SELECT_DTO = " select e.id, e.name, e.description, e.durationInMin, e.percentage, e.isSystem, e.createdAt," +
        " e.createdBy";

    private static final String FROM_ENTITY = " from ShrinkageEntity e";

    public Uni<ShrinkageEntity> findEntityByName(final String name) {
        return this.find("select e " + FROM_ENTITY +
                       " where e.name =?1", name)
                   .firstResult();
    }

}
