package com.bmc.flow.modules.service.utils;

import com.bmc.flow.modules.database.dto.base.BaseCatalogDto;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.base.BaseCatalogEntity;

import static java.lang.Boolean.FALSE;
import static java.util.UUID.randomUUID;

/**
 * this class is contains utilities to create entities or fragment of entities.
 */
public class CreationUtils {

    private CreationUtils() {
    }

    /**
     * populates a catalog entity with data coming from a dto.
     *
     * @param CatalogEntity the catalog entity to add data to
     * @param CatalogDto    the catalog dto containing the data to populate the entity
     */
    public static void populateBaseCatalogEntity(BaseCatalogEntity CatalogEntity, BaseCatalogDto CatalogDto) {
        UserEntity creator = new UserEntity();
        creator.setId(CatalogDto.getCreatedBy());

        CatalogEntity.setId(randomUUID());
        CatalogEntity.setName(CatalogDto.getName());
        CatalogEntity.setDescription(CatalogDto.getDescription());
        CatalogEntity.setCreatedBy(creator);
        CatalogEntity.setIsSystem(FALSE);
    }
}
