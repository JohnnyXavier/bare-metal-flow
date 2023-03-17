package com.bmc.flow.modules.database.entities.records;

import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.base.BaseRecordEntity;
import com.bmc.flow.modules.database.entities.catalogs.LabelEntity;
import com.bmc.flow.modules.database.entities.records.retro.RetrospectiveEntity;
import com.bmc.flow.modules.database.entities.resourcing.ScheduleEntryEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "project")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class ProjectEntity extends BaseRecordEntity {

  @OneToMany(mappedBy = "project", cascade = ALL)
  private Set<BoardEntity> boards = new HashSet<>();

  @OneToMany(mappedBy = "project", cascade = ALL)
  private Set<CardEntity> cards = new HashSet<>();

  @OneToMany(mappedBy = "project", cascade = ALL)
  private Set<RetrospectiveEntity> retroBoards = new HashSet<>();

  @ManyToMany(cascade = {PERSIST, MERGE})
  @JoinTable(name = "project_users", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
  private Set<UserEntity> users = new HashSet<>();

  @ManyToOne
  private UserEntity projectLead;

  @ManyToMany(cascade = {PERSIST, MERGE})
  @JoinTable(name = "project_labels", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "label_id"))
  private Set<LabelEntity> labels = new HashSet<>();


  @OneToMany(mappedBy = "project", cascade = ALL)
  private Set<ScheduleEntryEntity> scheduling;

  @ManyToOne
  private AccountEntity account;

}
