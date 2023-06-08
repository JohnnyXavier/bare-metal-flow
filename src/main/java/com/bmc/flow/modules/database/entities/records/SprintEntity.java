package com.bmc.flow.modules.database.entities.records;

import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.base.BaseEntity;
import com.bmc.flow.modules.database.entities.catalogs.LabelEntity;
import com.bmc.flow.modules.database.entities.records.retro.RetrospectiveEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;

/**
 * this class represents the sprint table and it's relations.
 */
@Entity
@Table(name = "sprint")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class SprintEntity extends BaseEntity {

    private String name;

    @Column(columnDefinition = "text")
    private String goal;

    private LocalDateTime fromDate;

    private LocalDateTime toDate; // fromDate + cycle

    private LocalDateTime startDate; // on.start()

    private LocalDateTime closeDate; // on.close()

    private Short daysCycle;

    @Column(columnDefinition = "boolean default false")
    private Boolean hasStarted;


    @Column(columnDefinition = "boolean default false")
    private Boolean isClosed;

    @Column(columnDefinition = "boolean default false")
    private Short currentPoints;

    private Short originalPoints; //on sprint.start() add the current here

    private Short finalPoints; // on sprint.close() add the final card points here

    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(name = "sprint_cards", joinColumns = @JoinColumn(name = "sprint_id"), inverseJoinColumns = @JoinColumn(name = "card_id"))
    private Set<CardEntity> cards = new HashSet<>();

    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(name = "sprint_original_cards", joinColumns = @JoinColumn(name = "sprint_id"),
               inverseJoinColumns = @JoinColumn(name = "card_id"))
    private Set<CardEntity> originalCards = new HashSet<>();

    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(name = "sprint_users", joinColumns = @JoinColumn(name = "sprint_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> users = new HashSet<>();

    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(name = "sprint_labels", joinColumns = @JoinColumn(name = "sprint_id"), inverseJoinColumns = @JoinColumn(name = "label_id"))
    private Set<LabelEntity> labels = new HashSet<>();

    @ManyToOne
    private ProjectEntity project;

    @ManyToOne
    private BoardEntity board;

    @OneToOne(cascade = ALL)
    private RetrospectiveEntity retroBoard;

}
