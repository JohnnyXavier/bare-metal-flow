package com.bmc.flow.modules.database.dto.records.retro;

import com.bmc.flow.modules.database.dto.base.BaseRecordDto;
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
public class RetrospectiveDto extends BaseRecordDto {

  @NotNull
  private UUID sprintBoardId;

  private UUID projectId;

  public RetrospectiveDto(final UUID id, final LocalDateTime createdAt,
                          @ProjectedFieldName("createdBy.id") final UUID createdBy,
                          @ProjectedFieldName("project.id") final UUID projectId,
                          @ProjectedFieldName("sprintBoard.id") final UUID sprintBoardId) {
    this.id = id;
    this.sprintBoardId = sprintBoardId;
    this.projectId = projectId;
    this.createdBy = createdBy;
    this.createdAt = createdAt;
  }
}
