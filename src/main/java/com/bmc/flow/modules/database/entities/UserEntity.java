package com.bmc.flow.modules.database.entities;

import com.bmc.flow.modules.database.entities.base.BaseEntity;
import com.bmc.flow.modules.database.entities.catalogs.DepartmentEntity;
import com.bmc.flow.modules.database.entities.catalogs.SeniorityEntity;
import com.bmc.flow.modules.database.entities.records.BoardEntity;
import com.bmc.flow.modules.database.entities.records.CardEntity;
import com.bmc.flow.modules.database.entities.records.ProjectEntity;
import com.bmc.flow.modules.database.entities.records.TaskEntity;
import com.bmc.flow.modules.database.entities.records.retro.RetroActionEntity;
import com.bmc.flow.modules.database.entities.records.retro.RetrospectiveEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
// required for Postgres as 'user' is reserved
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class UserEntity extends BaseEntity {

  public static final String FIELD_NAME = "user";

  private String firstName;

  private String lastName;

  @NotNull
  private String callSign;

  @Column(unique = true)
  @Email
  private String email;

  private String avatar;

  @Column(columnDefinition = "boolean default true")
  private boolean isActive;

  @ManyToOne
  private SeniorityEntity seniority;

  @OneToMany(mappedBy = "createdBy")
  private Set<UserEntity> createdUsers = new HashSet<>();

  @ManyToMany(mappedBy = "users")
  private Set<ProjectEntity> projects = new HashSet<>();

  @ManyToMany(mappedBy = "users")
  private Set<BoardEntity> boards = new HashSet<>();

  @ManyToMany(mappedBy = "assignees")
  private Set<CardEntity> assignedCards = new HashSet<>();

  @ManyToMany(mappedBy = "watchers")
  private Set<CardEntity> watchingCards = new HashSet<>();

  @ManyToMany(mappedBy = "users")
  private Set<DepartmentEntity> departments = new HashSet<>();

  @ManyToMany(mappedBy = "assignedTo")
  private Set<TaskEntity> tasks = new HashSet<>();

  @ManyToMany(mappedBy = "assignedTo")
  private Set<RetroActionEntity> retroActions = new HashSet<>();

  @ManyToMany(mappedBy = "attendingUsers")
  private Set<RetrospectiveEntity> retroBoards = new HashSet<>();

  @ManyToMany(mappedBy = "missingUsers")
  private Set<RetrospectiveEntity> missedRetroBoards = new HashSet<>();

}
