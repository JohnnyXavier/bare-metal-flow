package com.bmc.flow.modules.database.entities.catalogs;

import com.bmc.flow.modules.database.entities.base.BaseCatalogEntity;
import com.bmc.flow.modules.database.entities.records.CardEntity;
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
@Table(name = "card_status")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class StatusEntity extends BaseCatalogEntity {

  @OneToMany(mappedBy = "cardStatus", cascade = ALL)
  private Set<CardEntity> cards = new HashSet<>();

  @OneToMany(mappedBy = "status", cascade = ALL)
  private Set<BoardColumnEntity> boardColumns = new HashSet<>();

}
