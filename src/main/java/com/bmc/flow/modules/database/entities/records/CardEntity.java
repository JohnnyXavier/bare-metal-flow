package com.bmc.flow.modules.database.entities.records;

import com.bmc.flow.modules.database.entities.*;
import com.bmc.flow.modules.database.entities.base.BaseRecordEntity;
import com.bmc.flow.modules.database.entities.catalogs.CardDifficultyEntity;
import com.bmc.flow.modules.database.entities.catalogs.CardStatusEntity;
import com.bmc.flow.modules.database.entities.catalogs.CardTypeEntity;
import com.bmc.flow.modules.database.entities.catalogs.LabelEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "card")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class CardEntity extends BaseRecordEntity {

  public static final String FIELD_NAME = "card";

  private LocalDateTime dueDate;

  private LocalDateTime completedDate;

  private Duration estimatedTime;

  private Duration loggedTime;

  private Duration remainingTime;

  @Column(columnDefinition = "boolean default false")
  private Boolean isCompleted;

  // generic tasks, definition of done, definition of ready
  @OneToMany(mappedBy = "card", cascade = ALL)
  private Set<TaskEntity> tasks = new HashSet<>();

  @OneToMany(mappedBy = "card", cascade = ALL)
  private Set<CommentEntity> comments = new HashSet<>();

  @ManyToMany(mappedBy = "cards")
  private Set<SprintEntity> sprint;

  @OneToMany(mappedBy = "card", cascade = ALL)
  private Set<ChangeLogCardEntity> changelog = new HashSet<>();

  @ManyToMany(cascade = ALL)
  @JoinTable(name = "card_attachments", joinColumns = @JoinColumn(name = "card_id"), inverseJoinColumns = @JoinColumn(name = "attachment_id"))
  private Set<AttachmentEntity> attachments = new HashSet<>();

  @ManyToMany(cascade = {PERSIST, MERGE})
  @JoinTable(name = "card_users_assigned", joinColumns = @JoinColumn(name = "card_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
  private Set<UserEntity> assignees = new HashSet<>();

  @ManyToMany(cascade = {PERSIST, MERGE})
  @JoinTable(name = "card_users_watchers", joinColumns = @JoinColumn(name = "card_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
  private Set<UserEntity> watchers = new HashSet<>();

  @ManyToMany(cascade = {PERSIST, MERGE})
  @JoinTable(name = "card_labels", joinColumns = @JoinColumn(name = "card_id"), inverseJoinColumns = @JoinColumn(name = "label_id"))
  private Set<LabelEntity> labels = new HashSet<>();

  @ManyToOne
  private BoardEntity board;

  @ManyToOne
  private CardTypeEntity cardType;

  @ManyToOne
  private CardStatusEntity cardStatus;

  @ManyToOne
  private CardDifficultyEntity cardDifficulty;

}
