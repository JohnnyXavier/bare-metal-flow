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
public class BoardColumnDto extends BaseDto {

  @NotNull
  private UUID   statusId;
  private UUID   boardId;
  private String statusName;

  public BoardColumnDto(final UUID id, final LocalDateTime createdAt,
                        @ProjectedFieldName("createdBy.id") final UUID createdBy,
                        @ProjectedFieldName("board.id") final UUID boardId,
                        @ProjectedFieldName("status.id") final UUID statusId,
                        @ProjectedFieldName("status.name") final String statusName) {
    this.id         = id;
    this.statusId   = statusId;
    this.boardId    = boardId;
    this.statusName = statusName;
    this.createdBy  = createdBy;
    this.createdAt  = createdAt;
  }
}
