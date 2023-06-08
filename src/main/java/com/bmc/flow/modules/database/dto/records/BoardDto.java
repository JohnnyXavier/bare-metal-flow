package com.bmc.flow.modules.database.dto.records;

import com.bmc.flow.modules.database.dto.base.BaseRecordDto;
import io.quarkus.hibernate.reactive.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.bmc.flow.modules.utilities.DataUtils.getImage;

/**
 * this class carries board data
 */
@Getter
@Setter
@RegisterForReflection
public class BoardDto extends BaseRecordDto {

    @NotNull
    private UUID    projectId;
    @NotNull
    private UUID    boardTypeId;
    private Boolean isFavorite;

    public BoardDto(final UUID id, final String name, final String description, final String coverImage, final LocalDateTime createdAt,
                    final Boolean isFavorite,
                    @ProjectedFieldName("createdBy.id") final UUID createdBy,
                    @ProjectedFieldName("project.id") final @NotNull UUID projectId,
                    @ProjectedFieldName("boardType.id") final @NotNull UUID boardTypeId) {
        this.id          = id;
        this.name        = name;
        this.coverImage  = getImage(coverImage, "BOARD");
        this.description = description;
        this.boardTypeId = boardTypeId;
        this.projectId   = projectId;
        this.createdBy   = createdBy;
        this.createdAt   = createdAt;
        this.isFavorite  = isFavorite;
    }

}
