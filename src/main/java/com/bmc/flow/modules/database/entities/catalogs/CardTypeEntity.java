package com.bmc.flow.modules.database.entities.catalogs;

import com.bmc.flow.modules.database.entities.records.CardEntity;
import com.bmc.flow.modules.database.entities.base.BaseCatalogEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name = "card_type")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class CardTypeEntity extends BaseCatalogEntity {

  public static final String FIELD_NAME = "card-type";

  @OneToMany(mappedBy = "cardType", cascade = ALL)
  private Set<CardEntity> cards = new HashSet<>();
}
