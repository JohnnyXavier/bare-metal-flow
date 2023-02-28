package com.bmc.flow.modules.database.dto.records.retro;

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
public class RetroCardDto extends BaseDto {

  @NotNull
  private UUID retroBoardId;

  @NotNull
  private String comment;

  private Short votes;


  public RetroCardDto(final UUID id, final String comment, final Short votes, final LocalDateTime createdAt,
                      @ProjectedFieldName("createdBy.id") final UUID createdBy,
                      @ProjectedFieldName("retroBoard.id") final UUID retroBoardId) {
    this.id = id;
    this.retroBoardId = retroBoardId;
    this.votes = votes;
    this.comment = comment;
    this.createdBy = createdBy;
    this.createdAt = createdAt;
  }

}
