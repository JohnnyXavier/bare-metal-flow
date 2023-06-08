package com.bmc.flow.modules.database.dto;

import com.bmc.flow.modules.database.entities.catalogs.ChangeAction;
import io.quarkus.hibernate.reactive.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * this class changelog card data.
 */
@Getter
@Setter
@RegisterForReflection
public class ChangeLogCardDto {

    private String        field;
    private String        changeFrom;
    private String        changeTo;
    private ChangeAction  changeAction;
    private LocalDateTime createdAt;
    private UUID          createdById;
    private String        createdByCallSign;
    private String        createdByAvatar;

    public ChangeLogCardDto(final String field, final String changeFrom, final String changeTo, final LocalDateTime createdAt,
                            final ChangeAction changeAction,
                            @ProjectedFieldName("createdBy.id") final UUID createdById,
                            @ProjectedFieldName("createdBy.callSign") final String createdByCallSign,
                            @ProjectedFieldName("createdBy.avatar") final String createdByAvatar
    ) {
        this.changeFrom        = changeFrom;
        this.field             = field;
        this.changeTo          = changeTo;
        this.changeAction      = changeAction;
        this.createdAt         = createdAt;
        this.createdById       = createdById;
        this.createdByCallSign = createdByCallSign;
        this.createdByAvatar   = createdByAvatar;
    }

}
