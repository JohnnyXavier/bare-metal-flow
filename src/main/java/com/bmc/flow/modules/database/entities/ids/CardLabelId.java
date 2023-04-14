package com.bmc.flow.modules.database.entities.ids;

import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CardLabelId implements Serializable {

  @EqualsAndHashCode.Include
  private UUID cardId;

  @EqualsAndHashCode.Include
  private UUID labelId;
}
