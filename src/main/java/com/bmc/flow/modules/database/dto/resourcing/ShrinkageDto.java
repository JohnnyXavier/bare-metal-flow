package com.bmc.flow.modules.database.dto.resourcing;

import com.bmc.flow.modules.database.dto.base.BaseCatalogDto;
import io.quarkus.hibernate.reactive.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * this class carries shrinkage data.
 */
@Getter
@Setter
@RegisterForReflection
public class ShrinkageDto extends BaseCatalogDto {

    @Min(0)
    private Short durationInMin;
    @Min(0)
    @Max(100)
    private Short percentage;

    public ShrinkageDto(final UUID id, final String name, final String description, final Short durationInMin, final Short percentage,
                        final Boolean isSystem, final LocalDateTime createdAt,
                        @ProjectedFieldName("createdBy.id") final UUID createdBy) {
        this.id            = id;
        this.name          = name;
        this.description   = description;
        this.durationInMin = durationInMin;
        this.percentage    = percentage;
        this.isSystem      = isSystem;
        this.createdAt     = createdAt;
        this.createdBy     = createdBy;
    }

}
