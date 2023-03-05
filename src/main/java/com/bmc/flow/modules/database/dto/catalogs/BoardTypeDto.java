package com.bmc.flow.modules.database.dto.catalogs;

import com.bmc.flow.modules.database.dto.base.BaseCatalogDto;
import io.quarkus.hibernate.reactive.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@RegisterForReflection
public class BoardTypeDto extends BaseCatalogDto {

  public BoardTypeDto(final UUID id, final String name, final String description, final Boolean isSystem,
                      final LocalDateTime createdAt,
                      @ProjectedFieldName("createdBy.id") final UUID createdBy) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.createdAt = createdAt;
    this.createdBy = createdBy;
    this.isSystem = isSystem;
  }
}
