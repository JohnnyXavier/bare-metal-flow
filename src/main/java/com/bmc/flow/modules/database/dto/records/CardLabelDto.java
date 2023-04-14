package com.bmc.flow.modules.database.dto.records;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RegisterForReflection
public class CardLabelDto {

  private String name;
  private String description;
  private String colorHex;
  private UUID cardId;
  private UUID labelId;

  public CardLabelDto(final UUID cardId, final UUID labelId, final String name, final String description,
                      final String colorHex) {
    this.cardId  = cardId;
    this.labelId = labelId;
    this.name = name;
    this.description = description;
    this.colorHex = colorHex;
  }

}
