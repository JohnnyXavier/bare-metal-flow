package com.bmc.flow.modules.database.dto.catalogs;

import com.bmc.flow.modules.database.dto.base.BaseCatalogDto;
import io.quarkus.hibernate.reactive.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * this class carries card difficulty data
 */
@Getter
@Setter
@RegisterForReflection
public class CardDifficultyDto extends BaseCatalogDto {

    @NotNull
    private Short level;

    public CardDifficultyDto(final UUID id, final String name, final String description, final @NotNull Short level, final Boolean isSystem,
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
