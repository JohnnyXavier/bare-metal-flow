package com.bmc.flow.modules.database.dto.records;

import com.bmc.flow.modules.database.dto.base.BaseDto;
import io.quarkus.hibernate.reactive.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@RegisterForReflection
public class TaskDto extends BaseDto {

  @NotNull
  private final UUID cardId;

  @NotNull
  private String name;

  private LocalDateTime dueDate;

  private LocalDateTime completedDate;

  private Boolean isCompleted;


  public TaskDto(final UUID id, final String name, final LocalDateTime dueDate, final LocalDateTime completedDate,
                 final Boolean isCompleted, final LocalDateTime createdAt,
                 @ProjectedFieldName("createdBy.id") final UUID createdBy,
                 @ProjectedFieldName("card.id") final UUID cardId) {
    this.id = id;
    this.cardId = cardId;
    this.name = name;
    this.dueDate = dueDate;
    this.completedDate = completedDate;
    this.isCompleted = isCompleted;
    this.createdAt = createdAt;
    this.createdBy = createdBy;
  }

}
