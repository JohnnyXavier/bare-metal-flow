package com.bmc.flow.modules.database.repositories.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.DepartmentDto;
import com.bmc.flow.modules.database.entities.catalogs.DepartmentEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

/**
 * this class is the data access for department data.
 */
@ApplicationScoped
public class DepartmentRepository implements PanacheRepositoryBase<DepartmentEntity, UUID> {

    private static final String SELECT_DTO = " select e.id, e.name, e.description, e.isSystem, e.createdAt, e.createdBy.id";

    private static final String FROM_ENTITY = " from DepartmentEntity e";

    public Uni<List<DepartmentDto>> findAllCreatedByUserId(final UUID userId) {
        return this.find(SELECT_DTO + FROM_ENTITY +
                       " where e.createdBy.id =?1", userId)
                   .project(DepartmentDto.class)
                   .list();
    }

    public Uni<DepartmentEntity> findEntityByName(final String name) {
        return this.find("select e " + FROM_ENTITY +
                       " where e.name =?1", name)
                   .firstResult();
    }

}
