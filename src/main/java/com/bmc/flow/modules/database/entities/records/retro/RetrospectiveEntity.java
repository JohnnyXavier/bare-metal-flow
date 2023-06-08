package com.bmc.flow.modules.database.entities.records.retro;

import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.base.BaseEntity;
import com.bmc.flow.modules.database.entities.catalogs.LabelEntity;
import com.bmc.flow.modules.database.entities.records.ProjectEntity;
import com.bmc.flow.modules.database.entities.records.SprintEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;

/**
 * this class represents the retrospective table and it's relations.
 */
@Entity
@Table(name = "retrospective")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class RetrospectiveEntity extends BaseEntity {

    @OneToMany(mappedBy = "retroBoard", cascade = ALL)
    private Set<RetroCardEntity> retroCards = new HashSet<>();

    @OneToMany(mappedBy = "retroBoard", cascade = ALL)
    private Set<RetroActionEntity> retroActions = new HashSet<>();

    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(name = "retro_board_users", joinColumns = @JoinColumn(name = "retro_board_id"),
               inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> attendingUsers = new HashSet<>();

    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(name = "retro_board_missing_users", joinColumns = @JoinColumn(name = "retro_board_id"),
               inverseJoinColumns = @JoinColumn(name = "user_id"))
    // calculate every time the attending users is updated against the sprint's scheduled users
    private Set<UserEntity> missingUsers = new HashSet<>();

    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(name = "retro_board_labels", joinColumns = @JoinColumn(name = "retro_board_id"),
               inverseJoinColumns = @JoinColumn(name = "label_id"))
    private Set<LabelEntity> labels = new HashSet<>();

    @ManyToOne
    private ProjectEntity project;

    @OneToOne(mappedBy = "retroBoard")
    private SprintEntity sprintBoard;

}
