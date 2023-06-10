package com.bmc.flow.modules.database.dto.catalogs;

import com.bmc.flow.modules.database.dto.base.BaseCatalogDto;
import io.quarkus.hibernate.reactive.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * this class carries seniority data
 */
@Getter
@Setter
@RegisterForReflection
public class SeniorityDto extends BaseCatalogDto {

    @NotNull
    private Short level;

    public SeniorityDto(final UUID id, final String name, final String description, final Boolean isSystem, final Short level,
                        final LocalDateTime createdAt,
                        @ProjectedFieldName("createdBy.id") final UUID createdBy) {
        this.id          = id;
        this.name        = name;
        this.description = description;
        this.level       = level;
        this.createdAt   = createdAt;
        this.createdBy   = createdBy;
        this.isSystem    = isSystem;

    }

}

