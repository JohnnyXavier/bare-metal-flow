package com.bmc.flow.modules.database.entities.ids;

import com.bmc.flow.modules.database.entities.records.CardLabelEntity;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * this class represents the card label id.
 * <p>
 * it is an embeddable id that will be used as the {@link CardLabelEntity} id
 */
@Embeddable
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CardLabelId implements Serializable {

    @EqualsAndHashCode.Include
    private UUID cardId;

    @EqualsAndHashCode.Include
    private UUID labelId;
}

