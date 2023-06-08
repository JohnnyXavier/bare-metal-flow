package com.bmc.flow.modules.database.dto.records.retro;

import com.bmc.flow.modules.database.dto.base.BaseDto;
import io.quarkus.hibernate.reactive.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * this class carries retrospective action data
 */
@Getter
@Setter
@RegisterForReflection
public class RetroActionDto extends BaseDto {

    @NotNull
    private UUID   retroBoardId;
    @NotNull
    @Max(value = 1000)
    private String actionToTake;

    public RetroActionDto(final UUID id, final @NotNull String actionToTake, final LocalDateTime createdAt,
                          @ProjectedFieldName("createdBy.id") final UUID createdBy,
                          @ProjectedFieldName("retroBoard.id") final @NotNull UUID retroBoardId) {
        this.id           = id;
        this.retroBoardId = retroBoardId;
        this.actionToTake = actionToTake;
        this.createdBy    = createdBy;
        this.createdAt    = createdAt;
    }

}
