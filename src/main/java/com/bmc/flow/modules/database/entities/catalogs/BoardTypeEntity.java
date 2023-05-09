package com.bmc.flow.modules.database.entities.catalogs;

import com.bmc.flow.modules.database.entities.base.BaseCatalogEntity;
import com.bmc.flow.modules.database.entities.records.BoardEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name = "board_type")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class BoardTypeEntity extends BaseCatalogEntity {

  @OneToMany(mappedBy = "boardType", cascade = ALL)
  private Set<BoardEntity> board;

}
