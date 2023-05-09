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
public class BoardColumnDto extends BaseDto {

  private UUID   boardId;
  private String name;
  @NotNull
  private UUID   statusId;
  private String statusName;

  public BoardColumnDto(final UUID id, final LocalDateTime createdAt, final String name,
                        @ProjectedFieldName("createdBy.id") final UUID createdBy,
                        @ProjectedFieldName("board.id") final UUID boardId,
                        @ProjectedFieldName("status.id") final UUID statusId,
                        @ProjectedFieldName("status.name") final String statusName) {
    this.boardId    = boardId;
    this.createdAt  = createdAt;
    this.createdBy  = createdBy;
    this.id         = id;
    this.name       = name;
    this.statusId   = statusId;
    this.statusName = statusName;
  }
}
