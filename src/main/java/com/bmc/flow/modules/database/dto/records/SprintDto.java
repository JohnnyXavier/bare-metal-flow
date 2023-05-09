package com.bmc.flow.modules.database.dto.records;

import com.bmc.flow.modules.database.dto.base.BaseDto;
import io.quarkus.hibernate.reactive.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@RegisterForReflection
public class SprintDto extends BaseDto {

  @NotNull
  private String name;

  private String goal;

  @NotNull
  private LocalDateTime fromDate;

  @NotNull
  private LocalDateTime toDate;

  private LocalDateTime startDate;

  private LocalDateTime closeDate;

  private Boolean hasStarted;

  private Boolean isClosed;

  private Short daysCycle; //candidate for if null-> projectConfig.sprintDaysCycle

  private Short originalPoints;

  private Short finalPoints;

  @NotNull
  private UUID boardId;

  private UUID projectId;

  private UUID retroBoardId;

  public SprintDto(final UUID id, final String name, final String goal, final LocalDateTime fromDate, final LocalDateTime toDate,
                   final LocalDateTime startDate, final LocalDateTime closeDate, final Short daysCycle, final Boolean hasStarted,
                   final Boolean isClosed, final Short originalPoints, final Short finalPoints, final LocalDateTime createdAt,
                   @ProjectedFieldName("createdBy.id") final UUID createdBy,
                   @ProjectedFieldName("board.id") final UUID boardId,
                   @ProjectedFieldName("project.id") final UUID projectId,
                   @ProjectedFieldName("retroBoard.id") final UUID retroBoardId) {
    this.id = id;
    this.name = name;
    this.fromDate = fromDate;
    this.toDate = toDate;
    this.startDate = startDate;
    this.closeDate = closeDate;
    this.hasStarted = hasStarted;
    this.isClosed = isClosed;
    this.daysCycle = daysCycle;
    this.goal = goal;
    this.originalPoints = originalPoints;
    this.finalPoints = finalPoints;
    this.boardId = boardId;
    this.projectId = projectId;
    this.createdBy = createdBy;
    this.createdAt = createdAt;
    this.retroBoardId = retroBoardId;
  }
}
