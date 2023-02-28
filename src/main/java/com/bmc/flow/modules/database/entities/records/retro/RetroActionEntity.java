package com.bmc.flow.modules.database.entities.records.retro;

import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.base.BaseEntity;
import com.bmc.flow.modules.database.entities.catalogs.LabelEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;

@Entity
@Table(name = "retro_action")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class RetroActionEntity extends BaseEntity {

  @ManyToMany(cascade = {PERSIST, MERGE})
  @JoinTable(name = "retro_action_labels", joinColumns = @JoinColumn(name = "retro_action_id"), inverseJoinColumns = @JoinColumn(name = "label_id"))
  private Set<LabelEntity> labels = new HashSet<>();

  @ManyToMany(cascade = {PERSIST, MERGE})
  @JoinTable(name = "retro_action_users", joinColumns = @JoinColumn(name = "retro_action_id"), inverseJoinColumns = @JoinColumn(name = "label_id"))
  private Set<UserEntity> assignedTo = new HashSet<>();

  @Column(length = 1000)
  private String actionToTake;

  @ManyToOne
  private RetrospectiveEntity retroBoard;
}
