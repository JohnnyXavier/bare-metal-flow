package com.bmc.flow.modules.database.entities.catalogs;

import com.bmc.flow.modules.database.entities.base.BaseEntity;
import com.bmc.flow.modules.database.entities.records.AccountEntity;
import com.bmc.flow.modules.database.entities.records.BoardEntity;
import com.bmc.flow.modules.database.entities.records.CardEntity;
import com.bmc.flow.modules.database.entities.records.ProjectEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "board_column")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class BoardColumnEntity extends BaseEntity {

  private String name;

  @ManyToOne
  private StatusEntity status;

  @ManyToOne
  private BoardEntity board;

  @ManyToOne
  private ProjectEntity project;

  @ManyToOne
  private AccountEntity account;

  @OneToMany(mappedBy = "boardColumn", cascade = ALL)
  private Set<CardEntity> cards = new LinkedHashSet<>();

}
