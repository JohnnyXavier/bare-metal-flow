package com.bmc.flow.modules.database.entities.records;

import com.bmc.flow.modules.database.dto.records.CardLabelDto;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.catalogs.LabelEntity;
import com.bmc.flow.modules.database.entities.ids.CardLabelId;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "card_label")
@Getter
@Setter
@NamedNativeQuery(name = "CardLabel.findAllByBoardId",
    query = "select cl.card_id, cl.label_id, l.name, l.description, l.color_hex" +
        " from card_label cl" +
        " join label l on l.id = cl.label_id" +
        " where cl.board_id = :boardId",
    resultSetMapping = "Mapping.CardLabelDto")
@NamedNativeQuery(name = "CardLabel.findAllByCardId",
    query = "select cl.card_id, cl.label_id, l.name, l.description, l.color_hex" +
        " from card_label cl" +
        " join label l on l.id = cl.label_id" +
        " where cl.card_id = :id",
    resultSetMapping = "Mapping.CardLabelDto")
@SqlResultSetMapping(name = "Mapping.CardLabelDto",
    classes = {
        @ConstructorResult(targetClass = CardLabelDto.class,
            columns = {
                @ColumnResult(name = "card_id", type = UUID.class),
                @ColumnResult(name = "label_id", type = UUID.class),
                @ColumnResult(name = "name"),
                @ColumnResult(name = "description"),
                @ColumnResult(name = "color_hex")
            }
        )}
)
//@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
// FIXME: revisit the equals and hashcode on this entity
public class CardLabelEntity {

  @EmbeddedId
  private CardLabelId id;

  @ManyToOne(fetch = LAZY)
  @MapsId("cardId")
  private CardEntity card;

  @ManyToOne(fetch = LAZY)
  @MapsId("labelId")
  private LabelEntity label;

  @OneToOne(fetch = LAZY)
  private BoardEntity board;

  @ManyToOne(fetch = LAZY)
  private UserEntity createdBy;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  // there is no need to have an "updatedAt" field given that no field here is updatable
  // either it is and entry on the db, or it is not
}
