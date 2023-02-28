package com.bmc.flow.modules.database.dto.records;

import com.bmc.flow.modules.database.dto.base.BaseRecordDto;
import io.quarkus.hibernate.reactive.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.bmc.flow.modules.utilities.DataUtils.getImage;

@Getter
@Setter
@RegisterForReflection
public class CardDto extends BaseRecordDto {

  @NotNull
  private UUID boardId;

  private LocalDateTime dueDate;

  private LocalDateTime completedDate;

  private Duration estimatedTime;

  private Duration loggedTime;

  private Boolean isCompleted;

  private UUID cardTypeId;

  private UUID cardStatusId;

  private UUID cardDifficultyId;


  public CardDto(final UUID id, final String name, final String description, final String coverImage, final Boolean isCompleted,
                 final LocalDateTime dueDate, final LocalDateTime completedDate, final Duration estimatedTime, final Duration loggedTime,
                 final LocalDateTime createdAt,
                 @ProjectedFieldName("createdBy.id") final UUID createdBy,
                 @ProjectedFieldName("board.id") final UUID boardId,
                 @ProjectedFieldName("cardStatus.id") final UUID cardStatusId,
                 @ProjectedFieldName("cardType.id") final UUID cardTypeId,
                 @ProjectedFieldName("cardDifficulty.id") final UUID cardDifficultyId) {
    this.id = id;
    this.name = name;
    this.coverImage = getImage(coverImage, "CARD");
    this.description = description;
    this.estimatedTime = estimatedTime;
    this.loggedTime = loggedTime;
    this.isCompleted = isCompleted;
    this.dueDate = dueDate;
    this.completedDate = completedDate;
    this.boardId = boardId;
    this.cardStatusId = cardStatusId;
    this.cardTypeId = cardTypeId;
    this.createdBy = createdBy;
    this.createdAt = createdAt;
    this.cardDifficultyId = cardDifficultyId;
  }

}
