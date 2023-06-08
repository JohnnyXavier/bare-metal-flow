package com.bmc.flow.modules.database.entities.records;

import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.base.BaseRecordEntity;
import com.bmc.flow.modules.database.entities.catalogs.BoardColumnEntity;
import com.bmc.flow.modules.database.entities.catalogs.BoardTypeEntity;
import com.bmc.flow.modules.database.entities.catalogs.LabelEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;

/**
 * this class represents the board table and it's relations.
 */
@Entity
@Table(name = "board")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class BoardEntity extends BaseRecordEntity {

    @Column(columnDefinition = "boolean default false")
    private Boolean isFavorite;

    @OneToMany(mappedBy = "board", cascade = ALL, fetch = LAZY)
    private Set<SprintEntity> sprints = new HashSet<>();

    @OneToMany(mappedBy = "board", cascade = ALL, fetch = LAZY)
    private Set<BoardColumnEntity> boardColumns = new HashSet<>();

    @OneToMany(mappedBy = "board", cascade = ALL, fetch = LAZY)
    private Set<CardLabelEntity> cardLabels = new HashSet<>();

    @ManyToMany(cascade = {PERSIST, MERGE}, fetch = LAZY)
    @JoinTable(name = "board_users", joinColumns = @JoinColumn(name = "board_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> users = new HashSet<>();

    @ManyToMany(cascade = {PERSIST, MERGE}, fetch = LAZY)
    @JoinTable(name = "board_labels", joinColumns = @JoinColumn(name = "board_id"), inverseJoinColumns = @JoinColumn(name = "label_id"))
    private Set<LabelEntity> labels = new HashSet<>();

    @ManyToOne(cascade = ALL, fetch = LAZY)
    private BoardTypeEntity boardType;

    @ManyToOne(fetch = LAZY)
    private ProjectEntity project;

    @ManyToOne(fetch = LAZY)
    private AccountEntity account;

}
