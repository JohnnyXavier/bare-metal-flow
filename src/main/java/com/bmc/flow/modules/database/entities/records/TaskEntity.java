package com.bmc.flow.modules.database.entities.records;

import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.base.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;

@Entity
@Table(name = "task")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class TaskEntity extends BaseEntity {

  public static final String FIELD_NAME = "task";

  private String name;

  private LocalDateTime dueDate;

  // this gets set when is completed is true
  private LocalDateTime completedDate;

  @Column(columnDefinition = "boolean default false")
  private Boolean isCompleted;

  @ManyToOne
  private CardEntity card;

  @ManyToMany(cascade = {PERSIST, MERGE})
  @JoinTable(name = "tasks_users_assigned", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
  private Set<UserEntity> assignedTo = new HashSet<>();

}
