package com.bmc.flow.modules.database.entities.records;

import com.bmc.flow.modules.database.dto.records.CardLabelDto;
import com.bmc.flow.modules.database.entities.catalogs.LabelEntity;
import com.bmc.flow.modules.database.entities.ids.CardLabelId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "card_label")
@Getter
@Setter
@SqlResultSetMapping(name = "cardLabelMapping",
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

}
