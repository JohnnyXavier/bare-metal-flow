package com.bmc.flow.modules.database.dto.resourcing;

import com.bmc.flow.modules.database.dto.base.BaseDto;
import io.quarkus.hibernate.reactive.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * this class carries schedule data.
 */
@Getter
@Setter
@RegisterForReflection
public class ScheduleDto extends BaseDto {

    private Short hoursADay;

    public ScheduleDto(final UUID id, final Short hoursADay, final LocalDateTime createdAt,
                       @ProjectedFieldName("createdBy.id") final UUID createdBy) {
        this.id        = id;
        this.hoursADay = hoursADay;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

}
