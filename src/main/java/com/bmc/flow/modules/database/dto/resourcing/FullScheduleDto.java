package com.bmc.flow.modules.database.dto.resourcing;

import com.bmc.flow.modules.database.dto.base.BaseDto;
import io.quarkus.hibernate.reactive.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * this class carries full schedule data.
 * <p><br>
 * testing class fro scheduling...
 */
@Getter
@Setter
@RegisterForReflection
@NoArgsConstructor
public class FullScheduleDto extends BaseDto {
    //  private Set<ScheduleEntryDto> scheduleEntries;

    private Integer           totalShrinkageTime;
    private Short             hoursADay;
    private Set<ShrinkageDto> shrinkages;

    public FullScheduleDto(final UUID id, final Short hoursADay, Set<ShrinkageDto> shrinkages,
                           final LocalDateTime createdAt,
                           @ProjectedFieldName("createdBy.id") final UUID createdBy) {
        this.id         = id;
        this.hoursADay  = hoursADay;
        this.createdAt  = createdAt;
        this.createdBy  = createdBy;
        this.shrinkages = shrinkages;
    }

    public void setTotalShrinkageTime() {
        this.totalShrinkageTime = this.shrinkages.stream().mapToInt(ShrinkageDto::getDurationInMin).sum();
    }
}
