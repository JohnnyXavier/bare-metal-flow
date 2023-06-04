package com.bmc.flow.modules.service.utils;

import com.bmc.flow.modules.database.dto.base.BaseCatalogDto;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.base.BaseCatalogEntity;

import static java.lang.Boolean.FALSE;
import static java.util.UUID.randomUUID;

public class CreationUtils {

  private CreationUtils() {
  }

  public static void createBaseCatalogEntity(BaseCatalogEntity CatalogEntity, BaseCatalogDto CatalogDto) {
    UserEntity creator = new UserEntity();
    creator.setId(CatalogDto.getCreatedBy());

    CatalogEntity.setId(randomUUID());
    CatalogEntity.setName(CatalogDto.getName());
    CatalogEntity.setDescription(CatalogDto.getDescription());
    CatalogEntity.setCreatedBy(creator);
    CatalogEntity.setIsSystem(FALSE);
  }
}
