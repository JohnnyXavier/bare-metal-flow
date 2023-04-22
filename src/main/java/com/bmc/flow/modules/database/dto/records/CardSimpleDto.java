package com.bmc.flow.modules.database.dto.records;

import com.bmc.flow.modules.database.dto.base.BaseRecordDto;
import io.quarkus.hibernate.reactive.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bmc.flow.modules.utilities.DataUtils.getImage;

@Getter
@Setter
@RegisterForReflection
public class CardSimpleDto extends BaseRecordDto {

  @NotNull
  private Boolean            isCompleted;
  private LocalDateTime      dueDate;
  private LocalDateTime      updatedAt;
  private Long               position;
  private UUID               boardId;
  private UUID               boardColumnId;
  private UUID               cardDifficultyId;
  private UUID               cardStatusId;
  private UUID               cardTypeId;
  private List<CardLabelDto> labels = new ArrayList<>();

  public CardSimpleDto(final UUID id, final String name, final String description, final String coverImage,
                       final LocalDateTime dueDate, final LocalDateTime createdAt, final LocalDateTime updatedAt,
                       final Long position,
                       @ProjectedFieldName("createdBy.id") final UUID createdBy,
                       @ProjectedFieldName("board.id") final UUID boardId,
                       @ProjectedFieldName("boardColumn.id") final UUID boardColumnId,
                       @ProjectedFieldName("cardStatus.id") final UUID cardStatusId,
                       @ProjectedFieldName("cardType.id") final UUID cardTypeId,
                       @ProjectedFieldName("cardDifficulty.id") final UUID cardDifficultyId
  ) {
    this.boardId          = boardId;
    this.boardColumnId    = boardColumnId;
    this.cardDifficultyId = cardDifficultyId;
    this.cardStatusId     = cardStatusId;
    this.cardTypeId       = cardTypeId;
    this.coverImage       = getImage(coverImage, "CARD");
    this.createdAt        = createdAt;
    this.createdBy        = createdBy;
    this.description      = description;
    this.dueDate          = dueDate;
    this.id               = id;
    this.name             = name;
    this.position         = position;
    this.updatedAt        = updatedAt;
  }

}
