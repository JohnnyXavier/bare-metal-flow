package com.bmc.flow.modules.database.entities.catalogs;

import com.bmc.flow.modules.database.entities.base.BaseCatalogEntity;
import com.bmc.flow.modules.database.entities.records.*;
import com.bmc.flow.modules.database.entities.records.BoardEntity;
import com.bmc.flow.modules.database.entities.records.retro.RetroActionEntity;
import com.bmc.flow.modules.database.entities.records.retro.RetrospectiveEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "label")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class LabelEntity extends BaseCatalogEntity {

  //TODO: choose a better DB dataType for hex. Candidate is bytea(hex format) for postgresql
  // https://www.postgresql.org/docs/current/datatype-binary.html#id-1.5.7.12.9
  // ...
  // @Column(columnDefinition = "varchar", length = 4)
  private String colorHex;

  @ManyToMany(mappedBy = "labels")
  private Set<CardEntity> cards = new HashSet<>();

  @ManyToMany(mappedBy = "labels")
  private Set<AccountEntity> accounts = new HashSet<>();

  @ManyToMany(mappedBy = "labels")
  private Set<BoardEntity> boards = new HashSet<>();

  @ManyToMany(mappedBy = "labels")
  private Set<ProjectEntity> projects = new HashSet<>();

  @ManyToMany(mappedBy = "labels")
  private Set<RetrospectiveEntity> retroBoards = new HashSet<>();

  @ManyToMany(mappedBy = "labels")
  private Set<RetroActionEntity> retroActions = new HashSet<>();

}
