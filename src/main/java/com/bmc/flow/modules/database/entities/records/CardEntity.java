package com.bmc.flow.modules.database.entities.records;

import com.bmc.flow.modules.database.dto.records.CardSimpleDto;
import com.bmc.flow.modules.database.entities.AttachmentEntity;
import com.bmc.flow.modules.database.entities.ChangelogEntity;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.base.BaseEntity;
import com.bmc.flow.modules.database.entities.catalogs.BoardColumnEntity;
import com.bmc.flow.modules.database.entities.catalogs.CardDifficultyEntity;
import com.bmc.flow.modules.database.entities.catalogs.CardTypeEntity;
import com.bmc.flow.modules.database.entities.catalogs.StatusEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.CascadeType.*;

/**
 * this class represents the card table and it's relations.
 */
@Entity
@Table(name = "card")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@SqlResultSetMapping(name = "cardMapping",
                     classes = {
                         @ConstructorResult(targetClass = CardSimpleDto.class,
                                            columns = {
                                                @ColumnResult(name = "id", type = UUID.class),
                                                @ColumnResult(name = "name"),
                                                @ColumnResult(name = "description"),
                                                @ColumnResult(name = "created_at", type = LocalDateTime.class)}
                         )}
)
public class CardEntity extends BaseEntity {

    /**
     * this should allow to store relative positions of a few thousand cards without triggering a reshuffle.
     * <p>
     * with a fairly large gap we may not need to trigger a reordering of cards if when inserting at the tail we go "position + gap" and
     * when insert at the head we can go "0 - gap"
     * <p>
     * this will give us 2<sup>63</sup>.-1 on each side of the zero, and we will better tackle contention on head reordering (move to top
     * operation). Contention for the "in between" placing operation will remain the same.
     */
    private Long position;

    @Column(length = 512)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    // FIXME:
    //  maybe make an attachment entity that will have
    //  name | type(img/vid/doc/text/link/etc) | url/src @ | sizeX | sizeY | sizeBytes |
    @Column(length = 1024)
    private String coverImage;

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
    private Set<ChangelogEntity> changelog = new HashSet<>();

    @OneToMany(mappedBy = "card", cascade = ALL)
    private Set<CardBugReport> bugReports = new HashSet<>();

    @ManyToMany(cascade = ALL)
    @JoinTable(name = "card_attachments", joinColumns = @JoinColumn(name = "card_id"), inverseJoinColumns = @JoinColumn(name =
        "attachment_id"))
    private Set<AttachmentEntity> attachments = new HashSet<>();

    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(name = "card_users_assigned", joinColumns = @JoinColumn(name = "card_id"),
               inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> assignees = new HashSet<>();

    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(name = "card_users_watchers", joinColumns = @JoinColumn(name = "card_id"),
               inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> watchers = new HashSet<>();

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CardLabelEntity> labels = new HashSet<>();

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
