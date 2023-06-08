package com.bmc.flow.modules.database.dto.records;

import com.bmc.flow.modules.database.dto.base.BaseRecordDto;
import io.quarkus.hibernate.reactive.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * this class carries card data.
 * <p><br>
 * this card simple variation is to connect with front end with minimal data.
 * <p><br>
 * I am leaning to a more disconnected relation on the front end. If the idea works ok, this class will remain and the {@link CardDetailDto}
 * will be deleted.
 * <p><br>
 * the disconnected relation means: a card, having many components will be broken down to a main few in this class, and the rest will be
 * loaded via different calls.
 * <p><br>
 * The idea behind that decision is that when updating a card I would want to reload the minimal data set possible to keep the front end
 * updated. i.e. if I update watchers, just update watchers.
 * <p><br>
 * At some extent breaking a card into every component will be counterproductive. As usual... balance is key...
 */
@Getter
@Setter
@RegisterForReflection
public class CardSimpleDto extends BaseRecordDto {

    private LocalDateTime     dueDate;
    private LocalDateTime     updatedAt;
    private Long              position;
    private UUID              boardId;
    private UUID              boardColumnId;
    private UUID              cardDifficultyId;
    private UUID              cardStatusId;
    private UUID              cardTypeId;
    private Set<CardLabelDto> labels = new HashSet<>();

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
        this.coverImage       = coverImage;
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
