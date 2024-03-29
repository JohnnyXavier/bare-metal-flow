package com.bmc.flow.modules.database.dto.records;

import com.bmc.flow.modules.database.dto.base.BaseDto;
import io.quarkus.hibernate.reactive.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * this class carries comment data
 */
@Getter
@Setter
@RegisterForReflection
public class CommentDto extends BaseDto {

    @NotNull
    private final UUID    cardId;
    private final String  creatorCallSign;
    private final String  creatorAvatar;
    private final Boolean creatorIisActive;
    @NotNull
    private       String  comment;


    public CommentDto(final UUID id, final @NotNull String comment, final LocalDateTime createdAt,
                      @ProjectedFieldName("createdBy.id") final UUID createdBy,
                      @ProjectedFieldName("createdBy.callSign") final String creatorCallSign,
                      @ProjectedFieldName("createdBy.avatar") final String creatorAvatar,
                      @ProjectedFieldName("createdBy.isActive") final Boolean creatorIsActive,
                      @ProjectedFieldName("card.id") final @NotNull UUID cardId) {
        this.id               = id;
        this.cardId           = cardId;
        this.createdBy        = createdBy;
        this.createdAt        = createdAt;
        this.comment          = comment;
        this.creatorCallSign  = creatorCallSign;
        this.creatorAvatar    = creatorAvatar;
        this.creatorIisActive = creatorIsActive;
    }

}
