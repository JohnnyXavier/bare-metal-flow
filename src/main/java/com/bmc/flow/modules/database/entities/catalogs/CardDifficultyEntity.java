package com.bmc.flow.modules.database.entities.catalogs;

import com.bmc.flow.modules.database.entities.records.CardEntity;
import com.bmc.flow.modules.database.entities.base.BaseCatalogEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "card_difficulty")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class CardDifficultyEntity extends BaseCatalogEntity {

  public static final String FIELD_NAME = "card-difficulty";

  private short level;

  @OneToMany(mappedBy = "cardDifficulty", cascade = ALL)
  private Set<CardEntity> cards = new HashSet<>();

}
