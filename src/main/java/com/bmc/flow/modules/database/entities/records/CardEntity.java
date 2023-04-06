package com.bmc.flow.modules.database.entities.records;

import com.bmc.flow.modules.database.entities.AttachmentEntity;
import com.bmc.flow.modules.database.entities.ChangeLogCardEntity;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.base.BaseRecordEntity;
import com.bmc.flow.modules.database.entities.catalogs.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "card")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class CardEntity extends BaseRecordEntity {

  @Column(columnDefinition = "boolean default false")
  private boolean isHead;

  @Column(columnDefinition = "boolean default false")
  private boolean isTail;

  private UUID prevCard;

  private UUID nextCard;

  private LocalDateTime dueDate;

  private LocalDateTime completedDate;

  private Duration estimatedTime;

  private Duration loggedTime;

  private Duration remainingTime;

  @Column(columnDefinition = "boolean default false")
  private Boolean isCompleted;

  @Column(nullable = true)
  private Short bugsReported;

  // generic tasks, definition of done, definition of ready
  @OneToMany(mappedBy = "card", cascade = ALL)
  private Set<TaskEntity> tasks = new HashSet<>();

  @OneToMany(mappedBy = "card", cascade = ALL)
  private Set<CommentEntity> comments = new HashSet<>();

  @ManyToMany(mappedBy = "cards")
  private Set<SprintEntity> sprint;

  @OneToMany(mappedBy = "card", cascade = ALL)
  private Set<ChangeLogCardEntity> changelog = new HashSet<>();

  @OneToMany(mappedBy = "card", cascade = ALL)
  private Set<CardBugReport> bugReports = new HashSet<>();

  @ManyToMany(cascade = ALL)
  @JoinTable(name = "card_attachments", joinColumns = @JoinColumn(name = "card_id"), inverseJoinColumns = @JoinColumn(name =
      "attachment_id"))
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
  private BoardColumnEntity boardColumn;

  @ManyToOne
  private ProjectEntity project;

  @ManyToOne
  private AccountEntity account;

  @ManyToOne
  private CardTypeEntity cardType;

  @ManyToOne
  private StatusEntity cardStatus;

  @ManyToOne
  private CardDifficultyEntity cardDifficulty;

}
